//Graure Marius-Alexandru
//315CB
#ifndef __STACK_H_INCLUDED
#define __STACK_H_INCLUDED

#include "List.h"

typedef struct {
	TLG vf;
	int nr_elem;
	size_t elem_size;
} Stack, *AStack;

AStack InitS(size_t elem_size);
void DistrS(AStack *st);

void *Pop(AStack st);
void PrintS(AStack st, void (*print_elem)(void *));

int DeleteElem(AStack st, void *elem, int (*compare)(void *, void *));
void *ExtractElemS(AStack st, void *elem, int (*compare)(void *, void *));

void Push(AStack st, void *elem);
void PushNSort(AStack st, void *elem, int (*compare)(void *, void *));
void PushBase(AStack st, void *elem, int (*compare)(void *, void *));

#endif // __STACK_H_INCLUDED
