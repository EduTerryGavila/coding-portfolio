#include <stdlib.h>
#include <stdio.h>
#include <windows.h>
#include "ABB.h"

int main(){

    SetConsoleCP(1252);
    SetConsoleOutputCP(1252);

    int n;
    int m[100];

    ABB a = ABBCrea();

    printf("Introduce la cantidad de elementos a insertar: ");
    scanf("%d",&n);

    for(int i = 0; i<n; i++){
        printf("Introduce el elemento %d: ",i+1);
        scanf("%d",&m[i]);
        ABBInserta(&a,m[i]);

    }



    return 0;
}
