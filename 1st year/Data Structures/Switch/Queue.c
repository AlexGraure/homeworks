//Graure Marius-Alexandru
//315CB
#include "Queue.h"
#include "Stack.h"

/* Functie folosita pentru initializarea cozii */
AQueue InitQ(size_t elem_size)
{
	AQueue res = calloc(1, sizeof(AQueue));
	if (!res)
		return NULL;

	res->elem_size = elem_size;
	res->nr_elem = 0;
	res->ult = NULL;
	return res;
}

/* Functie ce insereaza un element intr-o coada. Functionalitatea acestui tip de inserare este cea generala */
void InsQ(AQueue q, void *elem)
{
	InsLGSF(&(q->ult), elem, q->elem_size);
	q->nr_elem++;
}

/* 	
	Functie ce insereaza un element intr-o coada astfel incat coada sa fie sortata. 
	Coada rezultata este un fel de coada de prioritati. Functia primeste ca parametru un pointer la o functie
	de comparare intre doua elemente de tip void* 
 */
void InsNSort(AQueue q, void *elem, int (*compare)(void *, void *))
{
	AQueue aux_s = InitQ(q->elem_size);
	int added = 0;
	while(q->nr_elem > 0)
	{
		/* Scoate elementele din coada */
		void *e = DelQ(q);
		/* Daca elementul nu a fost adaugat */
		if (!added && compare(elem, e) < 0)
		{
			InsQ(aux_s, elem);
			InsQ(aux_s, e);
			added = 1;
			continue;
		}
		InsQ(aux_s, e);
	}
	/* Daca elementul nu a fost inserat atunci inseram */
	if (!added)
		InsQ(aux_s, elem);

	/* Pune la loc elementele in coada */
	while(aux_s->nr_elem)
	{
		void *elem = DelQ(aux_s);
		InsQ(q, elem);
	}
}

/* Scoate primul element din coada */
void *DelQ(AQueue q)
{
	TLG aux = q->ult;
	if (!aux)
		return NULL;
	q->ult = q->ult->urm;

	void *res = aux->info;
	free(aux);

	/* Scade numarul de elemnte */
	q->nr_elem--;
	return res;
}

/* 
	Seterge un element specific. Primeste o functie de comparare pentru a determina doua elemente sunt egale.
	Functia intoarce 1 daca elementul a fost sters, 0 in caz contrar.
 */
int DelElem(AQueue q, void *elem, int (*compare)(void *, void*))
{
	AQueue aux_s = InitQ(q->elem_size);
	int deleted = 0;
	/* Scoare elementele din coada pe rand */
	while(q->nr_elem > 0)
	{
		void *e = DelQ(q);
		/* 
			Verifica daca elementul curent este egal cu elementul cautat. 
			Daca da atunci se va omite inserarea lui in coada auxiliara
		 */
		if (!deleted && compare(elem, e) == 0)
		{
			deleted = 1;
			continue;
		}
		InsQ(aux_s, e);
	}

	/* Pune elementele inapoi in coada initiala */
	while(aux_s->nr_elem)
	{
		void *elem = DelQ(aux_s);
		InsQ(q, elem);
	}

	return deleted;
}

/* 
	Functie folosita pentru stergerea unui element specific din coada. Se scot pe rand elementele din coada
	si se compara cu elementul cautat. Functia intoarce elementul sters.
*/
void *ExtractElem(AQueue q, void *elem, int (*compare)(void *, void*))
{
	AQueue aux_s = InitQ(q->elem_size);
	if (!aux_s)
	{
		fprintf(stderr, "Nu s-a putut aloca memorie\n");
		exit(0);
	}

	void *res = NULL;
	/* Scoate elemntele din coada */
	while(q->nr_elem > 0)
	{
		void *e = DelQ(q);
		
		if (compare(elem, e) == 0)
		{
			res = e;
			continue;
		}
		InsQ(aux_s, e);
	}
	/* Pune elementel inapoi in coada */
	while(aux_s->nr_elem > 0)
	{
		void *elem = DelQ(aux_s);
		InsQ(q, elem);
	}

	return res;
}

/*
	Functie folosita pentru afisarea elemntelor dintr-o coada. Aceasta functie primeste un pointer la o functie
	folosita pentru afisarea unui element
 */
void PrintQ(AQueue q, void (*print_elem)(void *))
{
	AQueue aux_s = InitQ(q->elem_size);
	while(q->nr_elem)
	{
		void *elem = DelQ(q);
		print_elem(elem);
		InsQ(aux_s, elem);
	}

	while(aux_s->nr_elem)
	{
		void *elem = DelQ(aux_s);
		InsQ(q, elem);
	}
}
