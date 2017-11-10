//GRAURE MARIUS-ALEXANDRU
//325CB

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h> 
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#define BUFLEN 256

void error(char *msg)
{
    perror(msg);
    exit(0);
}

int main(int argc, char *argv[])
{
	int sockfd, n;
    struct sockaddr_in serv_addr;
    struct hostent *server;

    char buffer[BUFLEN];
    if (argc < 3) {
       fprintf(stderr,"Usage %s server_address server_port\n", argv[0]);
       exit(0);
    }  
    //obtin pidul si creez fisierul de log
    char fileName[BUFLEN];
    int ID = getpid();
    sprintf(fileName, "client-%d.log", ID);
    int fileLOG = open(fileName,O_WRONLY | O_CREAT | O_TRUNC, 0644);

    if(fileLOG == -1){
        fprintf(stderr,"File could not be created\n");
        exit(1);
    }

	sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0) {
        error("ERROR opening socket");
    }
    
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(atoi(argv[2]));
    inet_aton(argv[1], &serv_addr.sin_addr);
    
    
    if (connect(sockfd,(struct sockaddr*) &serv_addr,sizeof(serv_addr)) < 0) {
        error("ERROR connecting");    
    }
    
    fd_set read_fds;    //multimea de citire folosita in select()

    //golim multimea de descriptori de citire (read_fds) si multimea tmp_fds 
    FD_ZERO(&read_fds);
    FD_SET(0, &read_fds);

    const char *delim = " \n"; /* Delimitatorii folositi pentru strtok */

    while(1) {        
    	FD_SET(0, &read_fds);
        FD_SET(sockfd, &read_fds);
        if (select(sockfd + 1, &read_fds, NULL, NULL, NULL) == -1) {
            error("ERROR in select");
        }

        if (FD_ISSET(0, &read_fds)) {

      		//citesc de la tastatura
        	memset(buffer, 0 , BUFLEN);
        	fgets(buffer, BUFLEN-1, stdin);
            //scriu in fisierul de log comanda primita
            if (write(fileLOG, buffer, strlen(buffer)) != strlen(buffer)) {
                error("Eroare");
            }
            //trimit prin TCP la server
            n = send(sockfd, buffer, strlen(buffer), 0);
            if(n < 0) {
                error("Eroare");
            }
            //daca primesc quit de la server, opresc clientul
            char* quit = strtok(buffer, delim);
            if(!strcmp(quit, "quit")) {
                break;
            }           
        }

        if (FD_ISSET(sockfd, &read_fds)) {
            memset(buffer, 0 , BUFLEN);
            int n = recv(sockfd, buffer, BUFLEN, 0);
            
            if(n > 0 ) {
            	//verific daca s-a dat comanda quit
                if(!strcmp(buffer, "quit")) {
                    printf("%s\n", buffer);
                    break;
                }
		//scriu in fisierul de log
                if (write(fileLOG, buffer, strlen(buffer)) != strlen(buffer)) {
                    error("Eroare");
                }
            }

        }
    }
	return 0;
}
