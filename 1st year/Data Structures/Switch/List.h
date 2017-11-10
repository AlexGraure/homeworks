//Graure Marius-Alexandru
//315CB
#ifndef __LIST_H_INCLUDED
#define __LIST_H_INCLUDED

#include <stdio.h>
#include <stdlib.h>

#define VidaL(L) ((L) == NULL)

typedef struct celulag
{
    struct celulag *urm; /* adresaurmatoareicelule*/
    void *info; /* informatie*/
} TCelulaG, *TLG, **ALG;

int InsLG(ALG, void *, size_t);
int InsLGSF(ALG aL, void *elem, size_t elem_size);
void DistrugeLG(ALG, void (*delete_elem)(void*));

void printList(TLG, void (*print_elem)(void *));
#endif 
