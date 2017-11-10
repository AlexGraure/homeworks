//Graure Marius-Alexandru
//315CB
#include "Stack.h"
#include "List.h"

/* Functie pentru alocarea initiala a stivei */
AStack InitS(size_t elem_size)
{
	AStack res = calloc(1, sizeof(Stack));
	if (!res)
		return NULL;

	res->elem_size = elem_size;
	res->nr_elem = 0;
	return res;
}

/* Functie folosita pentru adaugarea unui element in stiva */
void Push(AStack st, void *elem)
{
	InsLG(&(st->vf), elem, st->elem_size);
	st->nr_elem++;
}

/* Functie folosita pentru extragerea unui element din stiva */
void *Pop(AStack st)
{
	TLG aux = st->vf;
	if (!aux)
		return NULL;
	st->vf = st->vf->urm;

	void *res = aux->info;
	free(aux);

	st->nr_elem--;
	return res;
}

/*
	Seterge un element specific. Primeste o functie de comparare pentru a determina doua elemente sunt egale.
	Functia intoarce 1 daca elementul a fost sters, 0 in caz contrar.
 */
int DeleteElem(AStack st, void *elem, int (*compare)(void *, void *))
{
	AStack aux_s = InitS(st->elem_size);
	int deleted = 0;
	while(st->nr_elem > 0)
	{
		void *e = Pop(st);
		if (compare(e, elem) == 0)
		{
			deleted = 1;
			break;		
		}
		Push(aux_s, e);
	}

	while(aux_s->nr_elem)
	{
		void *elem = Pop(aux_s);
		Push(st, elem);
	}

	return deleted;
}

/* 
	Functie folosita pentru stergerea unui element specific din coada. Se scot pe rand elementele din coada
	si se compara cu elementul cautat. Functia intoarce elementul sters.
*/
void *ExtractElemS(AStack st, void *elem, int (*compare)(void *, void *))
{
	AStack aux_s = InitS(st->elem_size);
	int deleted = 0;
	void *res = NULL;
	while(st->nr_elem > 0)
	{
		void *e = Pop(st);
		if (compare(e, elem) == 0)
		{
			res = e;
			deleted = 1;
			break;		
		}
		Push(aux_s, e);
	}

	while(aux_s->nr_elem)
	{
		void *elem = Pop(aux_s);
		Push(st, elem);
	}

	return res;
}

/* 	
	Functie ce insereaza un element intr-o stiva astfel incat aceasta sa fie sortata. 
	Functia primeste ca parametru un pointer la o functie de comparare intre doua elemente de tip void* 
 */
void PushNSort(AStack st, void *elem, int (*compare)(void *, void *))
{
	AStack aux_s = InitS(st->elem_size);
	/* Nu dorim sa modificam baza stivei */
	if (st->nr_elem == 0 || st->nr_elem == 1)
	{
		Push(st, elem);
		return;
	}
	int added = 0;
	while(st->nr_elem > 1)
	{
		void *e = Pop(st);
		if (compare(elem, e) < 0)
		{
			Push(st, e);
			Push(st, elem);
			added = 1;
			break;
		}
		Push(aux_s, e);
	}

	if (!added)
		Push(st, elem);

	while(aux_s->nr_elem)
	{
		void *elem = Pop(aux_s);
		Push(st, elem);
	}
}

/*  
	Adaugarea unui element ca fiind baza stivei. 
	Elementul ce reprezinta baza va fi scos si el si reinserat in ordine. 
 */
void PushBase(AStack st, void *elem, int (*compare)(void *, void *))
{
	AStack aux_s = InitS(st->elem_size);
	while(st->nr_elem > 0)
	{
		void *e = Pop(st);
		Push(aux_s, e);
	}

	Push(st, elem);

	while (aux_s->nr_elem)
	{
		void *e = Pop(aux_s);
		PushNSort(st, e, compare);
	}
}

/* 
	Functie folosita pentru afisarea stivei. Aceasta primeste un pointer catre o functie de afisare, folosita
	pentru afisarea elementului.
*/
void PrintS(AStack st, void (*print_elem)(void *))
{
	AStack aux_s = InitS(st->elem_size);
	while(st->nr_elem)
	{
		void *elem = Pop(st);
		print_elem(elem);
		Push(aux_s, elem);
	}

	while(aux_s->nr_elem)
	{
		void *elem = Pop(aux_s);
		Push(st, elem);
	}
}
