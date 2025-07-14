#include "Cola.h"
#include <stdlib.h>
#define MAX 100

struct ColaRep{

    Elemento elem[MAX];
    int frente, posterior, n;
};

Cola crea() {

    Cola c = malloc(sizeof(struct ColaRep));
    c->frente = 0;
    c->posterior = 0;
    c->n = 0;
    return c;
}

void libera(Cola c){

    free(c);
}

void inserta(Cola c, Elemento e){

    c->elem[c->posterior] = e;
    c->posterior = (c->posterior+1)%MAX;
    c->n++;
}

void suprime(Cola c){

    c->frente = (c->frente+1)%MAX;
    c->n--;

}

Elemento recupera(Cola c){

    return c->elem[c->frente];
}

int vacia(Cola c){

    return (c->n==0);
}
