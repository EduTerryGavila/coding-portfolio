#include <stdlib.h>
#include <stdio.h>
#include <windows.h>
#include "Cola.h"

int main(){

    Cola * consultorio;
    consultorio = malloc(5*sizeof(Cola));

    for(int i = 0; i<5; i++){
        consultorio[i] = crea();
    }

    int especialidad;
    char * nombre;
    char * DNI;

    do{
        scanf("%d",&especialidad);
        scanf("%s",nombre);
        scanf("%s",DNI);
        consultorio[0].elem
    }while(especialidad!=0);



    return 0;
}
