#include "TablaHash.h"

TablaHash::TablaHash() { 
	nElem=0;
}


unsigned int TablaHash::funcionHash(string palabra) {
	string consonantes;
    for (int i = 0; i < int(palabra.size()); i++) {
        if (palabra[i] == char(0xC3)) {
            switch (palabra[i + 1]) {
                case char(0x9C):
                    
                    i++;
                    break;
                default:
					consonantes = consonantes + palabra[i] + palabra[i+1];
					i++;
					break;
            }
        }
        else if (palabra[i] != 'A' && palabra[i] != 'E' && palabra[i] != 'I' && palabra[i] != 'O' && palabra[i] != 'U'){
            consonantes = consonantes + palabra[i];
        }
    }
    
        unsigned int res = 0;

        for (unsigned int i = 0; i < consonantes.length(); i++) {
            res *= 33;
            res += consonantes[i];
        }

	return res % TAM;
}


void TablaHash::insertar(string palabra) {
	unsigned int indice = funcionHash(palabra);
    list<string>::iterator it= t[indice].begin();
	while (it!=t[indice].end() && *it<palabra){
		it++;
	}
	if (it==t[indice].end() || *it!=palabra){
		t[indice].insert(it, palabra);
		nElem++;
	}
}

bool TablaHash::contiene(string palabra) {
    unsigned int indice = funcionHash(palabra);
    list<string>::iterator it= t[indice].begin();
	while (it!=t[indice].end() && *it<palabra){
		it++;
	}
	if (it==t[indice].end() || *it!=palabra){
		return false;
	}
	return true;
}

list<string> TablaHash::getCubeta(unsigned int indice){
	list<string> copia = t[indice];
    	copia.sort();
    	return copia;
}

void TablaHash::vaciar() {
        for (int i = 0; i < TAM; i++) {
            t[i].clear();
        }
        nElem = 0;
}
