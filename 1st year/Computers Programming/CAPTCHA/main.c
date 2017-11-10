#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "bmp_header.h"

typedef struct 
{
	int R, G, B;
}Color;

typedef struct 
{
	unsigned char B, G, R;
}CColor;

typedef struct 
{
	int x, y;
}Point;

/* 
	Functie ce primeste un sir de caractere ce contine 3 numere si intoarce o structura de tip Color
*/
void parse_color (char* str, Color* color)
{
	//int length = strlen (str);
	char *ptr;
	const char delim[] = " \n";

	ptr = strtok (str, delim);
	color->B = atoi (ptr);

	ptr = strtok (NULL, delim);
	color->G = atoi (ptr);

	ptr = strtok (NULL, delim);	
	color->R = atoi (ptr);	
}

/* 
	Functie ce primeste un sir de caractere ce contine cifre si intoarce un vector ce contine contine 
	valoarea 1 pe pozitia cifre daca aceasta a fost gasita 
*/
void parse_digits (char* str, int result[10])
{
	int i;
	for (i = 0; i <= 9; i++)
		result[i] = 0;	/* Initializeaza vectorul */

	char* ptr;
	const char delim[] = " \n";

	ptr = strtok (str, delim);
	while (ptr != NULL)
	{
		int digit = atoi (ptr);
		result[digit] = 1;
		ptr = strtok (NULL, delim); 
	}
}

/* 
	Functie folosita pentru afisarea imaginii	
*/
void print_image (CColor** img, int width, int height)
{
	int i;
	int j;
	for (i = 0; i < height; i++)
	{
		for (j = 0; j < width; j++)
		{
			char c = (img[i][j].R == 255 && img[i][j].G == 255 && img[i][j].B == 255) ? ' ' : 'X';
			printf("%c", c);
		}
		printf("\n");
	}
}

/*
	Functie folosita pentru scrierea imaginii. Aceasta primeste 
*/
void write_image (const char* file_name, CColor** image, int width, int height, struct bmp_fileheader header, struct bmp_infoheader info_header)
{
	int i;
	int j;
	int k;

	FILE* output = fopen (file_name, "wb");
	if (output == NULL)
	{
		printf ("Fisierul %s nu a putut fi deschis pentru scriere.\n", file_name);
		return;
	}

	fwrite (&header, sizeof(struct bmp_fileheader), 1, output);
	fwrite (&info_header, sizeof(struct bmp_infoheader), 1, output);
	char c = 0;	

	long offset = ftell (output);
	
	for (i = height - 1; i >= 0; i--)
	{
		for (j = 0; j < width; j++)
			fwrite (&(image[i][j]), sizeof (CColor), 1, output);

		long diff = (ftell (output) - offset) % 4;
		if (diff != 0)
			for (k = 0; k < 4-diff; k++)
				fwrite (&c, sizeof(char), 1, output);
	}
	fclose (output);
}

/*
	Functie folosita pentru compararea a 2 pixeli.
*/
int pixel_equals (CColor a, CColor b)
{
	return (a.R == b.R) && (a.G == b.G) && (a.B == b.B);
}	

int get_digit (int matrix[7][7], int allowed_missmatch)
{
	const int digits_as_matrix[10][7][7] = 
	{
		// 0
		{
			{0, 0, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 1, 0, 0, 0, 1, 0},
			{0, 1, 0, 0, 0, 1, 0},
			{0, 1, 0, 0, 0, 1, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 0, 0}
		},
		// 1
		{
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 1, 0},
			{0, 0, 0, 0, 0, 1, 0},
			{0, 0, 0, 0, 0, 1, 0},
			{0, 0, 0, 0, 0, 1, 0},
			{0, 0, 0, 0, 0, 1, 0},
			{0, 0, 0, 0, 0, 0, 0}
		}, 
		// 2
		{
			{0, 0, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 1, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 1, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 0, 0}
		}, 
		// 3
		{
			{0, 0, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 1, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 1, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 0, 0}
		},
		// 4		
		{
			{0, 0, 0, 0, 0, 0, 0},
			{0, 1, 0, 0, 0, 1, 0},
			{0, 1, 0, 0, 0, 1, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 1, 0},
			{0, 0, 0, 0, 0, 1, 0},
			{0, 0, 0, 0, 0, 0, 0}
		},
		// 5
		{
			{0, 0, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 1, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 1, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 0, 0}
		},
		// 6
		{
			{0, 0, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 1, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 1, 0, 0, 0, 1, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 0, 0}
		},
		// 7
		{
			{0, 0, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 1, 0},
			{0, 0, 0, 0, 0, 1, 0},
			{0, 0, 0, 0, 0, 1, 0},
			{0, 0, 0, 0, 0, 1, 0}, 
			{0, 0, 0, 0, 0, 0, 0}

		},
		// 8
		{
			{0, 0, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 1, 0, 0, 0, 1, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 1, 0, 0, 0, 1, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 0, 0}
		},
		// 9
		{
			{0, 0, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 1, 0, 0, 0, 1, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 1, 0},
			{0, 1, 1, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 0, 0}
		}
	};

	int i, j, k;
	for (k = 0; k <= 9; k++)
	{
		int m_digit = 1;
		int ok = 1;
		int missmatch = allowed_missmatch;
		
		for (i = 0; i < 7 && ok && missmatch >= 0; i++)
		{
			for (j = 0; j < 7 && ok && missmatch >= 0; j++)
			{
				if (matrix[i][j] != digits_as_matrix[k][i][j])
				{

					missmatch--;
					if (missmatch < 0)
					{
						m_digit = 0;
						ok = 0;
					}
				}
			}
		}
		
		if (m_digit && ok)
		{
			return k;
		}
	}

	/* Intoarce -1 pentru a semnala ca nu a fost gasita nici o cifra */
	return -1;
}

int main(int argc, char const *argv[])
{
	const char* input_file = "input.txt";
	FILE *input = fopen (input_file, "r");
	
	/* Verifica daca fisierul a fost deschis cu success. Daca nu opreste executia programului. */
	if (input == NULL)
	{
		printf ("Fisierul input.txt nu exista. Programul se va termina.\n");
		return -1;
	}
	int i;
	int j;
	char *line = NULL;
	char *captcha_file = NULL;
	char *captcha_bonus_file = NULL;
	size_t line_length = 0;
	Color *color = NULL;

	/* Citeste prima linie ce contine numele fisierului captcha */
	getline (&line, &line_length, input);
	captcha_file = (char*) calloc (strlen (line) + 1, sizeof (char));
	strncpy (captcha_file, line, strlen (line) - 1);

	/* Citeste culoarea pentru taskul 1 */
	color = (Color *) calloc (1, sizeof (Color));
	getline (&line, &line_length, input);
	parse_color (line, color);
	
	/* Citeste linia ce contine cifrele ce vor fi eliminate la taskul 3 */
	getline (&line, &line_length, input);
	int to_be_removed[10];	/* Vom folosi acest vector pentru a marca cifrele ce vor fi eliminate la taskul 3 */
	parse_digits (line, to_be_removed);
		
	/* Citeste linia cu fisierul pentur bonus*/
	line = NULL;
	getline (&line, &line_length, input);
	captcha_bonus_file = (char*) calloc (strlen (line) + 1, sizeof (char));
	strncpy (captcha_bonus_file, line, strlen(line) - 1);

	fclose (input);

	char *captcha_file1 = NULL;
	char *captcha_file2 = NULL;
	char *captcha_file3 = NULL;
	char *captcha_bonus = NULL;

	/* Defineste extensiile pentru fiecare fisier */
	const char ext[] = ".bmp";
	const char ext1[] = "_task1.bmp";
	const char ext2[] = "_task2.txt";
	const char ext3[] = "_task3.bmp";
	const char extB[] = "_bonus.txt";

	/* Aloca memorie pentru vectorii ce salveaza numele fisierelor */
	captcha_file1 = (char*) calloc (strlen (captcha_file) + strlen (ext1) + 1, sizeof (char));
	captcha_file2 = (char*) calloc (strlen (captcha_file) + strlen (ext2) + 1, sizeof (char));
	captcha_file3 = (char*) calloc (strlen (captcha_file) + strlen (ext3) + 1, sizeof (char));
	captcha_bonus = (char*) calloc (strlen (captcha_file) + strlen (extB) + 1, sizeof (char));

	/* Creaza numele fisierelor pentru fiecare task */
	strncpy (captcha_file1, captcha_file, strlen (captcha_file) - strlen (ext));
	strcpy (captcha_file1 + strlen (captcha_file1), ext1);

	strncpy (captcha_file2, captcha_file, strlen (captcha_file) - strlen (ext));
	strcpy (captcha_file2 + strlen (captcha_file2), ext2);

	strncpy (captcha_file3, captcha_file, strlen (captcha_file) - strlen (ext));
	strcpy (captcha_file3 + strlen (captcha_file3), ext3);

	strncpy (captcha_bonus, captcha_bonus_file, strlen (captcha_bonus_file) - strlen (ext));
	strcpy (captcha_bonus + strlen (captcha_bonus), extB);

	/* Citeste imaginea initiala */
	struct bmp_fileheader header;
	struct bmp_infoheader info_header;

	FILE* image = fopen (captcha_file, "rb");
	if (image == NULL)
	{
		printf ("Fisierul %s nu a putut fi deschis. Programul se va termina.\n", captcha_file);
		return -1;
	}

	fread (&header, sizeof (struct bmp_fileheader), 1, image);
	fread (&info_header, sizeof (struct bmp_infoheader), 1, image);

	CColor **bitmap, **bitmap_copy;
	bitmap = (CColor**) calloc (info_header.height, sizeof (CColor*));
	bitmap_copy = (CColor**) calloc (info_header.height, sizeof (CColor*)); /* Furnizez o copie a matricei pentru taskul 1 */
	
	for (i = 0; i < info_header.height; i++)
	{
		bitmap[i] = (CColor*) calloc (info_header.width, sizeof (CColor));
		bitmap_copy[i] = (CColor*) calloc (info_header.width, sizeof (CColor));
	}

	long offset = ftell (image);
	
	for (i = info_header.height - 1; i >= 0; i--)
	{
		for (j = 0; j < info_header.width; j++)
		{
			fread (&(bitmap[i][j]), sizeof(CColor), 1, image);
			bitmap_copy[i][j] = bitmap[i][j];
		}
		long diff = (ftell (image) - offset) % 4; /* Determin offsetul */
		if (diff != 0)
			fseek (image, 4 - diff, SEEK_CUR);	
	}
	

	fclose (image);

	/************************************************** Task 1 **********************************************/
	
	/* Defineste culoarea alba pentru a putea fi ignorata */
	CColor white_pixel;
	white_pixel.R = 255;
	white_pixel.G = 255;
	white_pixel.B = 255;
	
	for (i = info_header.height - 1; i >= 0; i--)
	{
		for (j = 0; j < info_header.width; j++)
		{
			/* Pentru fiecare pixel colorat seteaza culoarea la cea primita in fisierul de input */
			if (!pixel_equals(bitmap[i][j], white_pixel))
			{
				bitmap_copy[i][j].R = color->R;
				bitmap_copy[i][j].G = color->G;
				bitmap_copy[i][j].B = color->B;	
			}
		}
	}
	
	write_image (captcha_file1, bitmap_copy, info_header.width, info_header.height, header, info_header);
	/************************************************* End Task 1 *******************************************/

	/************************************************** Task 2 **********************************************/
	int numbers = 0; // Variabila ce va contoriza numarul de numere descoperite
	Point *coordinates;
	coordinates = (Point *) calloc (225, sizeof(Point)); // Aloc un numar de 225 de structuri Point deoarece in enunt se specifica faptul ca nici o imagine nu va avea dimensiunea mai mare de 100. (100 / 7) ^ 2 = 225 (7 = numarul de patratele asociate pentru un numar) numarul maxim de cifre ce pot aparea intr-o imagine 
	int *digits = (int *) calloc (225, sizeof (int));

	FILE *task2 = fopen(captcha_file2, "w");
	if (task2 == NULL)
	{
		printf ("Fisierul %s nu a putut fi deschis pentru scriere.\n", captcha_file2);
		return -1;
	}

	int k, l;
	/* Iteratiile pornesc de la -1 pentru a acoperii si cazul cand o cifra este in coltul stanga sus */
	for (j = -1; j <= info_header.width; j++)
	{
		for (i = -1; i <= info_header.height; i++)
		{
			/* Copiez o matrice de 7x7 de la pozitia curenta pentru a verifica daca exista o cifra brodata de alb */
			int matrix[7][7];
			for (k = 0; k < 7; k++)
			{
				for (l = 0; l < 7; l++)
				{
					if (i + k >= info_header.height || j + l >= info_header.width  || i + k < 0 || j + l < 0)
					{
						matrix[k][l] = 0;
						continue;
					}
					if (bitmap[i+k][j+l].R != 255 && bitmap[i+k][j+l].G != 255 && bitmap[i+k][j+l].B != 255)
						matrix[k][l] = 1;
					else 
						matrix[k][l] = 0;
				}
			}

			int digit = get_digit(matrix, 0);
			if(digit != -1) 
			{
				/* Fiecarui numar gasit in imagine ii sunt asociate 2 coordonate */
				coordinates[numbers].x = i + 1;
				coordinates[numbers].y = j + 1;
				digits[numbers] = digit;
				numbers++;
				fprintf (task2, "%d", digit);
			}
		}
	}

	fclose (task2);
	/************************************************* End Task 2 *******************************************/

	/************************************************** Task 3 **********************************************/
	int index = 0;
	for (i = 0; i < numbers; i++)
	{
		int digit = digits[i];
		
		if (!to_be_removed[digit])
		{	
			for (k = 0; k < 5; k++)
			{
				for (l = 0; l < 5; l++)
				{
					bitmap[coordinates[index].x + k][coordinates[index].y + l].R = bitmap [coordinates[i].x + k][coordinates[i].y + l].R;
					bitmap[coordinates[index].x + k][coordinates[index].y + l].G = bitmap [coordinates[i].x + k][coordinates[i].y + l].G;
					bitmap[coordinates[index].x + k][coordinates[index].y + l].B = bitmap [coordinates[i].x + k][coordinates[i].y + l].B;
				}
			}

			index++;
		}
	}

	/* Sterge restul cifrelor */
	for (i = index; i < numbers; i++)
	{
		for (k = 0; k < 5; k++)
		{
			for (l = 0; l < 5; l++)
			{
				bitmap[coordinates[i].x + k][coordinates[i].y + l].R = 255;
				bitmap[coordinates[i].x + k][coordinates[i].y + l].G = 255;
				bitmap[coordinates[i].x + k][coordinates[i].y + l].B = 255;
			}
		}
	}
	write_image (captcha_file3, bitmap, info_header.width, info_header.height, header, info_header);
	/************************************************* End Task 3 *******************************************/

	/************************************************** Bonnus **********************************************/
	
	/* Fisierul bonus trebuie citit */


	FILE *bonus_file = fopen (captcha_bonus_file, "rb");
	if (bonus_file == NULL)
	{
		printf ("Fisierul %s nu a putut fi deschis. Programul se va termina.\n", captcha_bonus_file);
		return -1;
	}

	struct bmp_fileheader bonus_header;
	struct bmp_infoheader bonus_info_header;

	fread (&bonus_header, sizeof (struct bmp_fileheader), 1, bonus_file);
	fread (&bonus_info_header, sizeof (struct bmp_infoheader), 1, bonus_file);

	CColor **bitmap_bonus;
	bitmap_bonus = (CColor**) calloc (bonus_info_header.height, sizeof (CColor*)); /* Furnizez o copie a matricei pentru taskul 1 */
	
	for (i = 0; i < bonus_info_header.height; i++)
		bitmap_bonus[i] = (CColor*) calloc (bonus_info_header.width, sizeof (CColor));

	offset = ftell (bonus_file); // Salveaza offsetul curent 
	
	for (i = bonus_info_header.height - 1; i >= 0; i--)
	{
		for (j = 0; j < bonus_info_header.width; j++)
		{
			fread (&(bitmap_bonus[i][j]), sizeof(CColor), 1, bonus_file);
		}
		long diff = (ftell (bonus_file) - offset) % 4; /* Determin offsetul urmatoarei linii (diferenta pana la el) */
		if (diff != 0) /* In cazul in care diff = 0 => offsetul este corect */
			fseek (bonus_file, 4 - diff, SEEK_CUR);	
	}
	
	fclose (bonus_file);
	
	FILE *bonus_result = fopen (captcha_bonus, "w");
	/* Aplicam aproximativ acelasi algoritm ca si la task-ul 2 */
	for (j = -1; j <= bonus_info_header.width; j++)
	{
		for (i = -1; i <= bonus_info_header.height; i++)
		{
			/* Copiez o matrice de 7x7 de la pozitia curenta pentru a verifica daca exista o cifra brodata de alb */
			int matrix[7][7];
			for (k = 0; k < 7; k++)
			{
				for (l = 0; l < 7; l++)
				{
					if (i + k >= bonus_info_header.height || j + l >= bonus_info_header.width  || i + k < 0 || j + l < 0)
					{
						matrix[k][l] = 0;
						continue;
					}
					if (bitmap_bonus[i+k][j+l].R != 255 && bitmap_bonus[i+k][j+l].G != 255 && bitmap_bonus[i+k][j+l].B != 255)
						matrix[k][l] = 1;
					else 
						matrix[k][l] = 0;
				}
			}

			int digit = get_digit(matrix, 0);
			int digit_partial = get_digit (matrix, 1);
			int found = 0; // marchez faptul ca a fost gasita o cifra
			if(digit != -1) 
			{
				fprintf (bonus_result, "%d", digit);
				found = 1;
			}
			else if (digit_partial != -1)
			{
				fprintf (bonus_result, "%d", digit_partial);
				found = 1;
			}

			if (found)
			{
				for (k = 0; k < 7; k++)
				{
					for (l = 0; l < 7; l++)
					{
						if (i + k >= info_header.height || j + l >= info_header.width  || i + k < 0 || j + l < 0)
							continue;

						bitmap_bonus[i+k][j+l].R = 255;
						bitmap_bonus[i+k][j+l].G = 255;
						bitmap_bonus[i+k][j+l].B = 255;
						
					}
				}
			}
		}
	}

	fclose (bonus_result);

	write_image ("bonus.bmp", bitmap_bonus, bonus_info_header.width, bonus_info_header.height, bonus_header, bonus_info_header);

	/************************************************* End Bonus ********************************************/
	
	/* Elibereaza memoria */
	free (line);
	free (captcha_file);
	free (captcha_file1);
	free (captcha_file2);
	free (captcha_file3);
	free (color);

	for (i = 0; i < info_header.height; i++)
	{
		free (bitmap[i]);
		free (bitmap_copy[i]);
	}

	for (i = 0; i < bonus_info_header.height; i++)
	 	free (bitmap_bonus[i]);

	free (bitmap);
	free (bitmap_copy);
	free (bitmap_bonus);

	return 0;
}
