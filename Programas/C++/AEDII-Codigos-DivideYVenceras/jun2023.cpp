#include <iostream>
#include <cstdlib>
#include <algorithm>
#include <string>
#include <vector>

using namespace std;

pair<int, int> noSol = {-1,-1}; 

/*Nos piden encontrar, dentro de una secuencia S de n números indexados
por i=1..n, cuál es la subsecuencia más larga que cumple la condición de que sus elementos
consecutivos no disten, en valor absoluto, más de 1 entre ellos.
el resultado sería la subsecuencia [1 2 1 2 3 4], entre los índices 1 y 6, con longitud 6.*/

pair<int, int> SolDirecta(int i, int j,vector<int> S, int n){
	
	bool resultado = true;
	vector<int> cadena(n);
	
	for (int x = 0; x < n; x++){
		cadena[x] = S[i+x];
	}
	
	for (int x = 0; x <= n-2; x++){
		if (abs(cadena[x] - cadena[x+1]) > 1){
			resultado = false;
		}
	}
	
	if (resultado == true){
		return {i,j};
	}
	
	return noSol;
	
}
	
pair<int, int> Combinar(int i, int j, vector<int> S, int n){
	
	pair<int, int> solucion = noSol;
	
	for (int x = 0; x < n-1; x++){
		if( ((((i+j)/2) - i) >= i) && (((((i+j)/2) - i) + (n-1)) <= j)){
			solucion = SolDirecta((((i+j)/2) - i), ((((i+j)/2) - i) + (n-1)), S, n);
		}
		
		if (solucion != noSol){
			return solucion;
		}
	}
	
	return noSol;
}

pair<int, int> DyV(int i, int j, vector<int> S, int n){
	
	if ((j-i+1) <= n){
		return SolDirecta(i, j, S, n);
	}
	
	int mid = (i+j)/2;
	
	pair<int, int> s1 = noSol;
	pair<int, int> s2 = noSol;
	pair<int, int> s3 = noSol;
	
	s1 = DyV(i, mid, S, n);
	s2 = DyV(mid+1, j, S, n);
	s3 = Combinar(i, j, S, n);
	
	if (s1 != noSol){
		return s1;
	}
	
	if (s2 != noSol){
		return s2;
	}
	
	if (s3 != noSol){
		return s3;
	}
	
	return noSol;
}

int main(){
	
	vector<int> S = {1, 2, 1, 4, 3, 4, 7, 8, 9, 10, 11, 12};
	int fin = S.size();
	int n = 6;
	
	pair<int, int> sol = DyV(0, fin-1, S, n);
	
	cout << sol.first+1 << "," << sol.second+1 << endl;
	
	return 0;
}
