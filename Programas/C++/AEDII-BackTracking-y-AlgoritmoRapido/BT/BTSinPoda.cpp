#include <iostream>
#include <vector>
#include <algorithm>
#include <climits>

using namespace std;

void Generar(int nivel, vector<int> &S, int &pact, const vector<vector<int>> &prendas, vector<int> &Pn){
	
	S[nivel] = S[nivel] + 1;
	pact = pact + prendas[nivel][S[nivel]];
	Pn[nivel] = pact;
}

bool Solucion(int nivel, int C, int M, int pact){
	
	return (nivel == C - 1) and (pact <= M);
}

bool Criterio(int nivel, int C, int M, int pact){
	
	return (pact <= M) and (nivel < C - 1);
}

bool MasHermanos(vector<int> S, int nivel, vector<vector<int>> prendas){
	
	return S[nivel] < (int)prendas[nivel].size() - 1;
}

void Retroceder(int &pact, vector<int> &S, int &nivel, vector<vector<int>> prendas, vector<int> Pn){
	
	S[nivel] = - 1;
	nivel = nivel - 1;
	pact = Pn[nivel - 1];
}

int BackTracking(const vector<vector<int>> &prendas, int M, int C) {
	
	int nivel = 0;
	int voa = INT_MIN;
	int pact = 0;
	vector<int> S(C, -1);
	vector<int> Pn(C, 0);
	
	do{
		
		if(MasHermanos(S, nivel, prendas)){
			Generar(nivel, S, pact, prendas, Pn);
		
		
			if(Solucion(nivel, C, M, pact) and (pact > voa)){
				
				voa = pact;
			}
			
			if(Criterio(nivel, C, M, pact)){
				
				nivel = nivel + 1;
			}
			else{
				pact = Pn[nivel - 1];
				while(!MasHermanos(S, nivel, prendas) and (nivel > 0)){
					
					Retroceder(pact, S, nivel, prendas, Pn);
				}
			}	
		}
		else{
			Retroceder(pact, S, nivel, prendas, Pn);

		}
	} while(nivel >= 0 and voa != M);
	
	return voa;
}
 
int main(){
    int P;  // Número de casos de prueba.
    cin >> P;
    
    for (int i = 0; i < P; i++){
        int M, C;
        cin >> M >> C;  // Se ingresa el presupuesto y el número de categorías.
        
        vector<vector<int>> prendas(C);
        for (int i = 0; i < C; i++){
            int k;
            cin >> k;
            prendas[i].resize(k);
            for (int j = 0; j < k; j++){
                cin >> prendas[i][j];
            }
        }
        
        int best = BackTracking(prendas, M, C);
        if(best < 0)
            cout << "no solution";
        else
            cout << best;
        cout << "\n";
    }
    
    return 0;
}
