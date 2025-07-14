#ifndef _TablaHash
#define _TablaHash
#define TAM 300

#include <string>
#include <list>
#include <iostream>

using namespace std;

class TablaHash {
    private:
    	list<string> t[TAM];
    	int nElem;
    	
    public:
    	TablaHash();
    	
    	unsigned int funcionHash(string palabra);
    	void insertar(string palabra);
    	bool contiene(string palabra);
    	void vaciar();
    	list<string> getCubeta(unsigned int indice);

    	int numElem(void){return nElem;}

	 
};
#endif
