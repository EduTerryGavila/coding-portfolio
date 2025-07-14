#include <iostream>
#include <vector>
#include <algorithm>
#include <climits>

using namespace std;

vector<vector<int>> mochila01_paso2_rellenar_tabla(const vector<int>& P, int M, int n, const vector<int>& B) {
    vector<vector<int>> V(n + 1, vector<int>(M + 1, 0));
    
    for (int k = 1; k <= n; ++k) {
        for (int m = 1; m <= M; ++m) {
            int m1 = (m - P[k - 1] >= 0) ? B[k - 1] + V[k - 1][m - P[k - 1]] : INT_MIN;
            int m2 = V[k - 1][m];
            V[k][m] = max(m1, m2);
        }
    }
    return V;
}

pair<int, vector<int>> mochila01_paso3_reconstruir_solucion(const vector<int>& P, int M, int n, const vector<int>& B, const vector<vector<int>>& V) {
    int k_actual = n;
    int m_actual = M;
    vector<int> S(n, 0);
    
    while (k_actual > 0 && m_actual > 0) {
        int m2 = V[k_actual - 1][m_actual];        
        int m0 = V[k_actual][m_actual];
        
        if (m0 != m2) {
            S[k_actual - 1] = 1;
            m_actual -= P[k_actual - 1];
        }
        k_actual--;
    }
    return {V[n][M], S};
}

int main() {
    int n = 3;
    int M = 6;
    vector<int> P = {2, 3, 4};
    vector<int> B = {1, 2, 5};
    
    vector<vector<int>> V = mochila01_paso2_rellenar_tabla(P, M, n, B);
    pair<int, vector<int>> resultado = mochila01_paso3_reconstruir_solucion(P, M, n, B, V);
    
    cout << "Valor maximo: " << resultado.first << "\nSeleccion: ";
    for (int i : resultado.second) {
        cout << i << " ";
    }
    cout << endl;
    
    return 0;
}
