// Fichero Cola.h
#ifndef COLA_H_INCLUDED
#define COLA_H_INCLUDED
// Tipo de datos de los elementos de las colas, el cual
// representa pacientes de un consultorio.
typedef struct{
 char nombre[30];
 char DNI[10];
}Elemento;
// Tipo de datos de las colas.
typedef struct ColaRep * Cola;
// Devuelve una cola vacía.
Cola crea();
// Libera la memoria asignada a la cola c.
void libera(Cola c);
// Inserta el elemento e en el último lugar de la cola c.
void inserta(Cola c, Elemento e);
// Suprime el elemento situado en primer lugar de la cola c.
void suprime(Cola c);
// Devuelve el elemento situado en primer lugar de la cola c.
Elemento recupera(Cola c);
// Devuelve 0 si la cola c no es vacía, y 1 en caso contrario.
int vacia(Cola c);
#endif // COLA_H_ INCLUDED
