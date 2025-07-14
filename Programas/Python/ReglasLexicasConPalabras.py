import sys
import regex as re
import ast

Regla1 = r"(?P<silaba1>[AEIOUaeiuoáéíóúÁÉÍÓÚ])(?P<silaba2>[^AEIOUaeiouáéíóúÁÉÍÓÚ][AEIOUaeiouáéíóúÁÉÍÓÚ])"
Regla2a = r"(?P<silaba1>[aeiouAEIOUáéíóúÁÉÍÓÚ])(?P<silaba2>[pcbgf][rl][aeiouAEIOUáéíóúÁÉÍÓÚ])"
Regla2b = r"(?P<silaba1>[aeiouAEIOUáéíóúÁÉÍÓÚ])(?P<silaba2>[dt][r][aeiouAEIOUáéíóúÁÉÍÓÚ])"
Regla2c = r"(?P<silaba1>[AEIOUaeiuoáéíóúÁÉÍÓÚ][^AEIOUaeiouáéíóúÁÉÍÓÚ])(?P<silaba2>[^AEIOUaeiouáéíóúÁÉÍÓÚ][AEIOUaeiouáéíóúÁÉÍÓÚ])"
Regla3a = r"(?P<silaba1>[AEIOUaeiuoáéíóúÁÉÍÓÚ][^AEIOUaeiouáéíóúÁÉÍÓÚ])(?P<silaba2>[pcbgf][rl][AEIOUaeiouáéíóúÁÉÍÓÚ])|(?P<silaba1>[AEIOUaeiuoáéíóúÁÉÍÓÚ][^AEIOUaeiouáéíóúÁÉÍÓÚ])(?P<silaba2>[dt][r][AEIOUaeiouáéíóúÁÉÍÓÚ])"
Regla3b = r"(?P<silaba1>[AEIOUaeiuoáéíóúÁÉÍÓÚ][bdnmlr][s])(?P<silaba2>[^AEIOUaeiouáéíóúÁÉÍÓÚ][AEIOUaeiouáéíóúÁÉÍÓÚ])"
Regla3c = r"(?P<silaba1>[AEIOUaeiuoáéíóúÁÉÍÓÚ][s][t])(?P<silaba2>[^AEIOUaeiouáéíóúÁÉÍÓÚ][AEIOUaeiouáéíóúÁÉÍÓÚ])"
Regla4 = r"(?P<silaba1>([AEIOUaeiuoáéíóúÁÉÍÓÚ])([bdnmlr])([s]))(?P<silaba2>([^AEIOUaeiouáéíóúÁÉÍÓÚ])([^AEIOUaeiouáéíóúÁÉÍÓÚ])([AEIOUaeiouáéíóúÁÉÍÓÚ]))|(?P<silaba1>([AEIOUaeiuoáéíóúÁÉÍÓÚ])([s])([t]))(?P<silaba2>([^AEIOUaeiouáéíóúÁÉÍÓÚ])([^AEIOUaeiouáéíóúÁÉÍÓÚ])([AEIOUaeiouáéíóúÁÉÍÓÚ]))|(?P<silaba1>([AEIOUaeiuoáéíóúÁÉÍÓÚ])([^AEIOUaeiouáéíóúÁÉÍÓÚ])([^AEIOUaeiouáéíóúÁÉÍÓÚ])([pcbgf]))(?P<silaba2>([rl])([AEIOUaeiouáéíóúÁÉÍÓÚ]))"
Regla5a = r"(?P<silaba1>[aeoAEOÁÉÓáéó])(?P<silaba2>[iuIUüÜ])|(?P<silaba1>[iuIUüÜ])(?P<silaba2>[aeoAEOáéóÁÉÓ])|(?P<silaba1>[UÚuúü])(?P<silaba2>[iIíÍ])|(?P<silaba1>[iIíÍ])(?P<silaba2>[UÚuúü])"
Regla5b = r"(?P<silaba1>[aeoAEO])(?P<silaba2>[íúÍÚ])|(?P<silaba1>[íúÍÚ])(?P<silaba2>[aeoAEO])|(?P<silaba1>[AÁaá])(?P<silaba2>[AÁaá])|(?P<silaba1>[eéEÉ])(?P<silaba2>[eéEÉ])|(?P<silaba1>[iíIÍ])(?P<silaba2>[iíIÍ])|(?P<silaba1>[oóOÓ])(?P<silaba2>[oóÓO])|(?P<silaba1>[uúUÚ])(?P<silaba2>[uúUÚ])|(?P<silaba1>[aeoAEOáéóÁÉÓ])(?P<silaba2>[aeoAEOáéóÁÉÓ])"
Regla5c = r"(?P<silaba1>([aeoAEOÁÉÓáéó])([h]))(?P<silaba2>[iuIUüÜ])|(?P<silaba1>([iuIUüÜ])([h]))(?P<silaba2>[aeoAEOáéóÁÉÓ])|(?P<silaba1>([UÚuúü])([h]))(?P<silaba2>[iIíÍ])|(?P<silaba1>([iIíÍ])([h]))(?P<silaba2>[UÚuúü])"
Regla6 = r"(?P<silaba1>([iuIU])([aeoAEOáéóÁÉÓ])([iuIU]))"
Regla2 = Regla2a + "|" + Regla2b + "|" + Regla2c
Regla3 = Regla3a + "|" + Regla3b + "|" + Regla3c
Regla5 = Regla5a + "|" + Regla5b + "|" + Regla5c
R = Regla1 + "|" + Regla2 + "|" + Regla3 + "|" + Regla4 + "|" + Regla5 + "|" + Regla6
er_R = re.compile(R)

def cambiar_tile(s):
    D = {"á": "A", "é": "E", "í": "I", "ó": "O", "ú": "U"}
    er_tildes = re.compile(r"[áéíóúÁÉÍÓÚ]")
    nueva = []

    for palabra in s:
        coincidencias = er_tildes.findall(palabra)

        for letra_con_tilde in coincidencias:
            nueva_letra = D.get(letra_con_tilde.lower(), letra_con_tilde)
            palabra = palabra.replace(letra_con_tilde, nueva_letra)

        nueva.append(palabra)

    return nueva


def cambiar_vocal(s):
    D = {
        "a": "A",
        "e": "E",
        "i": "I",
        "o": "O",
        "u": "U",
    }

    for letra in s:
        s = s.replace(letra, D.get(letra, letra))
        s = s.replace(letra.upper(), D.get(letra, letra).upper())

    return s


def entonacion(lista):
    if isinstance(lista, str):
        try:
            lista = ast.literal_eval(lista)
        except:
            pass
    lista_str = ''.join(lista)

    with open("entonaciones.csv", "r", encoding="utf8") as f:
        for linea in f:
            if linea.startswith(lista_str):
                return linea.split(";")[1].strip()

    palabra = cambiar_tile(lista)

    # Compruebo si ya existe la palabra en el archivo
    with open("entonaciones.csv", "r", encoding="utf8") as f:
        for linea in f:
            if palabra == ast.literal_eval(linea.split(";")[1].strip()):
                return palabra

    # Compruebo tildes
    llana = False
    if lista == palabra:
        palabra = []
        er_llana = re.compile(r".*?[aeiouns]$")
        if er_llana.match(lista[-1]):
            llana = True
            for i in lista:
                if i == lista[len(lista) - 2]:
                    palabra.append(cambiar_vocal(i))
                else:
                    palabra.append(i)

        if not llana:
            for i in lista:
                if i == lista[len(lista) - 1]:
                    palabra.append(cambiar_vocal(i))
                else:
                    palabra.append(i)

    # Escribo el resultado en el archivo solo si no está presente
    with open("entonaciones.csv", "a", encoding="utf8") as sal:
        sal.write(f"{lista_str};{palabra}\n")

    return palabra




def cuenta_letras(s):
    mayuscula = not s.islower()
    return [len(s),mayuscula]

def contar_en_fichero(fichero):
    f = open(fichero,"r",encoding="utf8")
    sal=open("salida.csv","w", encoding="utf8")
    for linea in f:
        linea = linea.strip()
        print(linea,cuenta_letras(linea)[0],cuenta_letras(linea)[1],file=sal, sep= ";")
    f.close()
    sal.close()

def silabear(palabra):

    with open("silabas.csv", "r", encoding="utf8") as f:
        for linea in f:
            if linea.startswith(palabra):
                return linea.split(";")[1].strip()

    res = []
    inicio_s1 = 0
    inicio_s2 = 0
    m = er_R.search(palabra, 0)
    while m:
        inicio_s2 = m.start("silaba2")
        res.append(palabra[inicio_s1:inicio_s2])
        inicio_s1 = inicio_s2

        m = er_R.search(palabra, m.end() - 1)
    res.append(palabra[inicio_s2:])

    with open("silabas.csv", "a", encoding="utf8") as sal:
        # Escribo el resultado en el archivo
        sal.write(f"{palabra};{res}\n")

    return res


def menu():
    terminado = False
    while (not terminado):
        print("Elige una opción:")
        print("1. Contar letras de una palabra y decir si tiene mayúscula")
        print("2. Contar letras y decir si tiene mayúscula de cada una de las palabras de un fichero")
        print("3. Silabear una palabra")
        print("4. Encontrar la entonacion de una palabra")
        print("5. Salir")
        n = input()
        try:
            opcion = int(n)
            if opcion == 1:
                palabra = input("Introduce la palabra: ")
                print(cuenta_letras(palabra))
            elif opcion == 2:
                fichero = input("Introduce el nombre del fichero para analizar: ")
                contar_en_fichero(fichero)
            elif opcion == 3:
                palabra = input("Introduce la palabra: ")
                print(silabear(palabra))
            elif opcion == 4:
                palabra = input("Introduce la palabra: ")
                print(entonacion(silabear(palabra)))
            elif opcion == 5:
                terminado = True
            else:
                print("Recuerda introducir una opción valida (1,2 ó 3)")
        except:
            print("Introduce un número por favor")


menu()