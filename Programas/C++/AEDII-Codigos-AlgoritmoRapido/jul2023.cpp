#include <iostream>
#include <vector>

using namespace std;

/*Supongamos que tenemos un robot ubicado en la posición (1, 1, 1) de una malla tridimensional de
tamaño NxNxN. En cada celda (a, b, c) de la cuadrícula hay P(a, b, c) monedas. 1 <= a, b ,c <= N.
Si un robot pasa por una celda, automáticamente coge todas las monedas que hay en dicha celda.
El robot solo se puede mover hacia arriba, hacia la derecha y hacia atrás, es decir, si el robot se
encuentra en una celda (a, b, c) solamente se puede mover hacia las celdas (a + 1, b, c), (a, b + 1, c)
y (a, b, c + 1). Después de varios pasos, el robot debe de llegar a la posición (N, N, N).
Queremos maximizar la cantidad de monedas que el robot puede coger.*/

bool Vacia(vector<vector<vector<int>>> C, int n){

	for(int i = 0; i < n; i++){
		for(int j = 0; j < n; j++){
			for(int k = 0; k < n; k++){
				if(C[i][j][k] != 0){
					return false;
				}
			}
		}
	}
	
	return true;
}

bool Solucion(vector<pair<pair<int, int>, int>> s, int n, int salasvisit){
	
	return (s[salasvisit-1].first.first == n
       && s[salasvisit-1].first.second == n
       && s[salasvisit-1].second == n);
}

pair<pair<int, int>, int> Seleccionar(vector<vector<vector<int>>> C, vector<pair<pair<int, int>, int>> s, int salasvisit, int n){

	if(s[salasvisit-1].first.first < n && C[s[salasvisit-1].first.first + 1][s[salasvisit-1].first.second][s[salasvisit-1].second] > 0){
		return {{{s[salasvisit-1].first.first + 1}, s[salasvisit-1].first.second}, s[salasvisit-1].second};
	}
	
	else if(s[salasvisit-1].first.second < n && C[s[salasvisit-1].first.first][s[salasvisit-1].first.second + 1][s[salasvisit-1].second] > 0){
		return {{{s[salasvisit-1].first.first}, s[salasvisit-1].first.second + 1}, s[salasvisit-1].second};
	}
	
	return {{{s[salasvisit-1].first.first}, s[salasvisit-1].first.second}, s[salasvisit-1].second + 1};
}

bool Factible(pair<pair<int, int>, int> x, vector<vector<vector<int>>> C){

	if(C[x.first.first][x.first.second][x.second] == 0){
		return false;
	} 
	
	return true;
}

vector<vector<vector<int>>> ActualizarTabla(vector<vector<vector<int>>> C, pair<pair<int, int>, int> x){

	C[x.first.first][x.first.second][x.second] = 0;
	
	return C;
}

vector<pair<pair<int, int>, int>> Voraz(int n, vector<vector<vector<int>>> C){

	vector<pair<pair<int, int>, int>> s(3*n - 2);
	
	s[0] = {{1,1},1};
	
	ActualizarTabla(C, s[0]);
	int salasvisit = 1;
	
	pair<pair<int, int>, int> x;
	
	while(Vacia(C, n) == false && Solucion(s, n, salasvisit) == false){
	
		x = Seleccionar(C, s, salasvisit, n);
		
		if(Factible(x, C) == true){
			
			s[salasvisit] = x;
			
			salasvisit++;
			
			C = ActualizarTabla(C, x);
		}
		
	}
	
	s.resize(salasvisit);
	return s;
}

int main(){
    int n = 3;

    vector<vector<vector<int>>> C(n+1,
        vector<vector<int>>(n+1,
            vector<int>(n+1, 1)
        )
    );


    for(int i=0;i<=n;i++)
    for(int j=0;j<=n;j++){
        C[0][i][j] = C[i][0][j] = C[i][j][0] = 0;
    }

    auto camino = Voraz(n, C);

    cout << "Camino recorrido por el robot (en orden):\n";
    for (auto &p : camino) {
        cout << "("
             << p.first.first << ", "
             << p.first.second << ", "
             << p.second
             << ")\n";
    }

    cout << "Monedas totales recogidas: " << camino.size() << "\n";

    return 0;
}
