#include <iostream>
#include <vector>

using namespace std;

vector<vector<double>> Promediar(int n, int m, vector<vector<double>> foto){
	
	vector<vector<double>> promedio(n, vector<double>(m, 0));
	
	promedio[0][0] = (foto[0][1] + foto [1][1] + foto[1][0] + foto[0][0])/4;
	
	for(int i = 1; i < m-1; i++){
		promedio[0][i] = (foto[0][i] + foto[0][i-1] + foto[0][i+1] + foto[1][i] + foto[1][i-1] + foto[1][i+1])/6;
	}
	
	promedio[0][m-1] = (foto[0][m-1] + foto[0][m-2] + foto[1][m-1] + foto[1][m-2])/4;
	
	for(int i = 1; i < n-1; i++){
		for(int j = 1; j < m-1; j++){
			promedio[i][0] = (foto[i][1] + foto [i+1][1] + foto[i+1][0] + foto[i][0] + foto[i-1][0] + foto[i-1][1])/6;
			promedio[i][j] = (foto[i][j+1] + foto [i+1][j+1] + foto[i+1][j] + foto[i][j] + foto[i-1][j] + foto[i-1][j+1] + foto[i][j-1] + foto[i-1][j-1] + foto[i+1][j-1])/9;
			promedio[i][m-1] = (foto[i][m-1] + foto [i+1][m-1] + foto[i+1][m-2] + foto[i][m-2] + foto[i-1][m-2] + foto[i-1][m-1])/6;
		}
	}
	
	promedio[n-1][0] = (foto[n-1][0] + foto[n-2][0] + foto[n-1][1] + foto[n-2][1])/4;
	
	for(int i = 1; i < m-1; i++){
		promedio[n-1][i] = (foto[n-1][i] + foto[n-1][i-1] + foto[n-1][i+1] + foto[n-2][i] + foto[n-2][i-1] + foto[n-2][i+1])/6;
	}
	
	promedio[n-1][m-1] = (foto[n-1][m-1] + foto[n-1][m-2] + foto[n-2][m-2] + foto[n-2][m-1])/4;
	
	return promedio;
}

int main(){
	
	int n = 5;
	int m = 5;
	
	vector<vector<double>> foto(n, vector<double>(m, 0));
	
	for(int i = 0; i < n; i++){
		for(int j = 0; j < m; j++){
			foto[i][j] = rand() % 9 + 1;
		}
	}
	
	for(int i = 0; i < n; i++){
		for(int j = 0; j < m; j++){
			cout << foto[i][j] << " ";
		}
		cout << endl;
	}
	
	vector<vector<double>> promedio = Promediar(n,m,foto);
	
	cout << endl;
	
	for(int i = 0; i < n; i++){
		for(int j = 0; j < m; j++){
			cout << promedio[i][j] << " ";
		}
		cout << endl;
	}
	
	return 0;
}
