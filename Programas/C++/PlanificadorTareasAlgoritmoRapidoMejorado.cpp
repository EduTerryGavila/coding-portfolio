#include <iostream>
#include <vector>

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
	
	pair<int, int> x = {-1, -1};	//Variable encargada de devolver la pareja mecanico-averia
	int cont = 0;	//Contador para ver que mecanico tiene el menor numero de averias disponibles
	int contmin = A;	//Contador a comparar con el anterio el cual guardara el que mecanico tiene el minimo
	
	for(int i = 0; i < M; i++){	
		cont = 0;
		if(C[i][A_actual] == 1){	//Recorremos la tabla comprobando que mecanicos pueden arreglar la averia actual
			for(int j = 0; j < A; j++){
				if(C[i][j] == 1){	//Contamos que averias puede arreglar cada uno de los aptos ya que el que menos tenga disponobles es menos probable que luego pueda solucionar otra distinta
					cont++;
				}
			}
			if(cont < contmin){	//Comprobamos si el mecanico seleccionado como apto tiene el menor numero de tareas disponibles, si es asi se escoge
				contmin = cont;
				x = {i,A_actual};
			}
		}
	}
	
	return x;	//Se devuelve la decision voraz
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
	
	int P;
    cin >> P;
    
    cout << endl << P << endl;
    
    for (int caso = 0; caso < P; caso++) {
		cerr << "Leyendo caso " << caso << endl;

        int M, A;
        cin >> M >> A;
        
        vector<vector<int>> C(M, vector<int>(A, 0));
        
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < A; j++) {
                cin >> C[i][j];
            }
        }
        
        vector<int> S = Voraz(C, M, A);
        
        int reparadas = 0;
        for (int i = 0; i < A; i++) {
            if (S[i] != 0)
                reparadas++;
        }
 
        cout << reparadas << endl;

        for (int i = 0; i < A; i++) {
            cout << S[i] << (i < A - 1 ? " " : "");
        }

        cout << endl;
    }
    
    return 0;
}
