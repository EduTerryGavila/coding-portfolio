#include <iostream>
#include <cstdlib>
#include <algorithm>
#include <random>
#include <string>
#include <vector>
#include <array>
#include <ctime>
#include <chrono>

using namespace std;

void QueEjercicioMeToca(){
	
	int Integrante1 = 54961208;
	int Integrante2 = 24420735;
	
	int problema = ((Integrante1+Integrante2)%10) + 1;
	
	cout << "Se ha realizado el problema " << problema << endl;
}

string GeneradorCadenas(int longitud) {
    string caracteres = "abcdefghijklmnopqrstuvwxyz";  // Establece qué caracteres pueden aparecer
    int numCaracteres = caracteres.length();  // Calcula el número de caracteres disponibles
    
    unsigned int seed = chrono::system_clock::now().time_since_epoch().count();	// Genera una semilla usando el tiempo en milisegundos
    srand(seed);  // Usa la semilla dinámica para rand()

    string cadena;  // Cadena a devolver

    for (int i = 0; i < longitud; ++i) {
        // Usa rand() para seleccionar un índice aleatorio en el rango de caracteres
        cadena += caracteres[rand() % numCaracteres];
    }

    return cadena;
}

array<string, 10> GeneradorCasos(){

	int casos[] = {100, 101, 1000, 1001, 10000, 10001, 100000, 100001, 999999, 1000000};	//Longitud de cadenas a probar 
	array<string, 10> cadenas; 
	
	for(int i = 0; i < int(sizeof(casos) / sizeof(casos[0])); i++){
		cadenas[i] = GeneradorCadenas(casos[i]);		//Se generan las cadenas
	}
	return cadenas;
}

pair<int, int> CasoBase(string array, int inicio, int ult, int m) {
    int dif = 0;
    int maxDif = 0;
    int pos = inicio;
    
    string A = array.substr(inicio, ult - inicio + 1); //Extrae la subcadena a analizar
    
    for (int j = 0; j <= int(A.length()) - m; j++) {	//Algoritmo de aplicacion directa iterativo
        dif = 0;
        for (int i = 1; i < m; i++) {			//Se recorre cada caracter y sus m consecutivos y se calcula la diferencia
            dif += abs(A[j + i] - A[j + i - 1]);
        }
        
        if (dif > maxDif) {		//Se comprueba si la diferencia obtenida es la mayor
            maxDif = dif;
            pos = inicio + j;
        }
    }
    return {maxDif, pos};	//Devolvemos el par diferencia maxima y posicion
}

pair<int, int> Combinar(string array, int inicio, int mid, int ult, int m) {
    int maxDif = 0;
    int pos = mid;
    
    for (int i = 0; i < m - 1; i++) {	//Este bucle examina las posiciones concretas las cuales se ven perjudicadas por la division de cadenas
        if (mid - i >= inicio && mid - i + (m - 1) <= ult) {	//Se comprueba que no se accede a posiciones de memoria invalidas
            pair<int, int> caso = CasoBase(array, mid - i, mid - i + (m - 1), m);	//Se llama al caso base con para resolver las cadenas perjudicadas
            
            if (caso.first > maxDif) {	//Se comprueba si el valor obtenido es el maximo y en ese caso se guarda este y la posicion en la que empieza dicha cadena
                maxDif = caso.first;
                pos = caso.second;
            }
        }
    }
    return {maxDif, pos};	//Devolvemos el par diferencia maxima y posicion
}

pair<int, int> DyV(string array, int inicio, int ult, int m) {
	
    if (ult - inicio + 1 <= m) {	//Se comprueba si el problema es suficientemente pequeño como para llamar a la resolucion directa
        return CasoBase(array, inicio, ult, m);
    }
    
    int mid = (inicio + ult) / 2;
	//Dividimos el problema en subproblemas
    pair<int, int> s1 = DyV(array, inicio, mid, m);	//Parte izquierda
    pair<int, int> s2 = DyV(array, mid + 1, ult, m); //Parte derecha
    pair<int, int> s3 = Combinar(array, inicio, mid, ult, m);	//Analisis de la parte central
    
    if (s1.first >= s2.first && s1.first >= s3.first){ 	//Se comprueba cual es la mejor solucion en funcion del valor de la diferencia de posiciones y se devuelve
		return s1;
	}	
	
    if (s2.first >= s1.first && s2.first >= s3.first){
		 return s2;
    }
    
    return s3;
}

int main(){
	
	QueEjercicioMeToca();
	
	int m = 5;
	int n = 0;
	
	array<string, 10> cadenasGeneradas = GeneradorCasos();	//Se crea un array capaz de guardar 10 cadenas y lo rellenamos llamando al generador de casos
	
	cout << endl;
	cout << "Resolucion por Divide y Venceras: " << endl;
	cout << endl;
	
	for(int i = 0; i < 10; i++) {
		n = cadenasGeneradas[i].size();
		pair<int,int> solucion = DyV(string(cadenasGeneradas[i]), 0, n-1, m);	//Se llama a la funcion principal, divide y venceras
		cout << "Posicion de inicio es " << solucion.second << " diferencia total maxima igual a " << solucion.first << endl;	//Se imprime el resultado
   }	
	
	cout << endl;
	cout << "Resolucion directa: " << endl;
	cout << endl;
	
	for(int i = 0; i < 10; i++) {
		n = cadenasGeneradas[i].size();
		pair<int,int> solucion2 = CasoBase(string(cadenasGeneradas[i]), 0, n-1, m);	//Se llama a la funcion principal, divide y venceras
		cout << "Posicion de inicio es " << solucion2.second << " diferencia total maxima igual a " << solucion2.first << endl;	//Se imprime el resultado
   }
   
	return 0;
}
