## Resolución problema usando dos estrategias

# Algoritmo Voraz:

Para resolver el problema cuyo enunciado se ha mencionado se usará un algoritmo voraz.
El algoritmo debe de tomar una decisión para elegir la solución mas aproximada a la correcta.

En primer lugar, para cada valor del vector solución se comprueba si la averia tiene mecánico asignado, si lo tiene lo ignora y pasa al siguiente.
Con esto obtenemos una solución muy próxima a la mejor con un muy buen rendimiento.

- [Ver código](/AR/AR.cpp)

Luego se me ocurrio una opción que también sería muy eficiente pero daría una mejor solución.

- [Ver mejora](/AR/ARMejorado.cpp)

En esta versión se analizan las posibilidades por parejas de manera veloz y eficiente para obtener una solución mas cercata a al óptima.

# Backtracking:

