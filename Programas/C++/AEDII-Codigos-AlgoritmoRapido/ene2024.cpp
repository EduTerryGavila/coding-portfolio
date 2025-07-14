#include <iostream>
#include <vector>
#include <climits>

using namespace std;

/*Ahora que han pasado las fiestas nos toca apechugar en casa y contribuir a terminar con los dulces
de Navidad sobrantes (turrones, mazapanes y demás), aunque nuestro cuerpo esté hinchado por
tantas comidas, cenas y tardeos que llevamos encima. Un trozo de cada tipo de dulce que podemos
tomar lleva asociado un peso pi y un número de calorías ci
. Queremos tomarnos en total un peso lo
más cercano a P (sin pasarnos) minimizando el total de calorías, y sabiendo que podríamos
tomarnos hasta dos trozos de cada tipo de dulce.*/

bool Vacio(vector<pair<int, int>> C, int n){

	for(int i = 0; i < n; i++){
		if(C[i].first != 0){
			return false;
		}
	}
	
	return true;
}

bool Solucion(int pact, int p){

	if(pact < p){
		return false;
	}
	
	return true;
}

pair<pair<int, int>, int> Seleccionar(vector<pair<int, int>> C, int n, int p, int pact){
	
	pair<pair<int, int>, int> candidato = {{0, INT_MAX}, 0};
	
	pair<pair<int, int>, int> valorEstandar = {{0, INT_MAX}, 0};
	
	for(int i = 0; i < n; i++){
		if(C[i].first + pact <= p && C[i].first != 0){
			if(C[i].second < candidato.first.second){
				candidato = {{C[i].first, C[i].second}, i};
			}
		}
	}
	
	if(candidato == valorEstandar){
		return {{-1, -1}, -1};
	}
	
	return candidato;
}

bool Factible(pair<pair<int, int>, int> y, vector<int> x){

	if(x[y.second] == 2){
		return false;
	}
	
	return true;
}

vector<pair<int, int>> ActualizarTabla(vector<pair<int, int>> C, pair<pair<int, int>, int> y, vector<int> x){

	if(x[y.second] == 2){
	
		C[y.second].first = 0;
		return C;
	}
	
	return C;
}

vector<int> Voraz(vector<pair<int, int>> C, int n, int p){

	vector<int> x(n);
	
	pair<pair<int, int>, int> y;
	
	for(int i = 0; i < n; i++){
	
		x[i] = 0;
	}
	
	int pact = 0;
	
	int it = 0;
	
	while(Vacio(C, n) == false && Solucion(pact, p) == false && it < n*3){
	
		y = Seleccionar(C, n, p, pact);
		
		C = ActualizarTabla(C, y, x);
		
		if(y.first.first != -1){
		
			if(Factible(y, x) == true){
			
				x[y.second] = x[y.second] + 1;
				pact = pact + y.first.first;
			
			}
		}
		
		it++;
	}
	
	return x;
}

int main(){
	
	vector<pair<int, int>> C = {{30, 15}, {40, 30}, {15, 51}, {23, 26}, {48, 31}};
	
	int n = C.size();
	
	int p = 170;
	
	vector<int> x = Voraz(C, n, p);
	
	for(int i = 0; i <  n; i++){
		cout << x[i] << ",";
	}
	
	return 0;
}
