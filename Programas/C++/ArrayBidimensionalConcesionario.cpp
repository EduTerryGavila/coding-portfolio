#include <iostream>

using namespace std;

const int n = 3;
const int m = 12;
typedef int Matriz[n][m];

int encontrar(Matriz &existencias, int modelo){
	
	for(int i = 0; i < n; i++){
		if(existencias[i][modelo] > 0){
			return i+1;
		}
	}
	
	return -1;
}

int main(){
	
	Matriz existencias;
	
	for(int i = 0; i < n; i++){
		for(int j = 0; j < m; j++){
			existencias[i][j] = rand() % 10;
		}
	}
	
	for(int i = 0; i < n; i++){
		for(int j = 0; j < m; j++){
			cout << existencias[i][j] << " ";
		}
		cout << endl;
	}
	
	int modelo = rand() % m + 1;
	
	int concesionario = encontrar(existencias, modelo);
	
	if(concesionario == -1){
		cout << "No se ha encontrado el modelo" << endl;
	}
	else{
		cout << "El modelo " << modelo << " esta en el concesionario " << concesionario << endl;
	}

	return 0;
}
