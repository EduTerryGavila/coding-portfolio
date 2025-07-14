#include <iostream>
#include <vector>
#include <algorithm>
#include <climits>

using namespace std;

void Generar(int nivel, vector<int> &S, int &pact, const vector<vector<int>> &prendas, vector<int> &Pn){	//Funcion que genera nodos
	
	S[nivel] = S[nivel] + 1;	//Generacion de un nuevo nodo
	if(S[nivel] - 1 > 0 and prendas[nivel][S[nivel]] <= prendas[nivel][S[nivel] - 1] and S[nivel] < int(prendas[nivel].size()) - 1){
		S[nivel] = S[nivel] + 1;	//Si no es rentable generar el nodo ya que va a ser menor al anterior pasamos al siguiente (poda)
	}
	pact = pact + prendas[nivel][S[nivel]];	//El precio acumulado actual sera el anterior mas lo que haya en el nodo actual
	Pn[nivel] = pact;	//Actualizacion de Pn
}

bool Solucion(int nivel, int C, int M, int pact){	//Funcion que comprueba si se tiene una solucion
	
	return (nivel == C - 1) and (pact <= M);	//Si el nivel es el mas alto y el precio acumulado actual es menor al presupuesto si es solucion
}

bool Criterio(int nivel, int C, int M, int pact){	//Funcion que comprueba si se puede subir de nivel 
	
	return (pact <= M) and (nivel < C - 1);		//Si el precio acumulado actual es menor o igual al presupuesto y el nivel actual no es el mas alto podemos subir
}

bool MasHermanos(vector<int> S, int nivel, vector<vector<int>> prendas){	//Esta funcion comprueba si en el nivel actual hay mas hermanos
	
	return S[nivel] < (int)prendas[nivel].size() - 1;	// Si no estamos en el ultimo hermano es porque aun no lo hemos generado
}

void Retroceder(int &pact, vector<int> &S, int &nivel, vector<vector<int>> prendas, vector<int> Pn){	//Funcion que hace retroceder al nivel inferior
	
	S[nivel] = - 1;		//Anulamos los resultados actuales
	nivel = nivel - 1;	//Bajamos de nivel
	pact = Pn[nivel - 1];	//Configuramos el precio acumulado actual al que se tenia antes de subir
}

int BackTracking(const vector<vector<int>> &prendas, int M, int C) {
	
	int nivel = 0;	//Variable que representa el nivel actual en el arbol
	int voa = INT_MIN;	//Valor optimo actual
	int pact = 0;	//Variable que contiene el precio acumulado en cierto momento
	vector<int> S(C, -1);	//Vector solucion S que contiene la solucion actual 
	vector<int> Pn(C, 0);	//Vector el cual contendra el valor de niveles anteriores
	
	do{
		
		if(MasHermanos(S, nivel, prendas)){	//Antes de generar un nuevo nodo se comprueba que hayan mas hermanos
			Generar(nivel, S, pact, prendas, Pn);	//Se genera un nuevo nodo
		
		
			if(Solucion(nivel, C, M, pact) and (pact > voa)){	//Si tenemos una solucion y el precio actual es mayor que el mayor de los evaluados anteriormente se actualiza voa
				
				voa = pact;
			}
			
			if(Criterio(nivel, C, M, pact)){	//Si se cumple el criterio el cual comprueba que podemos subir de nivel subimos de nivel
				
				nivel = nivel + 1;
			}
			else{
				pact = Pn[nivel - 1];	//Si no se cumple el criterio guardamos en Pn el pact por si se usa mas tarde
				while(!MasHermanos(S, nivel, prendas) and (nivel > 0)){		//Comprobamos si no hay mas hermanos en el nivel actual y mas niveles abajo para poder bajar
					
					Retroceder(pact, S, nivel, prendas, Pn);
				}
			}	
		}
		else{	//Si no hay hermanos se retrocede
			Retroceder(pact, S, nivel, prendas, Pn);

		}
	} while(nivel >= 0 and voa != M);	//Se hace todo lo anterior hasta que nivel sea menos que 0 o voa valga M ya que no hay mejor solucion posible
	
	return voa;	//Se devuelve el mejor valor obtenido
}
 
int main(){
    int P;  //Numero de casos de prueba.
    cin >> P;
    
    for (int i = 0; i < P; i++){
        int M, C;
        cin >> M >> C;  //Se ingresa el presupuesto y el numero de tipos de prenda.
        
        vector<vector<int>> prendas(C);	//Declaracion de la tabla C la cual contiene el precio de cada modelo de cada prenda
        for (int i = 0; i < C; i++){	//Una iteracion por cada tipo de prenda para rellenar la tabla
            int k;	//Numero de modelos para cada prenda
            cin >> k;	
            prendas[i].resize(k);	//Dimensionado dinamico dependiendo del tamaño de cada fila
            for (int j = 0; j < k; j++){
                cin >> prendas[i][j];	//Precio de cada modelo de cada prenda
            }
        }
        
        for (auto& fila : prendas) {
			sort(fila.begin(), fila.end(), greater<int>());	//Ordenacion de mayor a menor de cada fila de manera que es mas probable encontrar 
															//la solucion antes suponiendo que los presupuestos suelen ser relativamente altos
		}
		
		
		
        int best = BackTracking(prendas, M, C);	//Llamada al backtracking
        if(best < 0)	//Se comprueba si se ha obtenido o no la solucion
            cout << "no solution";
        else
            cout << best;
        cout << "\n";
    }
    
    return 0;
}
