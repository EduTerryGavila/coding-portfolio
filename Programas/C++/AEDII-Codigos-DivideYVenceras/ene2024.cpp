#include <iostream>
#include <cstdlib>
#include <algorithm>
#include <string>
#include <vector>
#include <set>

using namespace std;

/*Queremos identificar la subsecuencia formada por los índices de todos los puntos del
perfil que sean un pico, es decir, un punto que es estrictamente más alto que los puntos anterior y
posterior a él en la secuencia.*/

set<int> SolDirecta(int i, int j, vector<int> A){
	
	set<int> sol;
	
	for (int x = i + 1; x < j; ++x) {
        if (A[x] > A[x - 1] && A[x] > A[x + 1]) {
            sol.insert(x);
        }
    }
	
	return sol;
}

set<int> Combinar(int i, int j, vector<int> A){
	
	set<int> sol;
	
	sol = SolDirecta(((i+j)/2)- 1, ((i+j)/2)+ 1, A);
	
	return sol;
}

set<int> DyV(int i, int j, vector<int> A){
	
	if((j-i+1) <= 3){
		return SolDirecta(i, j, A);
	}
	
	int mid = (i+j)/2;
	
	set<int> s1;
	set<int> s2;
	set<int> s3;
	
	s1 = DyV(i, mid, A);
	s2 = DyV(mid, j, A);
	s3 = Combinar(i, j, A);
	
	set<int> solucion;
	
	solucion.insert(s1.begin(), s1.end());
	solucion.insert(s2.begin(), s2.end());
	solucion.insert(s3.begin(), s3.end());
	
	return solucion;
}


int main(){
	
	vector<int> A = {3, 4, 2, 7, 8, 8, 9, 6, 5, 7, 6, 11, 11, 10, 12, 7, 15};
	
	int fin = A.size();
	
	set<int> sol = DyV(0, fin-1, A);
	
	for (int num : sol) {
        cout << num+1 << " ";
    }
	
	return 0;
}
