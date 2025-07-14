#include <stdlib.h>
#include <windows.h>
#include <stdio.h>
#include <time.h>
#include "Bombo.h"


int main(){

    int n;
    printf("Introduce un numero del 0 al 99.999: ");
    scanf("%d",&n);

    int numeroMax = 100000;

    Bombo b1 = BomboCrea(numeroMax);

    int numeroPremios = 1807;

    Bombo b2 = BomboCrea(numeroPremios);

    for(int i = 0; i<numeroMax; i++){
        BomboInserta(b1,i);
    }

    BomboInserta(b2,4000000);
    BomboInserta(b2,1250000);
    BomboInserta(b2,500000);
    BomboInserta(b2,200000);
    BomboInserta(b2,200000);

    for(int i = 0; i<8; i++){
        BomboInserta(b2,60000);
    }

    for(int i = 0; i<1794; i++){
        BomboInserta(b2,1000);
    }

    return 0;
}
