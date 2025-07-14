#include <iostream>
#include <vector>
#include <algorithm>
#include <climits>
#include <cstdlib> 
#include <ctime>

using namespace std;

vector<vector<int>> rellenar_tabla(int k, int a, vector<vector<int>> M){
	vector<vector<int>> V(k + 1, vector<int>(a + 1, INT_MAX));
	
	for(int i = 0; i <= k; i++){
		V[i][0] = 0;
	}
	
	
	for(int i = 1; i <= k; i++){
		for(int j = 1; j <= a; j++){
			int m1 = (j-M[i-1][2] >= 0 && V[i-1][j-M[i-1][2]] != INT_MAX) ? 1 + V[i-1][j-M[i-1][2]] : INT_MAX;
			int m2 = (j-M[i-1][1] >= 0 && V[i-1][j-M[i-1][1]] != INT_MAX) ? 1 + V[i-1][j-M[i-1][1]] : INT_MAX;
			int m3 = (j-M[i-1][0] >= 0 && V[i-1][j-M[i-1][0]] != INT_MAX) ? 1 + V[i-1][j-M[i-1][0]] : INT_MAX;
			int m4 = V[i-1][j];
			
			V[i][j] = min({m1,m2,m3,m4});
		}
	}
	return V;
}

pair<int, vector<int>> reconstruir_solucion(int k, int a, vector<vector<int>> M, vector<vector<int>> V){
	int i = k;
	int j = a;
	vector<int> S(k, 0);
	
	while (i > 0 && j > 0){
		int m1 = V[i-1][j];
		int m2 = V[i][j];
		
		if(m1 != m2){
			if((j-M[i-1][2] >= 0) && (V[i][j] == (1 + V[i-1][j-M[i-1][2]]))){
				S[i-1] = 3;
				j = j - M[i-1][2];
			}
			else if((j-M[i-1][1] >= 0) && (V[i][j] == (1 + V[i-1][j-M[i-1][1]]))){
				S[i-1] = 2;
				j = j - M[i-1][1];
			}
			else if((j-M[i-1][0] >= 0) && (V[i][j] == (1 + V[i-1][j-M[i-1][0]]))){
				S[i-1] = 1;
				j = j - M[i-1][0];
			}
		}
		i = i - 1;
	}
	return {V[k][a], S};
}

int main() {
	
    int a = 35;
    int k = 5;
    vector<vector<int>> M(k, vector<int>(3));
    
    std::srand(std::time(nullptr));
    
    for(int i = 0; i < k; i++){
		for(int j = 0; j < 3; j++){
				M[i][j] = 1 + std::rand() % 10;
		}
	}
	
	for(int i = 0; i < k; i++){
		for(int j = 0; j < 3; j++){
				cout << M[i][j] << " ";
		}
		cout << endl;
	}
     
    vector<vector<int>> V = rellenar_tabla(k, a, M);
    pair<int, vector<int>> resultado = reconstruir_solucion(k, a, M, V);
    
    cout << "Valor maximo: " << resultado.first << "\nSeleccion: ";
    for (int i : resultado.second) {
        cout << i << " ";
    }
    cout << endl;
	
    return 0;

}
