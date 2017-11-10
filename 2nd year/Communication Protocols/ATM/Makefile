CC=gcc
LIBSOCKET=-lnsl
CCFLAGS=-Wall -g
SRV=server
SRV=server
CLT=client
FILE=userData

build: $(SRV) $(CLT)

$(SRV):$(SRV).c
	$(CC) -o $(SRV) $(LIBSOCKET) $(SRV).c

$(CLT):	$(CLT).c
	$(CC) -o $(CLT) $(LIBSOCKET) $(CLT).c
	
clean:
	rm -f *.o *~ *.log
	rm -f $(SRV) $(CLT)
	

