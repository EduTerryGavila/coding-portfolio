#include <iostream>
#include <regex>
#include <fstream>
#include <string>
#include <vector>
#include <cmath>
#include <algorithm>

using namespace std;

struct regla {  //Definicion del tipo regla el cual almacenara reglas de la base de conocimiento
    string r;
    string antecedente1;
    string antecedente2;
    string consecuente;
    string conjuncion;
    double fc;
};

struct hecho {  //Definicion del tipo hecho el cual almacenara hechos de la base de hechos y los que se vayan razonando
    string h;
    double fc;
    string objetivo;
};

ofstream ficheroSalida; //Creacion del fichero salida

string obtenerBasename(const string &ruta) {    //Funcion que extrae el nombre del fichero sin la ruta completa
    size_t posSlash = ruta.find_last_of("/\\");
    if (posSlash != string::npos) {
        return ruta.substr(posSlash + 1);
    }
    return ruta;
}

void imprimeCabecera(vector<hecho>& baseHechos, string meta){   //Funcion para imprimir la cabecera
    ficheroSalida << "==============================================" << endl;
    ficheroSalida << "Encadenamiento hacia atras" << endl;
    ficheroSalida << "Meta a demostrar: " << meta << endl;
    ficheroSalida << "Base de Hechos inicial: {";
    for (int i = 0; i < baseHechos.size(); i++){    //Se imprime le base de hechos
        ficheroSalida << baseHechos[i].h;
        if(i != baseHechos.size() - 1){
            ficheroSalida << ", ";
        }
    }
    ficheroSalida << "}" << endl;
    ficheroSalida << "==============================================" << endl << endl;
}

void imprimeCC(vector<string> CC){  //Se imprime el conjunto conflicto
    ficheroSalida << "Conjunto de Reglas Candidatas (CC): { ";
    for (const auto &c : CC) {
        ficheroSalida << c << " ";
    }
    ficheroSalida << "}" << endl;
}

void imprimeSeleccionRegla(vector<string> CC, vector<string> R){    //Se imprime la regla seleccionada y como queda el CC al quitarla
    ficheroSalida << "Regla seleccionada: " << R[0] << endl;
    ficheroSalida << "Después de eliminar la regla seleccionada, CC queda: { ";
    for (const auto &c : CC) {
        ficheroSalida << c << " ";
    }
    ficheroSalida << "}" << endl;
}

void imprimeNuevasMetas(vector<string> nuevasMetas){    //Se imprimen las nuevas metas
    ficheroSalida << "Nuevas metas: {";
    for (int i = 0; i < nuevasMetas.size(); i++){
        ficheroSalida << nuevasMetas[i];
        if(i != nuevasMetas.size()-1){
            ficheroSalida << ", ";
        }
    }
    ficheroSalida << "}" << endl;
}

void imprimeVerificar(string Nmet, vector<hecho>& baseHechos){  //Se imprime la meta que esta siendo verificada y la base de hechos
    ficheroSalida << "Verificando meta: " << Nmet << endl;
    ficheroSalida << "Base de Hechos actual: { ";
    for (int i = 0; i < baseHechos.size(); i++){
        ficheroSalida << baseHechos[i].h << " (FC=" << baseHechos[i].fc << ")";
        if(i != baseHechos.size()-1){
            ficheroSalida << ", ";
        }
    }
    ficheroSalida << " }" << endl;
}

vector<regla> obtenerReglasConsecuencia(string meta, const vector<regla>& reglas) { //Se guardan las reglas cuyo consecuente es la meta
    vector<regla> reglasMeta;
    for (size_t i = 0; i < reglas.size(); i++) {
        if (reglas[i].consecuente == meta) {
            reglasMeta.push_back(reglas[i]);
        }
    }
    return reglasMeta;
}

hecho nuevoHecho(string nHecho, vector<hecho>& baseHechos, const vector<regla>& reglas, vector<string>& visitados);

double caso3(double fc, double fc2) {   //Se calcula el caso3 de acuerdo a los fundamentos teoricos
    double resultado = fc * max(0.0,fc2);
    double maximo = max(0.0,fc2);
    ficheroSalida << "Caso3 ->  " << fc << " * " << maximo << " = " << resultado << endl;
    return resultado;
}

double caso2(double fac, double fc2) {  //Se calcula el caso2 de acuerdo a los fundamentos teoricos
    double res;
    if (fac >= 0 && fc2 >= 0) {
        res = fac + fc2 * (1 - fac);
        ficheroSalida << "Caso2 -> " << fac << " + " << fc2 << "*(1-" << fac << ") = " << res << endl;
    } else if (fac <= 0 && fc2 <= 0) {
        res = fac + fc2 * (1 + fac);
        ficheroSalida << "Caso2 -> " << fac << " + " << fc2 << "*(1+" << fac << ") = " << res << endl;
    } else {
        res = (fac + fc2) / (1 - min(abs(fac), abs(fc2)));
        ficheroSalida << "Caso2 -> (" << fac << " + " << fc2 << ") / (1 - " << min(abs(fac), abs(fc2)) << ") = " << res << endl;
    }
    return res;
}

hecho caso1(const regla& regla1, double fc, hecho fHecho, vector<hecho>& baseHechos, const vector<regla>& reglas, vector<string>& visitados) {  //Se calcula el caso1 de acuerdo a los fundamentos teoricos
    vector<string> antecedentes = {regla1.antecedente1, regla1.antecedente2};
    ficheroSalida << "Caso1 -> Regla " << regla1.r << " con conjunción '" << regla1.conjuncion << "':" << endl;
    ficheroSalida << "Antecedente 1: " << antecedentes[0] << " (FC inicial = " << fc << ")" << endl;
    for (size_t i = 1; i < antecedentes.size(); i++) {
        hecho nHecho = nuevoHecho(antecedentes[i], baseHechos, reglas, visitados);
        ficheroSalida << "Antecedente " << i+1 << ": " << antecedentes[i] << " (FC = " << nHecho.fc << ")" << endl;
        if (regla1.conjuncion == "y") {
            double fcAnterior = fc;
            fc = min(fHecho.fc, nHecho.fc);
            ficheroSalida << "AND: min(" << fcAnterior << ", " << nHecho.fc << ") = " << fc << endl;
        } else if (regla1.conjuncion == "o") {
            double fcAnterior = fc;
            fc = max(fHecho.fc, nHecho.fc);
            ficheroSalida << "OR: max(" << fcAnterior << ", " << nHecho.fc << ") = " << fc << endl;
        }
        fHecho.fc = fc;
    }
    return fHecho;
}

hecho procesarVariasReglas(const vector<regla>& reglas, double fc, hecho hMeta, vector<hecho>& baseHechos, const vector<regla>& reglasCompleta, vector<string>& visitados) {
    for (size_t i = 1; i < reglas.size(); i++) {    //Recorre las reglas aplicables desde la segunda
        vector<string> antecedentes = {reglas[i].antecedente1, reglas[i].antecedente2}; //Obtiene los antecedentes
        ficheroSalida << "\nRegla " << reglas[i].r << ":" << endl;
        hecho nHecho = nuevoHecho(antecedentes[0], baseHechos, reglasCompleta, visitados);  //Rellena un nuevo hecho con lo recien obtenido
        if (!reglas[i].conjuncion.empty()) {    //Si hay una conjuncion evalua el segundo antecedente y lo combina con el primero
            nHecho = caso1(reglas[i], nHecho.fc, nHecho, baseHechos, reglasCompleta, visitados);
        }
        double fc_rule = caso3(reglas[i].fc, nHecho.fc);    //Calcula el fc derivado de aplicar la regla
        ficheroSalida << "FC derivado de la regla " << reglas[i].r << " = " << fc_rule << endl;
        hMeta.fc = caso2(fc, fc_rule);  //Combina el fc acumulado con el nuevo fc derivado
        ficheroSalida << "FC acumulado para meta " << hMeta.h << " tras combinar reglas = " << hMeta.fc << endl;
        fc = hMeta.fc;  //Actualizacion de fc
    }
    return hMeta;
}

hecho procesarReglas(const vector<regla>& reglasFiltradas, double fc, hecho hMeta, hecho nHecho, vector<hecho>& baseHechos, const vector<regla>& reglasCompletaOriginal, vector<string>& visitados) {
    const regla& reglaActiva = reglasFiltradas[0];  //Selecciona la regla a activar
    ficheroSalida <<reglaActiva.r << " (regla activada)" << endl;

    if (!reglaActiva.conjuncion.empty()) {  //Si la regla activada no tiene conjuncion se realiza el caso1
        nHecho = caso1(reglaActiva, fc, nHecho, baseHechos, reglasCompletaOriginal, visitados);
    }

    hMeta.fc = caso3(reglaActiva.fc, nHecho.fc);    //Se guarda el fc del caso3 en el hecho
    ficheroSalida << "FC después de aplicar regla " << reglaActiva.r <<" en " << hMeta.h
                  << " = " << hMeta.fc << endl;

    if (reglasFiltradas.size() > 1) {   //Se comprueba si hay mas de una regla y se procesan
        hMeta = procesarVariasReglas(reglasFiltradas, hMeta.fc, hMeta, baseHechos, reglasCompletaOriginal, visitados);
    }

    return hMeta;
}

hecho factorCerteza(string meta, const vector<regla>& reglas, vector<hecho>& baseHechos, vector<string>& visitados) {   //Funcion que calcula el factor de certeza
    hecho hMeta;    //Se declara el hecho que rellenaremos con el fc
    hMeta.h = meta; //Se le asigna el nombre
    vector<regla> listaReglas = obtenerReglasConsecuencia(meta, reglas);    //se guarda en listaReglas las reglas devueltas por la funcion
    ficheroSalida << "\nCalculando FC para la meta: " << meta << endl;
    if (listaReglas.empty()) {  //Si no se encuentra como calcularlo se le da un fc de 0.0 debido a que 0.0 representa falta de conocimiento
        ficheroSalida << "No se encontraron reglas que concluyan " << meta << ". Se asumira FC=0.0" << endl;
        hMeta.fc = 0.0;
        return hMeta;
    }
    vector<string> antecedentes = {listaReglas[0].antecedente1, listaReglas[0].antecedente2};   //Se guardan los dos antecedentes de la primera regla de listaReglas
    hecho nHecho = nuevoHecho(antecedentes[0], baseHechos, reglas, visitados);  //Se guarda el nuevos hecho ya relleno
    double fc = nHecho.fc;
    ficheroSalida << "Aplicando regla activa (" << listaReglas[0].r << ") para " << meta << ":" << endl;
    ficheroSalida << "Antecedente: " << antecedentes[0] << " con FC = " << nHecho.fc << endl;
    hMeta = procesarReglas(listaReglas, fc, hMeta, nHecho, baseHechos, reglas, visitados); //Se procesan las reglas para calcular el fc
    ficheroSalida << "\nResultado final para " << meta << " -> FC: " << hMeta.fc << endl;
    return hMeta;
}

hecho nuevoHecho(string nHecho, vector<hecho>& baseHechos, const vector<regla>& reglas, vector<string>& visitados) {
    if (find(visitados.begin(), visitados.end(), nHecho) != visitados.end()) {  //Detecta un ciclo en la inferencia. Si nHecho ya fue visitado, evita recursion infinita devolviendo un fc de 0.0
        ficheroSalida << "Se detectó un ciclo con el hecho: " << nHecho << ". Retornando FC=0.0 para evitar recursión infinita." << endl;
        return {nHecho, 0.0, ""};
    }
    for (size_t i = 0; i < baseHechos.size(); i++) {    //Comprueba si el hecho se encuentra ya en la base de hechos
        if (baseHechos[i].h == nHecho) {
            ficheroSalida << "Hecho " << nHecho << " ya se encuentra en la base con FC = " << baseHechos[i].fc << endl;
            return baseHechos[i];
        }
    }
    ficheroSalida << "\nNuevo hecho -> " << nHecho << endl;
    visitados.push_back(nHecho);    //Si no se encontraba ya, introduce el nuevo hecho en visitados
    hecho h = factorCerteza(nHecho, reglas, baseHechos, visitados); //Calcula el fc del nuevo hehco
    baseHechos.push_back(h);    //Se introduce el nuevo hecho en la base de hechos
    visitados.erase(remove(visitados.begin(), visitados.end(), nHecho), visitados.end());  //Se elimina de visitados ya que ya se ha procesado
    return h;
}

vector<string> ExtraerAntecedentes(const vector<string>& R, const vector<regla>& reglas) {  //Se guardan en un vector los antecedentes de las reglas seleccionadas de las que ademas estan en el vector reglas 
    vector<string> nuevasMetas;
    for (const auto& regla : R) {
        for (const auto& r : reglas) {
            if (r.r == regla) {
                if (r.antecedente1 != "N/A")
                    nuevasMetas.push_back(r.antecedente1);
                if (r.antecedente2 != "N/A" && !r.antecedente2.empty())
                    nuevasMetas.push_back(r.antecedente2);
            }
        }
    }
    return nuevasMetas;
}

vector<string> EliminarMeta(string Nmet, vector<string> nuevasMetas) {  //Elimina todas las apariciones de Nmet del vector nuevasMetas y devuelve el resultado
    nuevasMetas.erase(remove(nuevasMetas.begin(), nuevasMetas.end(), Nmet), nuevasMetas.end());
    return nuevasMetas;
}

string SeleccionarMeta(const vector<string>& nuevasMetas) { //Selecciona la primera meta de las nuevas metas
    return nuevasMetas[0];
}

vector<string> EliminarRegla(const vector<string>& R, vector<string> CC) {  //Si alguna regla de CC es la regla anteriormente seleccionada se elimina de CC
    vector<string> CCC;
    for (const auto& reglaC : CC) {
        if (reglaC != R[0])
            CCC.push_back(reglaC);
    }
    return CCC;
}

vector<string> Resolver(const vector<string>& CC) { //Simplemente devuelve el vector con el ultimo elemento si no esta vacio
    if (!CC.empty())
        return { CC.back() };
    return {};
}

vector<string> Equiparar(const vector<regla>& reglas, string meta) {    //Funcion que da valor a CC
    vector<string> CC;  //Definicion de CC
    for (const auto &r : reglas) {  //Se recorren las reglas
        if (r.consecuente == meta) {    //Si una regla deriva en la meta se guarda en CC
            CC.push_back(r.r);
        }
    }
    return CC;
}

bool Verificar(string meta, vector<hecho>& baseHechos, const vector<regla>& reglas, vector<string>& visitados) {    //Funcion principal la cual llama a practicamente a todas las funciones del programa
    for (const auto &h : baseHechos) {  //Se recorre la base de hechos para comprobar si se tiene la meta en la base de hechos
        if (h.h == meta) {
            ficheroSalida << "La meta " << meta << " ya se encuentra verificada en la base." << endl;
            return true;    //Se devuelve verdadero si la meta esta en la base de hechos
        }
    }
    if (find(visitados.begin(), visitados.end(), meta) != visitados.end()) {    //Evita que se vuelva a procesar una meta que ya este siendo procesada
        ficheroSalida << "Ya se está verificando " << meta << endl;
        return false;
    }
    visitados.push_back(meta);  //Se guarda la meta para saber que se va a procesar y no hacerlo mas veces
    vector<string> CC = Equiparar(reglas, meta);    //Le pasamos a CC (conjunto conflicto) lo que devuelva la funcion Equiparar
    imprimeCC(CC);  //Imprime CC
    bool verificado = false;    //Se inicializa a falso la variable que comprobara de manera recursiva si se encuentra el objetivo
    
    while (!CC.empty() && !verificado) {    //Bucle que va analizando el CC hasta que este se vacie o se verifique el objetivo
        vector<string> R = Resolver(CC);    //Se guarda en R lo que devuelva Resolver
        CC = EliminarRegla(R, CC);  //Modifica CC
        imprimeSeleccionRegla(CC, R);   //Se imprimen los resultados de la funcion anterior 
        vector<string> nuevasMetas = ExtraerAntecedentes(R, reglas);    //Se guardan las nuevas metas
        while (!nuevasMetas.empty() && !verificado) {   //Se repite un bucle para cada nueva meta y mientras no se verifique el objetivo
            string Nmet = SeleccionarMeta(nuevasMetas); //Se guarda la nueva meta seleccionada en Nmet
            imprimeNuevasMetas(nuevasMetas);    //Se imprimen las actualizaciones realizadas en las anteriores funciones
            nuevasMetas = EliminarMeta(Nmet, nuevasMetas);  //Guarda en nuevas metas despues de eliminar Nmet
            imprimeVerificar(Nmet, baseHechos); //Se imprimen la meta que se esta verificando y la BH
            verificado = Verificar(Nmet, baseHechos, reglas, visitados);    //Llama de manera recursiva a esta funcion
        }
        if (verificado) {   //Si verificado es verdadero calculamos el FC y guardamos el hecho en la base
            hecho h = factorCerteza(meta, reglas, baseHechos, visitados);
            baseHechos.push_back(h);
        }
    }
    
    visitados.erase(remove(visitados.begin(), visitados.end(), meta), visitados.end()); //Elimina todas las apariciones de meta del vector visitados

    return verificado;  //Devuelve verdadero o falso en funcion de si se ha encontrado o no el objetivo
}

bool ENCADENAMIENTO_HACIA_ATRAS(vector<hecho>& baseHechos, string meta, const vector<regla>& reglas) {  //Funcion la cual devuelve el resultado del problema
    vector<string> visitados;   //Se declara el vector de visitados para no cometer errores en un futuro
    return Verificar(meta, baseHechos, reglas, visitados);  //Esta llamada devolvera el resultado del problema llamando a la funcion principal
}

int main(int argc, char* argv[]){
    string meta;    //Definicion de la meta a alcanzar
    
    if (argc != 3) {    //Comprueba que el numero de parametros es correcto y si no lo es envia un mensaje a la salida de error
        cerr << "Uso: " << argv[0] << " <archivo_BC> <archivo_BH>" << endl;
        return 1;
    }
    
    string fichero_BC = argv[1];    //Se guarda en la variable fichero_BC el nombre del fichero con la base de conocimiento
    string fichero_BH = argv[2];    //Se guarda en la variable fichero_BH el nombre del fichero con la base de hechos

    string nombreFichero_BC = obtenerBasename(fichero_BC);  //Llamada a la funcion que obtiene el nombre del fichero sin ruta completa
    string nombreFichero_BH = obtenerBasename(fichero_BH);
    
    string nombreSalida;    //Definicion de la variable que almacenara el nombre que tendra el fichero de salida
    size_t pos1 = nombreFichero_BC.rfind(".txt");   //Se comprueba si es un "txt"
    if(pos1 != string::npos) 
        nombreSalida = nombreFichero_BC.substr(0, pos1);    //Se guarda lo que hay antes de .txt
    else
        nombreSalida = nombreFichero_BC;
        
    size_t pos2 = nombreFichero_BH.rfind(".txt");   //Lo mismo pero con el BH
    if(pos2 != string::npos)
        nombreSalida += nombreFichero_BH.substr(0, pos2);   //Se le suma a lo anterior el BH para conseguir el formato de salida pedido
    else
        nombreSalida += nombreFichero_BH;
        
    nombreSalida += ".txt"; //Se le añade la extension
    
    ficheroSalida.open(nombreSalida.c_str());   //Intenta abrir el fichero de salida y si no lo consigue devuelve un error
    if (!ficheroSalida.is_open()) {
        cerr << "Error al crear el fichero de salida: " << nombreSalida << endl;
        return 1;
    }
    
    vector<regla> reglas;   //Vector para guardar las reglas leidas
    vector<hecho> baseHechos;   //Vector para guardar los hechos leidos
    string linea;   //String para guardar la linea que se esta leyendo
    
    {
        regex regla_regex(R"(^(R\d+): Si ([^ ]+)(?: (y|o) ([^ ]+))? Entonces ([^,]+), FC=(-?\d+\.?\d*))");  //Definicion de la expresion regular del BC
        smatch coincidencias;   
    
        ifstream archivo_BC(fichero_BC); //Inicializa archivo_BC y abre el fichero
        if (!archivo_BC.is_open()) {    //Comprueba que se ha podido abrir correctamente
            cerr << "Error al abrir el archivo " << fichero_BC << endl;
            return 1;
        }
    
        getline(archivo_BC, linea); //Guarda en linea el contenido de la primera linea de archivo_BC
    
        while (getline(archivo_BC, linea)) {    //Guarda en la variable linea el contenido de las lineas de BC una a una
            if (regex_search(linea, coincidencias, regla_regex)) {  //Se va guardando el contenido de cada linea en su correspondiente variable
                regla r;    //Definicion de la regla a rellenar
                r.r = coincidencias[1];     //Guarda el nombre de la regla
                r.antecedente1 = coincidencias[2];  //Guarda el primer antecedente
                r.antecedente2 = coincidencias[4];  //Guarda el segundo antecedente
                r.consecuente = coincidencias[5];   //Guarda el consecuente
                r.fc = stod(coincidencias[6]);  //Guarda el factor de certeza
                if (coincidencias[3].matched)
                    r.conjuncion = coincidencias[3];    //Guarda la conjuncion si la hay
                else
                    r.conjuncion = "";
                reglas.push_back(r);    //Guarda la regla creada en el vector de reglas
            } else {
                cerr << "No se pudo procesar la línea: " << linea << endl;  //Si no se consigue procesar alguna linea el programa envia un mensaje de error
            }
        }
        archivo_BC.close(); //Se cierra el fichero BC
    }
    
    {
        regex hecho_regex(R"(([^,]+), FC=(-?\d+\.?\d*))");  //Definicion de la expresion regular del BH
        smatch coincidencias;
    
        ifstream archivo_BH(fichero_BH);    //Inicializa archivo_BH y abre el fichero
        if (!archivo_BH.is_open()) {    //Comprueba que se ha podido abrir correctamente
            cerr << "Error al abrir el archivo " << fichero_BH << endl;
            return 1;
        }
    
        bool seLeyoObjetivo = false;
        while (getline(archivo_BH, linea)) {    //Guarda en linea el contenido de la primera linea de archivo_BH
            if (regex_match(linea, regex(R"(\d+)"))) {
                continue;
            }
            if (linea == "Objetivo") {  //Se busca la palabra "Objetivo" para guardar la meta de la linea de abajo
                seLeyoObjetivo = true;
                continue;
            }
            if (seLeyoObjetivo) {
                meta = linea;   //Guarda el objetivo en la variable meta
                seLeyoObjetivo = false;
                continue;
            }
            if (regex_search(linea, coincidencias, hecho_regex)) {
                hecho h;    //Definicion del hecho a rellenar
                h.h = coincidencias[1]; //Guarda el nombre del hecho
                h.fc = stod(coincidencias[2]);  //Guarda el factor de certeza
                baseHechos.push_back(h);    //Guarda el hecho creado en la base de hechos
            }
        }
        archivo_BH.close(); //Se cierra el fichero BH
    }
    
    imprimeCabecera(baseHechos, meta);  //Se llama a la funcion que imprime la cabecera

    if (ENCADENAMIENTO_HACIA_ATRAS(baseHechos, meta, reglas)) {     //Se llama al algoritmo de encadenamiento hacia atras
        ficheroSalida << "\n==============================================" << endl;
        ficheroSalida << "Éxito: La meta " << meta << " fue deducida satisfactoriamente." << endl;
    } else {
        ficheroSalida << "\n==============================================" << endl;
        ficheroSalida << "Fracaso: La meta " << meta << " no pudo ser deducida." << endl;
    }
    
    ficheroSalida.close();  //Se cierra el fichero de salida
    
    return 0;
}
