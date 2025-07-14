#include <iostream>
#include <vector>

using namespace std;

/*Estas Navidades probablemente hayamos disfrutado de muchas comidas y cenas que habrán
provocado un aumento de nuestro volumen corporal. Pero nos queda una última celebración a base
de tapas. Cada tapa lleva asociado un peso pi y un número de calorías ci
. Queremos comernos en
total un peso lo más cercano a P (sin pasarnos) minimizando el total de calorías, y sabiendo que
podríamos comernos hasta dos unidades de cada tapa.*/

bool Vacia(vector<pair<int, int>> C, int t){

	for(int i = 0; i < t; i++){
		
		if(C[i].first != 0 && C[i].second != 0){
			return false;
		}
	}
	
	return true;
}

bool Solucion (int p, vector<pair<int, int>> C, int p_act, int t){

	for(int i = 0; i < t; i++){
	
		if((C[i].first + p_act) <= p && C[i].first > 0){
			return false;
		}
	}
	return true;
}

pair<pair<int, int>,int> Seleccionar(vector<pair<int, int>> C, int p, int p_act, int t){

	for(int i = 0; i < t; i++){
	
		if((C[i].first + p_act) <= p && C[i].first > 0){
			return {{C[i]},i};
		}
	}
	return {{-1, -1}, -1};
}

vector<pair<int, int>> ActualizarTabla(vector<pair<int, int>> C, pair<pair<int, int>,int> y, int t, vector<int> x){


	if(x[y.second] == 2){
			
		C[y.second] = {0, 0};
		return C;
	}
	
	return C;
}

bool Factible(vector<int> x, pair<pair<int, int>,int> y){

	if(x[y.second] < 2){
		return true;
	}
	
	return false;
}

vector<int> Voraz(int p, vector<pair<int, int>> C, int t){

	vector<int> x(t);
	
	for(int i = 0; i < t; i++){
		x[i] = 0;
	}
	
	pair<pair<int, int>,int> y;
	
	int p_act = 0;
	
	while(Vacia(C, t) == false && Solucion(p, C, p_act, t) == false){
	
		y = Seleccionar(C, p, p_act, t);
		
		if(y.first.first != -1 && y.first.second != -1){
					
			if(Factible(x, y)){
				p_act = p_act + C[y.second].first;
				x[y.second] = x[y.second] + 1;
				C = ActualizarTabla(C, y, t, x);
			}
		}
	}
	
	return x;
}

int main(){

	vector<pair<int, int>> C = {
    {50, 100},
    {20, 70},
    {30, 20},
    {40, 120}
	};

	int t = C.size();
	
	int p = 200;
	
	vector<int> x = Voraz(p, C, t);
	
	for(int i = 0; i < t; i++){
		
		cout << x[i] << ",";
	}

	return 0;
}
