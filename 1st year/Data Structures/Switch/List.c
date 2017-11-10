//Graure Marius-Alexandru
//315CB
#include "List.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/** Functia de adaugare a unui element in lista */
int InsLG(ALG aL, void *elem, size_t elem_size)
{
    TLG aux = (TLG) malloc(sizeof(TCelulaG));

    if (!aux)
        return 0;
    aux->info = malloc(elem_size);
    if (!aux->info)
    {
        free(aux);
        return 0;
    }

    memcpy(aux->info, elem, elem_size);
    aux->urm= *aL;
    *aL= aux;

    return 1;
}

/** Inserarea la sfarsitul unei liste */
int InsLGSF(ALG aL, void *elem, size_t elem_size)
{
    TLG aux = (TLG) malloc(sizeof(TCelulaG));
    if (!aux)
        return 0;

    aux->info = malloc(elem_size);
    aux->urm = NULL;

    if (!aux->info)
    {
        free(aux);
        return 0;
    }

    memcpy(aux->info, elem, elem_size);

    while (*aL)
    {
        aL = &(*aL)->urm;
    }

    *aL = aux;

    return 1;
}

/** Functia de distrugere a listei - elibereaza elementele si list */
void DistrugeLG(ALG aL, void (*delete_elem)(void*))
{
    TLG aux;
    while(*aL)
    {
        aux = *aL;
        *aL= aux->urm;
        (*delete_elem)(aux->info);
        free(aux->info);
        free(aux);
    }
}

/** Functia pentru afisarea listei */
void printList(TLG list, void (*afisElem)(void *))
{
    while (list)
    {
        (*afisElem)(list->info);
        list = list->urm;
    }
}
