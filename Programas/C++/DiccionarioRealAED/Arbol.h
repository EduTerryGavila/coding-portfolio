#ifndef _Arbol
#define _Arbol

#include <iostream>
#include <string>
#include <list>

using namespace std;

class NodoTrie {
private:
    char car;
    list<NodoTrie*> hijos;
    bool esPalabra;

public:
    NodoTrie(char c) : car(c), esPalabra(false) {}

    NodoTrie* obtenerHijo(char c);
	
	char obtenerCaracter() const;

    const list<NodoTrie*>& obtenerHijos() const;
    
    void insertarHijo(char c) ;

    bool tieneHijo(char c);

    bool hayMarca();

    void ponMarca() ;
    
    void vaciar() ;
};

class Arbol {
private:
    NodoTrie* raiz;
	int nElem;
public:
    
    Arbol() : raiz(new NodoTrie('\0')), nElem(0) {}

    ~Arbol() {
        raiz->vaciar();
        delete raiz;
    }

    void insertar(const string& palabra) ;

    bool consultar(const string& palabra) ;
    
    string alargarPalabra(const string& prefijo) ;
    
    void vaciar() ;
    int numElem() const ;
    
    private:
    void alargarPalabraRecursivo(NodoTrie* nodo, string& palabraParcial, list<string>& palabrasProcedentes) ;
};
#endif
