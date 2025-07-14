#include "Comandos.h"

Diccionario dic;

string convertirMayusculas(string p) {
    string resultado;
    for (int i = 0; i < int(p.size()); i++) {
        if ((p[i] >= 'a') && (p[i] <= 'z')) {
            resultado = resultado + char(toupper(p[i]));
        }
        else if (p[i] == char(0xC3)) {
            switch (p[i + 1]) {
                case char(0xA1):
                    resultado = resultado + "A";
                    break;
                case char(0xA9):
                    resultado = resultado + "E";
                    break;
                case char(0xAD):
                    resultado = resultado + "I";
                    break;
                case char(0xB3):
                    resultado = resultado + "O";
                    break;
                case char(0xBA):
                    resultado = resultado + "U";
                    break;
                case char(0xBC):
                    resultado = resultado + "Ü";
                    break;
                case char(0xB1):
                    resultado = resultado + "Ñ";
                    break;
                case char(0x81):
                    resultado = resultado + "A";
                    break;
                case char(0x89):
                    resultado = resultado + "E";
                    break;
                case char(0x8D):
                    resultado = resultado + "I";
                    break;
                case char(0x93):
                    resultado = resultado + "O";
                    break;
                case char(0x9A):
                    resultado = resultado + "U";
                    break;
                case char(0x9C):
                    resultado = resultado + "Ü";
                    break;
                case char(0x91):
                    resultado = resultado + "Ñ";
                    break;
                default:
                    resultado = resultado + p[i] + p[i+1];
            }
            i++;
        }
        else {
            resultado = resultado + p[i];
        }
    }
    return resultado;
}
string consonantes(string palabra){
	string resultado;
    for (int i = 0; i < int(palabra.size()); i++) {
        if (palabra[i] == char(0xC3)) {
            switch (palabra[i + 1]) {
                case char(0x9C):
                    
                    i++;
                    break;
                default:
					resultado = resultado + palabra[i] + palabra[i+1];
					i++;
					break;
            }
        }
        else if (palabra[i] != 'A' && palabra[i] != 'E' && palabra[i] != 'I' && palabra[i] != 'O' && palabra[i] != 'U'){
            resultado = resultado + palabra[i];
        }
    }
    return resultado;
}

void vaciar() {
    dic.vaciar();
    cout<< "Vaciando" << endl;
}

void insertar(){
    int m = 0;
    string palabra= "";
    while (cin >> palabra) {
		if (palabra == "</insertar>") {
			break;
		}
		dic.insertar(convertirMayusculas(palabra));
		m++;
    }
    cout << "Insertando: " << m << " palabras" << endl;

}

void buscar(){
        
	string palabra= "";
	cin>>palabra;
	palabra = convertirMayusculas(palabra);
	cout << "Buscando: "<< palabra;
	if (dic.contiene(palabra)){
		cout<<" -> Encontrada"<<endl;
	}
	else{
		cout<<" -> No encontrada"<<endl;
	}
}


void tamano(){
	cout << "Total diccionario: " << dic.numElem() << " palabras" << endl;
}

void consome(){
	string palabra= "";
	cin>>palabra;
	palabra = convertirMayusculas(palabra);
	string cons = consonantes(palabra);
	cout << "Consomé: "<< palabra<< " ->";
	for (string word : dic.juego1(palabra)){
		if (consonantes(word)==cons)
		cout<<" "<<word;
	}
	cout<<endl;

}

void alarga(){
	string palabra= "";
	cin>>palabra;
	palabra = convertirMayusculas(palabra);
    cout << "Alarga: "<< palabra<< " ->" << dic.juego2(palabra) << endl;
}

