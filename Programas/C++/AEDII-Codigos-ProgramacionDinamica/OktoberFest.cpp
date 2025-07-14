#include <iostream>
#include <vector>
#include <algorithm>
#include <climits>

using namespace std;

vector<vector<int>> rellenar_tabla(const vector<int>& C, int l, int k){
	vector<vector<int>> V(k + 1, vector<int>(l + 1, INT_MAX));
	
	for (int i = 0; i <= k; i++) {
        V[i][0] = 0;
    }
	
	for(int i = 1; i <= k; i++){
		for(int j = 1; j <= l; j++){
			int m1 = (j - (4*C[i-1]) >= 0 && V[i-1][j - 4 * C[i-1]] != INT_MAX) ? 4 + V[i-1][j - (4*C[i-1])] : INT_MAX;
			int m2 = (j - (3*C[i-1]) >= 0 && V[i-1][j - 3 * C[i-1]] != INT_MAX) ? 3 + V[i-1][j - (3*C[i-1])] : INT_MAX;
			int m3 = (j - (2*C[i-1]) >= 0 && V[i-1][j - 2 * C[i-1]] != INT_MAX) ? 2 + V[i-1][j - (2*C[i-1])] : INT_MAX;
			int m4 = (j - C[i-1] >= 0 && V[i-1][j - C[i-1]] != INT_MAX) ? 1 + V[i-1][j - C[i-1]] : INT_MAX;
			int m5 = V[i-1][j];
			
			V[i][j] = min({m1,m2,m3,m4,m5});
		}
	}	
	
	for(int i = 0; i <= k; i++){
		for(int j = 0; j <= l; j++){
			if(V[i][j] == INT_MAX){
				cout << "∞" << " ";
			}
			else{
			cout << V[i][j] << " ";
			}
		}
		cout << endl;
	}
	
	return V;
}

pair<int, vector<int>> reconstruir_solucion(const vector<int>& C, int l, int k, vector<vector<int>>& V){
	int i = k;
	int j = l;
	vector<int> S(k, 0);
	
	while(i > 0 && j > 0){
		int m1 = V[i][j];
		int m2 = V[i-1][j];
		
		if(m1 != m2){
			if((j - (4 * C[i-1]) >= 0) && (V[i][j] == (4 + V[i-1][j - (4 * C[i-1])]))){
				S[i-1] = 4;
				j = j - (4 * C[i-1]);
			}
			else if((j - (3 * C[i-1]) >= 0) && (V[i][j] == (3 + V[i-1][j - (3 * C[i-1])]))){
				S[i-1] = 3;
				j = j - (3 * C[i-1]);
			}
			else if((j - (2 * C[i-1]) >= 0) && (V[i][j] == (2 + V[i-1][j - (2 * C[i-1])]))){
				S[i-1] = 2;
				j = j - (2 * C[i-1]);
			}
			else if((j - (1 * C[i-1]) >= 0) && (V[i][j] == (1 + V[i-1][j - C[i-1]]))){
				S[i-1] = 1;
				j = j - (1 * C[i-1]);
			}
		}
		i = i - 1;
	}
	return {V[k][l], S};
}

int main() {
    int l = 40;
    int k = 5;
    vector<int> C = {2, 3, 4, 3, 5};
    
    vector<vector<int>> V = rellenar_tabla(C, l, k);
    pair<int, vector<int>> resultado = reconstruir_solucion(C, l, k, V);
    
    cout << "Valor maximo: " << resultado.first << "\nSeleccion: ";
    for (int i : resultado.second) {
        cout << i << " ";
    }
    cout << endl;
    
    return 0;
}
