a.out: Arbol.o TablaHash.o Diccionario.o Comandos.o main.o
	g++ Arbol.o TablaHash.o Diccionario.o Comandos.o main.o
	
Arbol.o: Arbol.cpp Arbol.h
	g++ -c Arbol.cpp

TablaHash.o: TablaHash.cpp TablaHash.h
	g++ -c TablaHash.cpp

Diccionario.o: Diccionario.cpp Diccionario.h
	g++ -c Diccionario.cpp
	
Comandos.o: Comandos.cpp Diccionario.h
	g++ -c Comandos.cpp

main.o: main.cpp Comandos.h Diccionario.h
	g++ -c main.cpp


clear:
	rm a.out *.o
