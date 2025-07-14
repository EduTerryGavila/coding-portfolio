#include <iostream>
#include <vector>
#include <random>

using namespace std;

bool Solucion(vector<int> S, int A){
	
	int cont = 0;
	
	for(int i = 0; i < int(S.size()); i++){	//Para cada valor del vector solucion se comprueba si la averia tiene mecanico asignado
		if(S[i] != 0){
			cont ++;
		}
	}
	
	if(cont == A){	//Si hay tantos mecanicos asignados como averias significan que todas estan cubiertas por lo que hay solucion
		return true;
	}
	return false;
}

bool Vacio(vector<vector<int>> C, int M, int A){
	
	for(int i = 0; i < M; i++){
		for(int j = 0; j < A; j++){
			if(C[i][j] != 0){
				return false;		//Se recorre la tabla C de manera que si esta a 0 significa que C esta vacio
			}	
		}
	}
	
	return true;
}

pair<int, int> Seleccionar(vector<vector<int>> C, int M, int A, int A_actual){
	
	pair<int, int> x;	//Variable encargada de devolver la pareja mecanico-averia
	
	for(int i = 0; i < M; i++){	
		if(C[i][A_actual] == 1){	//Recorremos la tabla comprobando que mecanicos pueden arreglar la averia actual
			return x = {i, A_actual};
		}
	}
	
	return {-1, -1};	//Se devuelve la decision voraz
}

vector<vector<int>> ActualizarTabla(vector<vector<int>> C, pair<int, int> x, int A){
	
	for(int j = 0; j < A; j++){	//Ponemos las averias a 0 del mecanico asignado de manera que ya no se puede usar
		C[x.first][j] = 0;
	}
	
	return C;	//Devolvemos la tabla actualizada
}

bool Factible(vector<int> S, pair<int, int> x){
	
	if(S[x.second] == 0){	//Se comprueba si en la averia j-esima hay un mecanico asignado y si no lo hay se devuelve verdadero
		return true;
	}
	return false;
}

vector<int> Voraz(vector<vector<int>> C, int M, int A){
	
	vector<int> S(A, 0);	//Vector solucion de tamaño A (averias) el cual esta inicializado a 0
	
	pair<int, int> x = {0, 0};	//Pareja mecanico-averia incializados a 0
	
	int A_actual = 0;	//A_actual sirve para ver que averia es a la que le buscamos un mecanico disponible en cada momento
	
	while(Solucion(S, A) == false && Vacio(C,M,A) == false && A_actual < A){	//Se comprueba que no haya solucion aun en S, la tabla c no este vacia y que A_actual no sobrepasa a A
		 x = Seleccionar(C,M,A,A_actual);	//Se guarda en x la pareja mecanico-averia seleccionada
		 if(x.first != -1 && x.second != -1){	//Se comprueba que no se trate de un caso en el que no se ha encontrado ningun mecanico disponible
		 C = ActualizarTabla(C,x,A);		//Se actualiza la tabla C con lo obtenido en x
		 
		 if(Factible(S,x) == true){		//Se comprueba que la pareja obtenida es adecuada para guardarla en la solucion
			 S[x.second] = x.first + 1;	//Se guarda en la solucion el mecanico obtenido anteriormente sumandole 1 ya que los arrays comienzan en 0
		 }
		 }
		 A_actual++;	//Se le suma uno a A_actual para pasar a analizar la siguiente averia en la proxima iteracion
	}
	
	return S;
}

int main(){
	
	random_device rd;
	
	mt19937 gen(rd());
	
	uniform_int_distribution<> distrib(1, 100);

    int M, A;
    M = 100;
    A = 5000;	//Se leen los mecanicos y averias de este caso
        
    vector<vector<int>> C(M, vector<int>(A, 0));	//Declaracion de la tabla 
        
    for (int i = 0; i < M; i++) {
		for (int j = 0; j < A; j++) {
			C[i][j] = distrib(gen);	//Se leen los valores de cada tabla de entrada
        }
    }
        
    vector<int> S = Voraz(C, M, A);	//Se guarda en S la solucion correspondiente
       
    int reparadas = 0;
    for (int i = 0; i < A; i++) {	//Se cuentan las averias reparadas para imprimirlas 
		if (S[i] != 0)
			reparadas++;
        }
 
    cout << reparadas << endl;

    for (int i = 0; i < A; i++) {
		cout << S[i] << (i < A - 1 ? " " : "");	//Se imprime la solucion con espacios entre caracteres excepto la ultima que no se imprime nada
    }

    cout << endl;
    
    
    return 0;
}
