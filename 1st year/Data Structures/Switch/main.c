//Graure Marius-Alexandru
//315CB
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "List.h"
#include "Stack.h"
#include "Queue.h"
#include "Switch.h"

#define BUFFER_SIZE 500

/* Aceasta functie este folosita pentru a converti o adresa ip din sir de caractere in valoare intreaga */
unsigned int convert(char *ip)
{
	const char *delim = "."; /* Delimitatorii folositi pentru strtok */
	char *copy = strdup(ip); /* Salveaza o copie a adresei pentru a nu modifica valoarea initiala */
	unsigned int res = 0;
	int i;
	/*
		Se parcurge sirul de caractere si se extrage fiecare grup de cifre, dupa care valoarea acestuia este 
		shiftata in numarul initial 
	 */
	res |= atoi(strtok(copy, delim));
	for (i = 0; i < 3; i++)
	{
		res <<= 8;
		res |= atoi(strtok(NULL, delim));
	}

	free(copy);
	return res;
}

/* 
	Functie folosita pentru compararea a doua elemente de tip Switch din punct de vedere al id-ului. 
	Aceasta functie este folosita pentru sortarea unei stive
 */
int compareElem(void *e1, void *e2)
{
	Switch *l1 = e1, *l2 = e2;
	return l1->id - l2->id;
}

/*
	Functie folosita pentru compararea a doua elemente de tip Switch din punctul de vedere al adresei.
	Aceasta functie este folosita pentru sortarea unei cozi
 */
int compareQ(void *e1, void *e2)
{
	Switch *l1 = e1, *l2 = e2;
	return convert(l1->address) - convert(l2->address);	
}

/*
	Functie folosita pentru eliberarea memoriei unui element de tip Switch
 */
void distrElem(void *elem)
{
	Switch *l = elem;
	free(l->address);
	free(l->name);
}

/*
	Functie folosita pentru afisarea completa a infomatiilor pe care un Switch le contine
*/
void printElem(void *elem)
{
	Switch *l = elem;
	printf("%d %s %s\n", l->id, l->address, l->name);
}

/*
	Functie folosita pentru afisarea id-ului unui Switch
*/
void printId(void *elem)
{
	Switch *l = elem;
	printf("%d ", l->id);
}

int main(int argc, char const *argv[])
{
	/* Verifica daca numarul de argumente este corect */
	if (argc != 3)
	{
		fprintf(stderr, "Mod de rulare: %s in_file out_file\n", argv[0]);
		return -1;
	}

	int i;

	const char *input = argv[1];
	const char *output = argv[2];

	/* Deschide fisierul de intrare */
	FILE *in = fopen(input, "r");
	if (!in)
	{
		fprintf(stderr, "Fisierul %s nu a putut fi deschis pentru citire\n", input);
		return -1;
	}
	/* Redirecteaza iesirea catre fisierul primit ca parametru */
	if(!freopen(argv[2], "w", stdout))
	{
		fprintf(stderr, "Fisierul %s nu a putut fi deschis pentru scriere\n", output);
		return -1;
	}

	int number_of_stacks; /* Numarul de stive */
	char *buffer = malloc(BUFFER_SIZE * sizeof(char));
	fgets(buffer, BUFFER_SIZE, in);

	number_of_stacks = atoi(buffer);

	AQueue queue = InitQ(sizeof(Switch)); /* Initializeaza coada */
	AStack *stacks = calloc(number_of_stacks, sizeof(AStack)); /* Aloca vectorul de stive */
	for (i = 0; i < number_of_stacks; i++)
		stacks[i] = InitS(sizeof(Switch)); /* Initializeaza toate stivele */

	const char *delim = " \n"; /* Delimitatorii folositi pentru strtok */
	while(!feof(in))
	{
		memset(buffer, 0, BUFFER_SIZE);
		fgets(buffer, BUFFER_SIZE, in);

		/* Extrage comanda din buffer */
		char *cmd = strtok(buffer, delim);
		if (cmd == NULL)
			break;

		int stack_id = 0;

		if (!strcmp(cmd, "add"))
		{
			int id = atoi(strtok(NULL, delim));
			char *name = strtok(NULL, delim);
			char *ip = strtok(NULL, delim);
			char *mode = strtok(NULL, delim);
			
			Switch *s = malloc (sizeof(Switch));
			s->id = id;

			s->name = malloc(strlen(name) + 1);
			memcpy(s->name, name, strlen(name) + 1);

			s->address = malloc(strlen(ip) + 1);
			memcpy(s->address, ip, strlen(ip) + 1);

			if (!strcmp(mode, "STACK"))
			{
				stack_id = atoi(strtok(NULL, delim));
				char *type = strtok(NULL, delim);
				/* Adauga elementul in mod Baza */
				if (!strcmp(type, "BASE"))
					PushBase(stacks[stack_id], (void *)s, compareElem);
				else /* Adauga elementul in stiva si sorteaza stiva */
					PushNSort(stacks[stack_id], (void *)s, compareElem);
			}
			else if (!strcmp(mode, "SINGLE"))
				InsNSort(queue, s, compareQ); /* Adauga elementul in coada si apoi sorteaza coada */

			free(s);
		}
		else if (!strcmp(cmd, "del"))
		{
			int id = atoi(strtok(NULL, delim));

			/* Caut in coada */
			Switch *s = malloc (sizeof(Switch));
			s->id = id;
			/* Verifica daca elementul se regaseste in coada. Daca da atunci acesta va fi eliminat */
			if (!DelElem(queue, s, compareElem))
			{
				/*
					Din moment ce elementul nu se regaseste in coada atunci el exista in una dintre stive.
					Prin urmare daca elementul este gasit atunci este eliminat. Din moment ce lista este generica
					ea are nevoie de o functie de comparare intre elemente pentru a determina daca doua elemente
					sunt egale
				*/
				for (i = 0; i < number_of_stacks; i++)
					if (DeleteElem(stacks[i], s, compareElem))
						break;
			}
		}
		else if (!strcmp(cmd, "set"))
		{
			int id = atoi(strtok(NULL, delim));
			char *mode = strtok(NULL, delim);
			void *elem;
			/* Din moment ce compararea se face numai in functie de id se creaza un Switch ce contine doar un id */
			Switch *s = malloc (sizeof(Switch));
			s->id = id;

			/* Cauta elementul cu id-ul dat intai in coada apoi in vectorul de stive */
			if (!(elem = ExtractElem(queue, s, compareElem)))
			{
				for (i = 0; i < number_of_stacks; i++)
					if ((elem = ExtractElemS(stacks[i], s, compareElem)))
						break;
			}
			/* Elementul cautat a fost scos din structura anterioara, si va fi inserat in noua structura */
			if (!strcmp(mode, "STACK"))
			{
				stack_id = atoi(strtok(NULL, delim));
				char *type = strtok(NULL, delim);
				if (!strcmp(type, "BASE"))
					PushBase(stacks[stack_id], (void *)elem, compareElem);
				else
					PushNSort(stacks[stack_id], (void *)elem, compareElem);
			}
			else if (!strcmp(mode, "SINGLE"))
				InsNSort(queue, elem, compareQ);
		}
		else if (!strcmp(cmd, "ipmin"))
		{
			/* In cazul in care coada este goala atunci ip-ul minim este 0 in caz contrar este chiar varful cozii */
			if(queue->nr_elem == 0)
				printf("ipmin=0\n");
			else
				printf("ipmin=%u\n", convert(((Switch *)queue->ult->info)->address));
		}
		else if (!strcmp(cmd, "show"))
		{
			printf("{");
			PrintQ(queue, printId);
			printf("}\n");
			for (i = 0; i < number_of_stacks; i++)
			{
				printf("%d:\n", i);
				PrintS(stacks[i], printElem);
			}
			printf("\n");
		}
		else
			fprintf(stderr, "UNKOWN COMMAND %s", cmd);
	}

	/* Elibereaza memoria */
	free(stacks);
	free(buffer);
	fclose(in);
	return 0;
}
