#include <iostream>
#include <vector>
#include <algorithm>
#include <climits>

using namespace std;

vector<vector<int>> rellenar_tabla(const vector<int>& P, const vector<int>& T, int e, int k){
	vector<vector<int>> V(k + 1, vector<int>(e + 1, 0));
	
	for(int i = 0; i <= k; i++){
		for(int j = 0; j <= e; j++){
			V[i][j] = 0;
		}
	}
	
	for(int i = 1; i <= k; i++){
		for(int j = 1; j <= e; j++){
			int m1 = ((j - (3*P[i-1])) >= 0) ? (3*T[i-1]) + V[i-1][j- (3*P[i-1])] : INT_MIN;
			int m2 = ((j - (2*P[i-1])) >= 0) ? (2*T[i-1]) + V[i-1][j- (2*P[i-1])] : INT_MIN;
			int m3 = ((j - P[i-1]) >= 0) ? T[i-1] + V[i-1][j- P[i-1]] : INT_MIN;
			int m4 = V[i-1][j];
			V[i][j] = max({m1,m2,m3,m4});
		}
	}
	
	/*for(int i = 0; i <= k; i++){
		for(int j = 0; j <= e; j++){
			cout << V[i][j] << " ";
		}
		cout << endl;
	}	*/
	return V;
}

pair<int, vector<int>> reconstruir_solucion(const vector<int>& P, const vector<int>& T, int e, int k, const vector<vector<int>>& V){
	
	int j = e;
	int i = k;
	vector<int> S(k, 0);
	
	while (i > 0 && j > 0){
		
		int m1 = V[i-1][j];
		int m2 = V[i][j];
		
		if(m1 != m2){
			if((j-(3*P[i-1]) >= 0) && (V[i][j] == (3*T[i-1] + V[i-1][j-(3*P[i-1])])))  {
				S[i-1] = 3;
				j = j - (3*P[i-1]);
			}
			
			else if((j-(2*P[i-1]) >= 0) && (V[i][j] == (2*T[i-1] + V[i-1][j-(2*P[i-1])]))){
				S[i-1] = 2;
				j = j - (2*P[i-1]);
			}
			
			else if((j-P[i-1] >= 0) && (V[i][j] == (T[i-1] + V[i-1][j-P[i-1]]))){
				S[i-1] = 1;
				j = j - P[i-1];
			}
		}
		i = i-1;
	}
	
	return {V[k][e], S};
	
}

int main() {
    int e = 20;
    int k = 5;
    vector<int> P = {2, 3, 4, 3, 5};
    vector<int> T = {1, 2, 5, 4, 3};
    
    vector<vector<int>> V = rellenar_tabla(P, T, e, k);
    pair<int, vector<int>> resultado = reconstruir_solucion(P, T, e, k, V);
    
    cout << "Valor maximo: " << resultado.first << "\nSeleccion: ";
    for (int i : resultado.second) {
        cout << i << " ";
    }
    cout << endl;
    
    return 0;
}
