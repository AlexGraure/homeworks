CC=gcc
LIBSOCKET=-lnsl
CCFLAGS=-Wall -g
PRX=httpproxy

all: $(PRX)

$(PRX):$(PRX).c
	$(CC) -o $(PRX) $(LIBSOCKET) $(PRX).c
	
clean:
	rm -f *.o *~ *.log
	rm -f $(PRX)
