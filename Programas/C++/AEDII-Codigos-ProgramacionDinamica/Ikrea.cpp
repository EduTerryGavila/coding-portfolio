#include <iostream>
#include <vector>
#include <algorithm>
#include <climits>

using namespace std;

vector<vector<int>> rellenar_tabla(const vector<int>& a,const vector<int>& b, int m, int k){
	vector<vector<int>> V(k + 1, vector<int>(m + 1));
	
	for (int i = 0; i <= k; i++) {
        V[i][0] = 1;
    }
    
    for (int i = 0; i <= m; i++) {
        V[0][i] = 0;
    }
	
	int nformas = 0;
	
	for(int i = 1; i <= k; i++){
		for(int j = 1; j <= m; j++){
			nformas = V[i-1][j];
			if(j >= a[i-1]){
				nformas = nformas + V[i-1][j-a[i-1]];
			}
			if(j >= b[i-1]){
				nformas = nformas + V[i-1][j-b[i-1]];
			}
			
			V[i][j] = nformas;
		}
	}
	
	for(int i = 0; i <= k; i++){
		for(int j = 0; j <= m; j++){
			cout << V[i][j] << " ";
		}
		cout << endl;
	}
	
	return V;
}

pair<int, vector<int>> reconstruir_solucion(const vector<int>& a,const vector<int>& b, int m, int k, vector<vector<int>>& V){
	
	int j = m;
	vector<int> S(k, 0);
	
	if(V[k][j] == 0){
		return {0, S};
	}
	
	for(int i = k; i > 0; i--){
		if(V[i-1][j] != 0){
			S[i-1] = 0;
		}
		else if((j-a[i-1] >= 0) && (V[i-1][j-a[i-1]] != 0)){
			S[i-1] = 1;
			j = j - a[i-1];
		}
		else if((j-b[i-1] >= 0) && (V[i-1][j-b[i-1]] != 0)){
			S[i-1] = 2;
			j = j - b[i-1];
		}
	}
	
	
	return {V[k][m], S};
}

int main() {
    int m = 10;
    int k = 6;
    vector<int> a = {2, 3, 4, 3, 1, 3};
    vector<int> b = {1, 2, 5, 4, 3, 2};
    
    vector<vector<int>> V = rellenar_tabla(a, b, m, k);
    pair<int, vector<int>> resultado = reconstruir_solucion(a, b, m, k, V);
    
    cout << "Valor maximo: " << resultado.first << "\nSeleccion: ";
    for (int i : resultado.second) {
        cout << i << " ";
    }
    cout << endl;
    
    return 0;
}
