#include <iostream>
#include <vector>
#include <climits>

using namespace std;

/*Nos vamos al cine y vamos a comprar bebidas y bolsas de frutos secos.
Tenemos D euros de presupuesto. Queremos maximizar el gasto sin sobrepasar el presupuesto. Hay
N tipos de productos; un producto de tipo i tiene un precio igual a pi euros. De cada tipo podemos
comprar hasta tres unidades. Si de un producto compramos más de una unidad, tendremos un
descuento del 10% en todas las unidades compradas de ese producto. No tenemos obligación de
comprar de todos los productos.*/

bool Vacio(vector<int> C, int n){

	for(int i = 0; i < n; i++){
		if(C[i] != 0){
			return false;
		}
	}
	
	return true;
}

bool Solucion(int d, int pact){

	if(pact < d){
	
		return false;
	}
	
	return true;
}

pair<int, int> Seleccionar(vector<int> C, int n, int pact, int d, vector<int> x){
	
	pair<int, int> candidato = {-1, -1};
	
	for(int i = 0; i < n; i++){
		
		if(x[i] > 1){
			
			if(C[i]*0.9 + pact <= d && C[i] != 0){
				if(C[i]*0.9 > candidato.first){
					candidato = {C[i]*0.9, i};
				}
			}
		}
		
		else if(C[i] + pact <= d && C[i] != 0){
			if(C[i] > candidato.first){
				candidato = {C[i], i};
			}
		}
	}
	
	return candidato;
}

vector<int> ActualizarTabla(vector<int> C, pair<int, int> y, vector<int> x){

	if(x[y.second] == 3){
		C[y.second] = 0;
	}
	
	return C;
}

bool Factible(vector<int> x, pair<int, int> y){

	if(x[y.second] == 3){
		return false;
	}
	
	return true;
}

vector<int> Voraz(int d, int n, vector<int> C){

	vector<int> x(n);
	
	for(int i = 0; i < n; i++){
		x[i] = 0;
	}
	
	int pact = 0;
	
	pair<int, int> y;
	
	int it = 0;
	
	while(Vacio(C, n) == false && Solucion(d, pact) == false && it < n*3){
		
		y = Seleccionar(C, n, pact, d, x);
		
		C = ActualizarTabla(C, y, x);
		
		if(Factible(x, y) == true && y.first != -1){

			x[y.second] = x[y.second] + 1;
			pact = pact + y.first;
		}
		
		it++;
	}
	
	return x;
}

int main(){
	
	vector<int> C = {10, 6, 7, 3, 2, 11, 9, 4};
	
	int d = 60;
	
	int n = C.size();
	
	vector<int> x = Voraz(d, n, C);
	
	for(int i = 0; i < n; i++){
		
		cout << x[i] << ",";
	}
	
	return 0;
}
