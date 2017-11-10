//GRAURE MARIUS-ALEXANDRU
//325CB

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h> 
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include "Struct.h"

#define MAX_CLIENTS	5
#define BUFLEN 256
#
void error(char *msg)
{
    perror(msg);
    exit(1);
}

int main(int argc, char *argv[])
{
	int sockfd, newsockfd, portno, clilen;
	char buffer[BUFLEN];
	struct sockaddr_in serv_addr, cli_addr;
	int n, i, j;

	fd_set read_fds;	//multimea de citire folosita in select()
	fd_set tmp_fds;	//multime folosita temporar 
	int fdmax;		//valoare maxima file descriptor din multimea read_fds


	if (argc < 3) {
         fprintf(stderr,"Usage : %s port\n", argv[0]);
         exit(1);
     }

	const char *input = argv[2];
	/* Deschide fisierul de intrare */
	FILE *in = fopen(input, "r");
	if (!in)
	{
		fprintf(stderr, "Fisierul %s nu a putut fi deschis pentru citire\n", input);
		return -1;
	}
	
	int nrClienti = 0;
	fscanf (in, "%d", &nrClienti);
	//construiesc baza de date despre useri
	ATUser users = malloc(nrClienti * sizeof(TUser));
	
	for (i = 0; i < nrClienti; i++) {
		char* nume = (char*)malloc(12 * sizeof(char));
		fscanf (in, "%s", nume);
		
		char* prenume = (char*)malloc(12 * sizeof(char));
		fscanf(in, "%s", prenume);
		
		int nrCard = 0;
		fscanf (in, "%d", &nrCard);
		
		int pin = 0;
		fscanf (in, "%d", &pin);
		
		char* passwd = (char*)malloc(16 * sizeof(char));
		fscanf(in, "%s", passwd);
		
		float sold = 0;
		fscanf (in, "%f", &sold);
		
		users[i] = AlocUser(nume, prenume, nrCard, pin, passwd, sold);
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
	FD_SET(0, &read_fds);
	fdmax = sockfd;

	int credentials[100];
	int logged[100];
	for( i = 0; i < 100; i++) {
		credentials[i] = 0;
		logged[i] = 100 + 1;
	}

	const char *delim = " \n"; /* Delimitatorii folositi pentru strtok */
	int ok = 1;
	int exit = 0;
	// main loop
	while (1) {
		tmp_fds = read_fds; 
		if (select(fdmax + 1, &tmp_fds, NULL, NULL, NULL) == -1) {
			error("ERROR in select");
		}
	
		for(i = 0; i <= fdmax; i++) {

			if (FD_ISSET(i, &tmp_fds)) {
				if (i == sockfd) {
					// a venit ceva pe socketul inactiv(cel cu listen) = o noua conexiune
					// actiunea serverului: accept()
					clilen = sizeof(cli_addr);
					if ((newsockfd = accept(sockfd, (struct sockaddr *)&cli_addr, &clilen)) == -1) {
						error("ERROR in accept");
					} 
					else {
						//adaug noul socket intors de accept() la multimea descriptorilor de citire
						FD_SET(newsockfd, &read_fds);
						if (newsockfd > fdmax) { 
							fdmax = newsockfd;
						}
					}
				} else {
					if (i == STDIN_FILENO) {
						memset(buffer, 0 , BUFLEN);
        				fgets(buffer, BUFLEN-1, stdin);
        				char* buf = strtok(buffer, delim);
        				//daca primeste serverul quit, trimit quit tuturor clientilor
        				//si opresc serverul
            			if(!strcmp(buf, "quit")) {
           					for(int j = 1; j <= fdmax; j++) {
           						if (FD_ISSET(j, &read_fds) && j != sockfd) {
	           						n = send(j, buffer, strlen(buf), 0);
									if(n < 0) {
										error("ERROR writing to socket");
									}	
           						}
            				}
            				exit = 1;
               				break;
           				}  
					} else {
						// am primit date pe unul din socketii cu care vorbesc cu clientii
						//actiunea serverului: recv()
						memset(buffer, 0, BUFLEN);
						if ((n = recv(i, buffer, sizeof(buffer), 0)) <= 0) {
							if (n == 0) {
								//conexiunea s-a inchis
								printf("server: socket %d hung up\n", i);
							} else {
								error("ERROR in recv");
							}
							close(i); 
							FD_CLR(i, &read_fds); // scoatem din multimea de citire socketul pe care 
						} else { 
							char buf[BUFLEN];
							/* Extrage comanda din buffer */
            				char *cmd = strtok(buffer, delim);
            				if (cmd == NULL) {
                				break;
   		         			}

        	    			int id = logged[i - 1];
        	    			//serverul reactioneaza in functie de comanda primita
     		       			if (!strcmp(cmd, "login")) {     		  
            	    			int nrCard = atoi(strtok(NULL, delim));
								int pin = atoi(strtok(NULL, delim));  
        	            		sprintf(buf,"%s", checkPin(users, credentials, logged, nrClienti, nrCard, pin, i - 1));
            				} else {
            					if(!strcmp(cmd, "logout")) {
            						if(id == 101 || users[id]->logged == 0) {
            							sprintf(buf, "ATM> -1 : Clientul nu este autentificat\n");
           		 					} else {
            							logged[i - 1] = 101;
            							users[id]->logged = 0;
            							sprintf(buf, "ATM> Deconectare de la bancomat\n");
            						}
         		   				} else {
            						if(!strcmp(cmd, "listsold")) {
            							sprintf(buf, "ATM> %.2f\n", users[id]->sold);
            						} else {
            							if(!strcmp(cmd, "getmoney")) {
            								int money = atoi(strtok(NULL, delim));
            								sprintf(buf, "%s", getMoney(users[id], money));
            							} else {
            								if(!strcmp(cmd, "putmoney")) {
            									int money = atof(strtok(NULL, delim));
            									sprintf(buf, "%s", putMoney(users[id], money));
            								} else {
           		 								if(!strcmp(cmd, "unlock")) {

            									} else {
            										if(!strcmp(cmd, "quit")) {
            											users[id]->logged = 0;
            											ok = 0;
            											close(i); 
														FD_CLR(i, &read_fds); // scoatem din multimea de citire clientul i 
            										}
            									}
         		   							}
            							}
            						}
            					}
            				}
       		     			if(ok == 1) {
            					n = send(i, buf, strlen(buf), 0);
								if(n < 0) {
									error("ERROR writing to socket");
								}
            				}
						}
					} 
				} 
			}	
		}
		if(exit == 1) {
			break;
		}
    }
	close(sockfd);
	fclose(in);
	return 0;	
}
