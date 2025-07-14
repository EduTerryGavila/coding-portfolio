#include "Arbol.h"


NodoTrie* NodoTrie::obtenerHijo(char c) {
	for (NodoTrie* hijo : hijos) {
		if (hijo->car == c) {
			return hijo;
        }
    }
    return nullptr;
}
	
char NodoTrie::obtenerCaracter() const {
        return car;
    }

const list<NodoTrie*>& NodoTrie::obtenerHijos() const {
        return hijos;
    }
    
void NodoTrie::insertarHijo(char c) {
        hijos.push_back(new NodoTrie(c));
    }

bool NodoTrie::tieneHijo(char c) {
        return obtenerHijo(c) != nullptr;
    }

bool NodoTrie::hayMarca() {
        return esPalabra;
    }

void NodoTrie::ponMarca() {
        esPalabra = true;
    }
    
void NodoTrie::vaciar() {
        for (NodoTrie* hijo : hijos) {
            hijo->vaciar();
            delete hijo;
        }
        hijos.clear();
    }



void Arbol::insertar(const string& palabra) {
        NodoTrie* actual = raiz;

        for (char c : palabra) {
            if (!actual->tieneHijo(c)) {
                actual->insertarHijo(c);
            }
            actual = actual->obtenerHijo(c);
        }

        if (!actual->hayMarca()) {
            actual->ponMarca();
            nElem++;
        }
    }

bool Arbol::consultar(const string& palabra) {
        NodoTrie* actual = raiz;

        for (char c : palabra) {
            if (!actual->tieneHijo(c)) {
                return false;
            }
            actual = actual->obtenerHijo(c);
        }

        return actual->hayMarca();
    }
    
string Arbol::alargarPalabra(const string& prefijo) {
        NodoTrie* nodoActual = raiz;

        for (char c : prefijo) {
            if (!nodoActual->tieneHijo(c)) {
                return "";
            }
            nodoActual = nodoActual->obtenerHijo(c);
        }

        list<string> palabrasProcedentes;
        string palabraParcial = prefijo;
        alargarPalabraRecursivo(nodoActual, palabraParcial, palabrasProcedentes);

        if (palabrasProcedentes.empty()) {
        } else {
            string palabraLarga="";
            int largoMax=0;
            palabrasProcedentes.sort();
            for (const string& palabra : palabrasProcedentes) {
				
				int largo=0;
				
				for (int i = 0; i < int(palabra.size()); i++) {
					if (palabra[i] == char(0xC3)) {
						largo++;
						i++;
					}
					else{
						largo++;
					}
				}
                if(largo > largoMax ){
					palabraLarga=palabra;
					largoMax=largo;
				}
				else if ((largoMax == largo) && (palabraLarga>palabra)){
					palabraLarga=palabra;
				}
				
            }
            return " "+palabraLarga;
        }
        return "";
    }
    
void Arbol::vaciar() {
        raiz->vaciar();
        delete raiz;
        raiz = new NodoTrie('\0');
        nElem = 0;
    }
int Arbol::numElem() const {
        return nElem;
    }
    

void Arbol::alargarPalabraRecursivo(NodoTrie* nodo, string& palabraParcial, list<string>& palabrasProcedentes) {
        if (nodo->hayMarca()) {
            palabrasProcedentes.push_back(palabraParcial);
        }

        for (NodoTrie* hijo : nodo->obtenerHijos()) {
            palabraParcial.push_back(hijo->obtenerCaracter());
            alargarPalabraRecursivo(hijo, palabraParcial, palabrasProcedentes);
            palabraParcial.pop_back();
        }
    }
