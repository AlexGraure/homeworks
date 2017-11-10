#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h> 
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <errno.h>
#include <netdb.h>
#include <fcntl.h>
#include <unistd.h>
#define SMTP_PORT 80

#define MAX_CLIENTS	5
#define BUFLEN 2000
#define MAXBUFLEN 2500
#define MAXLEN 2000

void error(char *msg)
{
    perror(msg);
    exit(1);
}

/**
 * Citeste maxim maxlen octeti din socket-ul sockfd. Intoarce
 * numarul de octeti cititi.
 * Functia este din laborator
 */
ssize_t Readline(int sockd, void *vptr, size_t maxlen) {
    ssize_t n, rc;
    char c, *buffer;
    buffer = vptr;
    for ( n = 1; n < maxlen; n++ ) {	
		if ( (rc = read(sockd, &c, 1)) == 1 ) {
	    	*buffer++ = c;
	    	if ( c == '\n' ) {
	    		break;
	    	}
		} else {
			if ( rc == 0 ) {
	    		if ( n == 1 ) {
					return 0;
				} else {
	    			break;	
	    		}
	    	} else {
	    		if ( errno == EINTR ) {
	    			continue;
	    			return -1;
	    		}
			}
    	}
    }
    *buffer = 0;
    return n;
}
 
/* Functie ce returneaza nr de aparitii pt delim */
int getSymbolNr(char* hostName, char delim) {
	int nr = 0;
	char *pch = strchr(hostName, delim);

  	while (pch != NULL) {
    		nr++;
    		pch = strchr(pch + 1, delim);
  	}
  	return nr; 
}

/* Functie ce returneaza calea */
char* getPath(char* host) {
	char* path = (char*)malloc (BUFLEN * sizeof (char));
	host = strtok(NULL, "/");

	sprintf(path, "%s", "");

	while(host != NULL && strcmp(host, " HTTP")) {
		strcat(path, "/");

		strcat(path, host);
		host = strtok(NULL, "/");
	}

	if(!strcmp(path, "")) {
		sprintf(path, "%s", "/");
	}

	return path;
}

/* Functie ce returneaza calea, punand . in loc de / */
char* getPathName(char* path) {
	char* aux1 = (char*)malloc (BUFLEN * sizeof (char));
   	memset(aux1, 0, strlen(path));
   	memcpy(aux1, path, strlen(path));

   	char* pathName = (char*)malloc (BUFLEN * sizeof (char));

   	char* aux2 = (char*)malloc (BUFLEN * sizeof (char));
   	aux2 = strtok(aux1, "/");

   	char* aux3 = (char*)malloc (BUFLEN * sizeof (char));

   	while (aux2 != NULL) {
   		sprintf(aux3, "_%s", aux2);
   		strcat(pathName, aux3);

   		aux2 = strtok(NULL, "/");
   	}

   	return pathName;
}

/* Functie ce realizeaza parsarea comenzii primite, obtinandu-se host, port si path*/
char* getPathHostPort(char* cmd, int socket, char sendbuf[], char* hostName, int* httpPort) {
	int n = 0;
	const char *delim = " \n"; /* Delimitatorii folositi pentru strtok */
	
	char* path = (char*)malloc (BUFLEN * sizeof (char));
	char* host = strtok(NULL, delim);

	const char *ptr = strchr(host, '/');
	if(!strcmp(cmd, "UNKNOWN")) {
		memset(hostName, 0, strlen(cmd));
		memcpy(hostName, cmd, strlen(cmd));
		return path;
	}

	if(ptr) {
   		int index = ptr - host;
   		if(index != 0) {

   			char* aux1 = (char*)malloc (BUFLEN * sizeof (char));
   			memset(aux1, 0, strlen(host));
   			memcpy(aux1, host, strlen(host));
   			
			//get nr of colons
   			int nrOfColons = getSymbolNr(aux1, ':');
   			if(nrOfColons == 1) {
   				//get name/path
   				char* aux2 = strtok(host, "/");
   				aux2 = strtok(NULL, "/");

   				memset(hostName, 0, strlen(aux2));
   				memcpy(hostName, aux2, strlen(aux2));

   				//get path
   				path = getPath(aux2);
   			} else {
   				//get name:port
   				char* aux2 = (char*)malloc (BUFLEN * sizeof (char));
   				memset(aux2, 0, strlen(host));
   				memcpy(aux2, host, strlen(host));
   				
   				aux2 = strtok(aux2, "/");
   				aux2 = strtok(NULL, "/");

   				//get name and port
   				char* aux3 = (char*)malloc (BUFLEN * sizeof (char));
   				memset(aux3, 0, strlen(aux2));
   				memcpy(aux3, aux2, strlen(aux2));

   				//get name
   				aux3 = strtok(aux3, ":");
   				memset(hostName, 0, strlen(aux3));
   				memcpy(hostName, aux3, strlen(aux3));

   				//get port
   				aux3 = strtok(NULL, ":");
   				*httpPort = atoi(aux3);

   				//get path
   				aux2 = strtok(host, "/");
   				aux2 = strtok(NULL, "/");
   				path = getPath(aux2);
   			}						
   		} else {
   			memset(path, 0, strlen(host));
   			memcpy(path, host, strlen(host));

   			//get second line
   			char newline[MAXLEN];
			memset(newline, 0, MAXLEN);

			int n = Readline(socket, newline, MAXLEN - 1);
			if (n <= 0) {
				return 0;
			}

			sprintf(sendbuf, "%s", newline);

   			char* aux2 = strtok(newline, "\n");
   			char* aux3 = (char*)malloc (BUFLEN * sizeof (char));

   			memset(aux3, 0, strlen(aux2));
   			memcpy(aux3, aux2, strlen(aux2));
   			
   			//get nr of colons
   			int nrOfColons = getSymbolNr(aux3, ':');
   			if(nrOfColons == 1) {
   				//HOST:hostName
   				aux2 = strtok(aux2, ":");
   				aux2 = strtok(NULL, ":");

   				//copy hostname
   				memset(hostName, 0, strlen(aux2));
   				memcpy(hostName, aux2, strlen(aux2));
   			} else {//we have a port
   				aux2 = strtok(aux2, ":");
   				aux2 = strtok(NULL, ":");

   				//copy hostname
   				memset(hostName, 0, strlen(aux2));
   				memcpy(hostName, aux2, strlen(aux2));

   				//get port
   				aux2 = strtok(NULL, ":");
   				*httpPort = atoi(aux2);
   			}
   		}
	}

	return path;
}

/* Functie ce trimite din cache catre client */
void sendFromFile(char* fileName, int sockcli) {
	FILE * fp;
    char* line = NULL;
    size_t len = 0;
    ssize_t read;

    fp = fopen(fileName, "rb");
    if (fp == NULL) {
        exit(EXIT_FAILURE);
    }

    while ((read = getline(&line, &len, fp)) != -1) {
    	send(sockcli, line, strlen(line), 0);
        //printf("Linia %s", line);
    }

    fclose(fp);

    if (line) {
        free(line);
    }
}

int doTheConnection(int sockcli, int httpPort, char* hostName) {
	struct sockaddr_in serv_addr;
	struct hostent *host;

	char* server_ip = (char*)malloc(100 * sizeof(char));
	int socksrv;
	
	host = gethostbyname(hostName);
    
    struct in_addr **ip;
    ip = (struct in_addr **)host->h_addr_list;
    
    strcpy(server_ip,inet_ntoa(*ip[0]));

    int err = 0;

	if ( (socksrv = socket(AF_INET, SOCK_STREAM, 0)) < 0 ) {
		printf("Eroare la  creare socket http.\n");
		err = 1;
		exit(-1);
 	}

 	memset((char *) &serv_addr, 0, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = INADDR_ANY;	// foloseste adresa IP a masinii
	serv_addr.sin_port = htons(httpPort);

	if (inet_aton(server_ip, & serv_addr.sin_addr) <= 0) {
   		printf("Adresa IP invalida.\n");
   		err = 1;
    	exit(-1);
    }
  	
  	if (connect(socksrv, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0 ) {
    	printf("Eroare la conectare server\n");
    	err = 1;
    	exit(-1);
  	}

  	if (err == 1) {
  		char* notFound = "HTTP/1.0 404 Not Found\r\n\r\n";
  		send(sockcli, notFound, strlen(notFound), 0);
  		return 0;
  	}

	return socksrv;
}

int exchange(int sockcli) {
	
	char buffer[MAXBUFLEN];
	memset(buffer, 0, MAXBUFLEN);
	
	char line[MAXLEN];
	memset(line, 0, MAXLEN);
	
	char sendbuf[MAXLEN]; 
    char recvbuf[MAXLEN];

    const char *delim = " \n"; /* Delimitatorii folositi pentru strtok */

    int n = Readline(sockcli, line, MAXLEN -1);
    if (n <= 0) {
    	return 0;
    }

    sprintf(sendbuf, "%s", line);

    char* hostName = (char*)malloc(BUFLEN * sizeof (char));
    char* path = (char*)malloc(BUFLEN * sizeof(char));

	char* cmd = strtok(line, delim);
	int httpPort = 80;
	path = getPathHostPort(cmd, sockcli, sendbuf, hostName, &httpPort);

	int socksrv = doTheConnection(sockcli, httpPort, hostName);
  	
  	while(1) {
		int n = Readline(sockcli, line, MAXLEN - 1);
		strcat(sendbuf, line);

		if (n < 4) {
			break;
		}
	}

	int no_cache = 0;

	if (strstr(sendbuf, "POST")) {
		return 0;
		no_cache = 1;

		int nrbytes;

		char buff[BUFLEN];
		memset(buff, 0, BUFLEN);
			
		while (1) {
			nrbytes = recv(sockcli, buff, BUFLEN, 0);
				
			if (nrbytes > 0) {
				strcat(sendbuf, buff);
			} else {
				break;
			}
		}
	}

	//send to server
	send(socksrv, sendbuf, strlen(sendbuf), 0);

	char srvline[MAXLEN];
	memset(srvline, 0, MAXLEN);

	Readline(socksrv, srvline, MAXLEN - 1);

	char copyline[MAXLEN];
	memset(copyline, 0, strlen(srvline));
	memcpy(copyline, srvline, strlen(srvline));

	char* aux = strtok(copyline, " ");
	aux = strtok(NULL, " ");
	
	int cache;

	if (!strcmp(aux, "200")) {
		if (no_cache == 0) {
			char fileName[BUFLEN];
			char* pathName = (char*)malloc (BUFLEN * sizeof (char));

			if (!strcmp(path, "/")) {
				pathName = "_";
			} else {
				pathName = getPathName(path);
			}

			sprintf(fileName, "%s%s.log", hostName, pathName);

			if( access( fileName, F_OK ) != -1 ) {
				// file exists
				sendFromFile(fileName, sockcli);
				close(socksrv); 
				return 0;
			}

    		// file doesn't exist
    		cache = open(fileName, O_WRONLY | O_CREAT | O_TRUNC, 0644);

    		if (write(cache, srvline, strlen(srvline)) != strlen(srvline)) {
       	    	error("Eroare scriere cache");
        	}
		}

		send(sockcli, srvline, strlen(srvline), 0);
    	
    	/* Citesc headerele */
		while (1) {
			int n = Readline(socksrv, srvline, MAXLEN - 1);
			if (!strcmp(srvline, "\r\n")) {
				if (no_cache == 0) {
					if (write(cache, srvline, strlen(srvline)) != strlen(srvline)) {
                		error("Eroare scriere cache");
            		}
				}
				send(sockcli, srvline, strlen(srvline), 0);
				break;
			}
				
			send(sockcli, srvline, strlen(srvline), 0);
			
			if(no_cache == 0) {
				if (write(cache, srvline, strlen(srvline)) != strlen(srvline)) {
                	error("Eroare scriere cache");
            	}

				char* aux = strtok(srvline, ": ");

				if (!strcmp(line, "Cache-Control")) {
					aux = strtok(NULL, ": ");

					if (!strcmp(aux, "no-cache") || !strcmp(aux, "private")) {
						no_cache = 1;
					}
				} else {
					if (!strcmp(aux, "Pragma")) {
						aux = strtok(NULL, ": ");
						
						if(!strcmp(aux, "no-cache")) {
							no_cache = 1;
						}
					}
				}
			}
		//sfarsit while
		}

		int nbytes = 1;
		/* Citesc restul */
		while (nbytes != 0) {
			nbytes = Readline(socksrv, recvbuf, MAXLEN - 1);
			recvbuf[nbytes] = 0;

			send(sockcli, recvbuf, nbytes, 0);

			if (no_cache == 0) {
				if (write(cache, recvbuf, strlen(recvbuf)) != strlen(recvbuf)) {
            		error("Eroare scriere cache");
       			}
			}
		}
	//sfarsit if 200
	}

	close(socksrv);   
	return 0;
}

int main(int argc, char *argv[])
{
     int sockfd, newsockfd, portno, clilen;
 
     struct sockaddr_in serv_addr, cli_addr;
     int n, i;

     fd_set read_fds;	//multimea de citire folosita in select()
     fd_set tmp_fds;	//multime folosita temporar 
     int fdmax;		//valoare maxima file descriptor din multimea read_fds

     if (argc < 2) {
         fprintf(stderr,"Usage : %s <port>\n", argv[0]);
         exit(1);
     }

     //golim multimea de descriptori de citire (read_fds) si multimea tmp_fds 
     FD_ZERO(&read_fds);
     FD_ZERO(&tmp_fds);
     
     sockfd = socket(AF_INET, SOCK_STREAM, 0);
     if (sockfd < 0) {
        error("ERROR opening socket");
     }
     portno = atoi(argv[1]);

     memset((char *) &serv_addr, 0, sizeof(serv_addr));
     serv_addr.sin_family = AF_INET;
     serv_addr.sin_addr.s_addr = INADDR_ANY;	// foloseste adresa IP a masinii
     serv_addr.sin_port = htons(portno);
     
     if (bind(sockfd, (struct sockaddr *) &serv_addr, sizeof(struct sockaddr)) < 0) {
              error("ERROR on binding");
     }
     listen(sockfd, MAX_CLIENTS);

     //adaugam noul file descriptor (socketul pe care se asculta conexiuni) in multimea read_fds
     FD_SET(sockfd, &read_fds);
     fdmax = sockfd;

     // main loop
	while (1) {
		tmp_fds = read_fds; 
		if (select(fdmax + 1, &tmp_fds, NULL, NULL, NULL) == -1) 
			error("ERROR in select");
		for(i = 0; i <= fdmax; i++) {
			if (FD_ISSET(i, &tmp_fds)) {
				if (i == sockfd) {
					// a venit ceva pe socketul inactiv(cel cu listen) = o noua conexiune
					// actiunea serverului: accept()
					clilen = sizeof(cli_addr);
					if ((newsockfd = accept(sockfd, (struct sockaddr *)&cli_addr, &clilen)) == -1) {
						error("ERROR in accept");
					} else {
						//adaug noul socket intors de accept() la multimea descriptorilor de citire
						FD_SET(newsockfd, &read_fds);
						if (newsockfd > fdmax) { 
							fdmax = newsockfd;
						}
						printf("New connection\n");
					}
					//printf("Noua conexiune de la %s, port %d, socket_client %d\n ", inet_ntoa(cli_addr.sin_addr), ntohs(cli_addr.sin_port), newsockfd);
				} else {
					// am primit date pe unul din socketii cu care vorbesc cu clientii
					//actiunea serverului: recv()
					n = exchange(i);
					if (n == 0) {
						//conexiunea s-a inchis
						printf("proxy: socket %d hung up\n", i);
					} else {
						error("ERROR in recv");
					}
					close(i); 
					FD_CLR(i, &read_fds); // scoatem din multimea de citire socketul pe care

				} 
			}
		}
     }
     close(sockfd);
   
     return 0; 
}