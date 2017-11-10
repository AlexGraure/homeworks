//GRAURE MARIUS-ALEXANDRU
//325CB


//structura pentru baza de date de useri
typedef struct TUtilizator{
	char* nume;
	char* prenume;
	int nrCard;
	int pin;
	char* passwd;
	double sold;
	int logged;
} TU, *TUser, **ATUser;

//functie ce aloca un user cu parametrii primiti
TUser AlocUser(char* nume, char* pnume, int nCd, int pin, char* pass, double sold){
	TUser aux = (TUser)malloc(sizeof(TUser));
	if(!aux){
		printf("Alocare aux nereusita");
		return NULL;
	}
	aux->nume = (char*)malloc(strlen(nume) * sizeof(char));
	if(!aux->nume) {
		printf("Alocare nume nereusita pentru %s", nume);
	} else {
		memcpy( aux->nume, nume, sizeof aux->nume );
	}
	
	aux->prenume = (char*)malloc(strlen(pnume) * sizeof(char));
	if(!aux->prenume) {
		printf("Alocare prenume nereusita pentru %s", pnume);
	} else {
		memcpy( aux->prenume, pnume, sizeof aux->prenume );
	}
	
	aux->nrCard = nCd;
	aux->pin = pin;
	
	aux->passwd = (char*)malloc(strlen(pass) * sizeof(char));
	if(!aux->prenume) {
		printf("Alocare parola nereusita pentru %s", pass);
	} else {
		memcpy( aux->passwd, pass, sizeof aux->passwd );
	}
	
	aux->sold = sold;
	aux->logged = 0;
	
	return aux;
}

//functie ce verifica daca numarul cardului exista si daca este corect pinul
char* checkPin(ATUser users, int credentials[], int logged[], int nrUsers, int nrCard, int pin, int userno) {
	//printf("userno din struct = %d\n", userno);	

	int wrongCard = 1, wrongPin = 1;
	int id = 0;
	char* buffer = (char*)malloc (256 * sizeof (char));

	for(int i = 0; i < nrUsers; i++) {
		if(users[i]->nrCard == nrCard) {
			wrongCard = 0;
			id = i;
			if(users[i]->pin == pin) {
				wrongPin = 0;
				break;
			}
		}
	}

	if(users[id]->logged == 1) {
		buffer = "ATM> -2 : Sesiune deja deschisa\n";
		return buffer;
	} else {
		if(logged[userno] < 0) {
			buffer = "ATM> -5 : Card blocat\n";
			return buffer;
		}
	}

	if(wrongCard == 1) {
		buffer = "ATM> -4 : Numar card inexistent\n";
	} else {
		if(wrongPin == 1) {
			if(credentials[userno] > 2) {
				buffer = "ATM> -5 : Card blocat\n";
				logged[userno] = -1;
			} else {
				buffer = "ATM> -3 : Pin gresit\n";
				credentials[userno]++;
			}
		} else {
			sprintf(buffer, "ATM> Wellcome %s %s\n", users[id]->nume, users[id]->prenume);
			credentials[userno] = 0;
			logged[userno] = id;
			users[id]->logged = 1;
		}
	}
	return buffer;
}

//functie ce scoate o suma de bani din soldul clientului
char* getMoney(TUser user, int money) {
	char *buffer = (char*)malloc (256 * sizeof (char));
	if(money % 10 != 0) {
		buffer = "ATM> −9 : Suma nu este multiplu de 10\n";
		return buffer;
	} else {
		if(user->sold < money) {
			buffer = "ATM> −8 : Fonduri insuficiente\n";
			return buffer;
		}
	}
	user->sold -= money;
	sprintf(buffer, "ATM> Suma %d retrasa cu succes\n", money);
	return buffer;
}

//functie ce adauga o suma de bani in soldul clientului
char* putMoney(TUser user, double money) {
	char *buffer = (char*)malloc (256 * sizeof (char));
	user->sold += money;
	buffer = "ATM> Suma depusa cu succes\n";
	return buffer;
}
