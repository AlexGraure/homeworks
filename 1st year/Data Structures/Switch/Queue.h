//Graure Marius-Alexandru
//315CB
#ifndef __QUEUE_H_INCLUDED
#define __QUEUE_H_INCLUDED

#include "List.h"

typedef struct 
{
	TLG ult;
	int nr_elem;
	size_t elem_size;
} Queue, *AQueue;

AQueue InitQ(size_t elem_size);

void InsQ(AQueue q, void *elem);
void InsNSort(AQueue q, void *elem, int (*compare)(void *, void *));
void *DelQ(AQueue q);
void *ExtractElem(AQueue q, void *elem, int (*compare)(void *, void*));
int DelElem(AQueue q, void *elem, int (*compare)(void *, void*));
void PrintQ(AQueue, void (*printElem)(void *));

#endif 
