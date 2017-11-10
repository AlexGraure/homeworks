//Graure Marius-Alexandru
//35CB
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char const *argv[])
{
	/* Verifica daca numarul de argumente este corect */
	if (argc != 4 )
	{
		fprintf(stderr, "Mod de rulare: %s tm.in tape.in tape.out\n", argv[0]);
		return -1;
	}

	const char *input1 = argv[1];
	const char *input2 = argv[2];
	const char *output = argv[3];

	/* Deschide fisierele de intrare */
	FILE *tm = fopen(input1, "r");
	if (!tm)
	{
		fprintf(stderr, "Fisierul %s nu a putut fi deschis pentru citire\n", input1);
		return -1;
	}
	
	FILE *tape = fopen(input2, "r");
	if (!tape)
	{
		fprintf(stderr, "Fisierul %s nu a putut fi deschis pentru citire\n", input2);
		return -1;
	}
	
	/* Redirecteaza iesirea catre fisierul primit ca parametru */
	
	if(!freopen(output, "w", stdout))
	{
		fprintf(stderr, "Fisierul %s nu a putut fi deschis pentru scriere\n", output);
		return -1;
	}

	int N, i;
	fscanf(tm,"%d ",&N);							
	int states[N];
	char c1,c2;
	
	for(i = 0; i < N; i++){
		fscanf(tm,"%c%d%c",&c1,&states[i],&c2);
	}
	
	int M;
	fscanf(tm,"%d ",&M);							
	int fstates[M];
	for(i = 0; i < M; i++){
		fscanf(tm,"%c%d%c",&c1,&fstates[i],&c2);
	}
	
	int istate;
	fscanf(tm,"%c%d%c",&c1,&istate,&c2);

	int P;
	fscanf(tm,"%d ",&P);							
	int current_state, next_state[P], state[P];
	char read_symbol[P], write_symbol[P], direction[P];
	for(i = 1; i <= P; i++){
		fscanf(tm,"%c%d%c ",&c1,&state[i],&c2);
		fscanf(tm,"%c ",&read_symbol[i - 1]);
		fscanf(tm,"%c%d%c ",&c1,&next_state[i],&c2);
		fscanf(tm,"%c ",&write_symbol[i - 1]);
		fscanf(tm,"%c",&direction[i]);
		fscanf(tm,"%c",&c1);
	}
	
	char *the_tape = malloc(10000 * sizeof(char));
	fscanf(tape,"%s",the_tape);
	int j = 1, index = 1, OK = 1;
	current_state = istate;
	while(OK == 1){

		if(current_state == state[j] && the_tape[index] == read_symbol[j - 1]){
			current_state = next_state[j];
			the_tape[index] = write_symbol[j - 1];
			if(direction[j] == 'R'){
				index++;
			} 
			if(direction[j] == 'L'){
				index--;
			}
			j = 1;
			for(i = 0; i < M; i++){
				if(current_state == fstates[i])
					OK = 0;
			}
		}
		else {
			j++;
		}

	}
	printf("%s\n", the_tape);
	free(the_tape);
	fclose(tm);
	fclose(tape);
	return 0;
}
