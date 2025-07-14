minic: minic.tab.c minic.tab.h lex.yy.c listaSimbolos.c listaCodigo.c main.c
	gcc main.c listaSimbolos.c listaCodigo.c lex.yy.c minic.tab.c -L/usr/lib -lfl -o minic
minic.tab.c minic.tab.h: minic.y 
	bison -v -d minic.y
lex.yy.c: minic.l 
	flex minic.l
clean:
	rm -rf lex.yy.c minic.tab.c minic.tab.h minic minic.output

run:
	./minic
run-lexico:
	./minic < tests/lexico/test_lex_01.txt
