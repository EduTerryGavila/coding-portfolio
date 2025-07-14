#include <iostream>
#include <cstdlib>
#include <algorithm>
#include <string>
#include <vector>

using namespace std;

/* Nos piden encontrar, dentro de una secuencia S de n números
indexados por i=1..n, cuál es la subsecuencia más larga que cumple la
condición de que sus elementos contiguos tienen al menos un divisor en
común; al margen del 1, claro, que divide a cualquier número.
el resultado sería la subsecuencia [2 4 2 6 3], entre los índices 2 y 6, con longitud 5.*/

int mcd(int a, int b) {
    while (b != 0) {
        int temp = b;
        b = a % b;
        a = temp;
    }
    return a;
}

pair<int, int> SolDirecta(int i, int j, vector<int> S){
	
	pair<int, int> sol;
	
	for(int x = i; x < j; x++){
		int contador = 0;
		int ind = x;
		int inicio = x;
		
		while(S[x] != 1 && mcd(S[x], S[x+1]) != 1){
			ind++;
			contador++;
		}
		
		if(contador > 0){
			contador++;
			ind++;
			if(contador > sol.first){
				sol = {inicio,ind};
			}
		}
	}
	
	return sol;
}

pair<int, int> Combinar(int i, int j, vector<int> S, int mid){

	pair<int, int> sol;
	
	if(mcd(S[mid],S[mid+1]) && S[mid] != 1 && S[mid+1] != 1){
		
		int inicio = mid;
		int fin = mid+1;
		int contador = 1;
		
		while(inicio-1 > 0 && S[inicio] != 1 && S[inicio-1] != 1 && mcd(S[inicio], S[inicio-1]) != 1){
			inicio--;
			contador++;
		}
		
		while(fin+1 <= j && S[fin] != 1 && S[fin+1] != 1 && mcd(S[fin], S[fin+1]) != 1){
			fin++;
			contador++;
		}
		
		if(sol.first < contador){
			sol = {inicio+1, fin};
		}
	}
	
	return sol;
}

pair<int, int> DyV(int i, int j, vector<int> S){

	if(i == j){
		pair<int, int> nada = {0,0};
		return nada;
	}
	
	int mid = (i+j)/2;
	
	pair<int, int> s1 = DyV(i, mid, S); 
	pair<int, int> s2 = DyV(mid+1, j, S);
	pair<int, int> s3 = Combinar(i, j, S, mid);
	
	if((((s1.second - s1.first) + 1) > ((s2.second - s2.first) + 1)) && (((s1.second - s1.first) + 1) > ((s3.second - s3.first) + 1))){
		return s1;
	}

	if((((s2.second - s2.first) + 1) > ((s1.second - s1.first) + 1)) && (((s2.second - s2.first) + 1) > ((s3.second - s3.first) + 1))){
		return s2;
	}
	
	return s3;
}

int main(){

	vector<int> S = {1, 2, 4, 2, 6, 3, 7, 8, 9, 10, 12, 6};
	
	pair<int, int> sol = DyV(0, S.size()-1, S);
	
	cout << sol.first << "," << sol.second;

	return 0;
}
