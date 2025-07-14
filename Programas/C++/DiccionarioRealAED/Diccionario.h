#ifndef _Diccionario
#define _Diccionario

#include <string>
#include <list>
#include "TablaHash.h"
#include "Arbol.h"

using namespace std;


class Diccionario {
    private:
    	TablaHash tabla;
    	Arbol arbol;
    public:
    	Diccionario() {};
    
    	void insertar(string palabra) { tabla.insertar(palabra); arbol.insertar(palabra); }
    	
    	list<string> juego1(string palabra) { return tabla.getCubeta(tabla.funcionHash(palabra)); }
    	
    	string juego2(string palabra) { return arbol.alargarPalabra(palabra); }

    	bool contiene(string palabra){ return arbol.consultar(palabra); }

    	void vaciar() { tabla.vaciar(); arbol.vaciar();}

    	int numElem(void){return arbol.numElem();}


};
#endif
