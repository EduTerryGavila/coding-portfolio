#include "Bombo.h"
#include <stdlib.h>
#include <stdio.h>

int error;
char mensajeError[100];

struct BomboRep{

    int * bolas;
    int n,max;
};

typedef struct BomboRep * Bombo;

Bombo BomboCrea(int max){

    if(max<2){
        error = 1;
        return NULL;
    }

    Bombo b = malloc(sizeof(struct BomboRep));
    b->bolas = malloc(max*sizeof(int));
    b->max = max;
    b->n = 0;
    return b;

}

int BomboInserta(Bombo b, int bola){

    if(b->n == b->max){
        error = 2;
        return -1;
    }
    b->bolas[b->n] = bola;
    b->n++;
    return 0;
}

void BomboLibera(Bombo b){

    free(b->bolas);
    free(b);
}

int BomboExtrae(Bombo b){

    if(b->n == 0){
        error = 3;
        return -1;
    }

    int n = rand()%(b->n);
    int bola = b->bolas[n];
    b->n--;
    b->bolas[n] = b->bolas[b->n];
    return bola;

}

int BomboVacia(Bombo b){

    if(b->n == 0){
        return 1;
    }
    return 0;
}

char * BomboMensajeError(Bombo b, int codigoError){

    switch(codigoError){

    case 1:
        sprintf(mensajeError, "El numero de bolas debe de ser mayor o igual que 2\nn");
        return mensajeError;
    case 2:
        sprintf(mensajeError, "El bombo esta lleno\n");
        return mensajeError;
    case 3:
        sprintf(mensajeError, "El bombo esta vacio\n");
        return mensajeError;
    }
    return NULL;

}
