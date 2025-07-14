#include <iostream>
#include <cstdlib>
#include <algorithm>
#include <string>
#include <vector>

using namespace std;

/*Disponemos de un vector A [1..n] que contiene elementos enteros ordenados en sentido decreciente
estricto, i.e., no hay elementos repetidos. Se pretende encontrar la posición i tal que A[i] = i.
Diseñar un algoritmo basado en el esquema divide y vencerás que devuelva dicha posición o el
valor 0 cuando no exista. */

int SolDirecta(int i, int j, vector<int> A){
	
	int sol = 0;
	
	for(int x = i; x < j; x++){
		if(A[x] == x){
			return x;
		}
	}
	
	return sol;
}

int DyV(int i, int j, vector<int> A){
	
	if(i <= j){
		return SolDirecta(i, j, A);
	}
	
	int mid = (i+j)/2;
	
	int s1 = DyV(i, mid, A);
	int s2 = DyV(mid+1, j, A);
	
	if (s1 != 0){
		return s1;
	}
	
	return s2;
	
}

int main(){

	vector<int> A = {30, 22, 10, 8, 6, 5, 2, 1};
	
	int sol = DyV(0, A.size()-1, A);
	
	cout << sol;

	return 0;
}
