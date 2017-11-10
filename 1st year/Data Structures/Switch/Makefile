CC=gcc
CFLAGS=-g -Wall -Wextra 

build: List.o Stack.o Queue.o main.c
	$(CC) $^ $(CFLAGS) -o tema2

List.o: List.c
Stack.o: Stack.h Stack.c
Queue.o: Queue.h Queue.c

run: build
	./tema2 input output > outputt
clean:
	rm tema2 *.o