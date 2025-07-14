#include <iostream>
#include <cstdlib>
#include <algorithm>
#include <string>
#include <vector>

using namespace std;

/*Nos piden encontrar, dentro de una secuencia S de n letras indexadas
desde i=1 hasta n, cuál es la longitud de la subsecuencia de vocales más larga y su índice de
comienzo.
El resultado sería longitud = 4, e índice de comienzo = 8, que corresponde en el ejemplo con la
subsecuencia [i o u a].*/

pair<int, int> SolDirecta(int i, int j, vector<int> S){
	
	pair<int, int> solucion = {0,0};
	
	for(int x = i; x < j; x++){
		int contador = 0;
		int ind = x;
		
		while(S[ind] == 'a' || S[ind] == 'e' || S[ind] == 'i' || S[ind] == 'o' || S[ind] == 'u'){
			contador++;
			ind++;
		}
		
		if(solucion.first < contador){
			solucion = {contador, ind};
		}
	}
	
	return solucion;
}

pair<int, int> Combinar(int i, int j, vector<char> S, int mid){
	
	pair<int, int> solucion = {0,0};
	
	if((S[mid] == 'a' || S[mid] == 'e' || S[mid] == 'i' || S[mid] == 'o' || S[mid] == 'u') && (S[mid+1] == 'a' || S[mid+1] == 'e' || S[mid+1] == 'i' || S[mid+1] == 'o' || S[mid+1] == 'u')){
		int inicio = mid;
		int fin = mid+1;
		int contador = 0;
		
		while(S[inicio] == 'a' || S[inicio] == 'e' || S[inicio] == 'i' || S[inicio] == 'o' || S[inicio] == 'u'){
			contador++;
			inicio--;
		}
		
		while(S[fin] == 'a' || S[fin] == 'e' || S[fin] == 'i' || S[fin] == 'o' || S[fin] == 'u'){
			contador++;
			fin++;
		}
		
		solucion = {contador, inicio+1};
	}
	
	return solucion;
}

pair<int, int> DyV(int i, int j, vector<char> S){

	if(i==j){
		pair<int, int> nada = {0,0};
		return nada;
	}
	
	int mid = (i+j)/2;
	
	pair<int, int> s1 = DyV(i, mid, S); 
	pair<int, int> s2 = DyV(mid+1, j, S);
	pair<int, int> s3 = Combinar(i, j, S, mid);
	
	if(s1.first > s2.first && s1.first > s3.first){
		return s1;
	}
	
	if(s2.first > s1.first && s2.first > s3.first){
		return s2;
	}
	
	return s3;
}

int main(){
	
	vector<char> S = {'b', 'a', 'b', 'a', 'e', 'a', 'c', 'i', 'o', 'u', 'a', 'b'};
	
	pair<int, int> sol = DyV(0, S.size()-1, S);
	
	cout << sol.first << "," << sol.second+1 << endl;
	
	return 0;
}
