#include <iostream>
#include <vector>

using namespace std;

/*Acabamos de llegar al puesto de CEO de la startup para la que
trabajamos en San Francisco. Queremos adoptar un look un poco
“Zukerver”, así que nos toca llenar el armario de prendas iguales,
para estar a la moda. La empresa nos da D dólares como
presupuesto para vestuario. Queremos maximizar el gasto, sin
sobrepasar el presupuesto. Hay N tipos de prenda, cada tipo con
un precio pi por unidad de este tipo. De cada tiopo podemos elegir
hasta 3 unidades. No tenemos obligación de elegir prendas de
todos los tipos.*/

bool Vacio(vector<int> C, int n){

	for(int i = 0; i < n; i++){
		if(C[i] != 0){
			return false;
		}
	}
	
	return true;
}

bool Solucion(int precioact, int d){

	if(precioact < d){
		return false;
	}
	
	return true;
}

pair<int, int> Seleccionar(vector<int> C, int d, int precioact, int n){

	for(int i = 0; i < n; i++){
		if(C[i] + precioact <= d && C[i] != 0){
			return {C[i], i};
		}
	}
	
	return {-1, -1};
}

bool Factible(pair<int, int> y, vector<int> x){

	if(x[y.second] == 3){
		return false;
	}
	
	return true;
}

vector<int> ActualizarTabla(vector<int> x, pair<int, int> y, vector<int> C){

	if(x[y.second] == 3){
		C[y.second] = 0;
		return C;
	}
	
	return C;
}

vector<int> Voraz(int d, vector<int> C, int n){

	vector<int> x(n);
	
	pair<int, int> y;
	
	for(int i = 0; i < n; i++){
		x[i] = 0;
	}
	
	int precioact = 0;
	int it = 0;
	
	while(Vacio(C, n) == false && Solucion(precioact, d) == false && it < n*3){
		
		y = Seleccionar(C, d, precioact, n);
		
		if(y.first != -1 && y.second != -1){
			
			C = ActualizarTabla(x, y, C);
			
			if(Factible(y, x) == true){
				
				x[y.second] = x[y.second] + 1;
				precioact = precioact + y.first;
			}
		}
		it++;
	}
	
	return x;
}

int main(){

	int d = 200;
	
	vector<int> C = {30, 10, 50, 80, 25, 22, 33, 40};
	
	int n = C.size();
	
	vector<int> x = Voraz(d, C, n);
	
	for(int i = 0; i < n; i++){
		
		cout << x[i] << ",";
	}
	
	return 0;
}
