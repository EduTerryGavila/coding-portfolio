#include <iostream>
#include "Diccionario.h"
#include "Comandos.h"
using namespace std;



int main() {
    string palabra = "";
    string palabra2 = "";

    
    while (cin >> palabra) {
	if (palabra == "<insertar>") {
            insertar();
            tamano();	
        }
        if (palabra == "<vaciar>") {
            vaciar();
            tamano();
        }
        if (palabra == "<buscar>") {
	    buscar();
        }
        if (palabra == "<partidas>") {
	    	cout<<"Partidas:";
		while (cin >> palabra) {
				
        		if (palabra == "</partidas>") {
                		break;
               		}
         		cout<<" "<<convertirMayusculas(palabra);
       	 	}
        	cout << endl;
        	cout << "No implementado" << endl;
        }
        
        if (palabra == "<alocado>") {
			cin>>palabra;
            cout << "Alocado: "<< convertirMayusculas(palabra)<< endl << "No implementado" << endl;
        }
        if (palabra == "<juanagra>") {
			cin>>palabra;
            cout << "Juanagrama: "<< convertirMayusculas(palabra)<< endl << "No implementado" << endl;
        }
        if (palabra == "<saco>") {
			cin>>palabra>>palabra2;
            cout << "Saco: "<< convertirMayusculas(palabra)<<" "<< convertirMayusculas(palabra2) << endl << "No implementado" << endl;
        }
        if (palabra == "<consomé>") {
		consome();
        }
        if (palabra == "<césar>") {
	    cin>>palabra;
            cout << "César: "<< convertirMayusculas(palabra)<< endl << "No implementado" << endl;
        }
        if (palabra == "<alarga>") {
			alarga();
        }
        if (palabra == "<exit>") {
			cout << "Saliendo..." << endl;
			exit(0);
        }
    }
    return 0;
}
