--Para tener siempre activados patrones n+k
{-# LANGUAGE NPlusKPatterns #-}

--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-}

import ListaPar
import Data.Char

type ListaPCh = ListaP2 Char

listaAcadena :: ListaPCh -> String
listaAcadena l = concat[show(fst e) ++ show(snd e)| e <- l]

cadenaComprimida :: String -> String
cadenaComprimida s = listaAcadena(comprimida s)

cadenaAlista :: String -> ListaPCh
cadenaAlista [] = []
cadenaAlista s = [(read nums, char)] ++ cadenaAlista resto
    where 
            (nums,restoL) = span isDigit s
            char = head restoL
            resto = tail restoL

cadenaExpandida :: String -> String
cadenaExpandida s = expandida(cadenaAlista s)

main :: IO()
main = do 
        putStrLn "Programa codificador/decodificador"
        putStrLn " c.- Codificar cadena "
        putStrLn " d.- Decodificar cadena "
        putStrLn "---------------------------Teclear Opcion: "
        x <- getChar
        putStrLn ""
        if elem x "cd" then analizarOpcion x 
            else main

analizarOpcion :: Char -> IO()
analizarOpcion x = do
                    putStrLn ""
                    putStrLn "Introduce la cadena"
                    cad <- getLine
                    if elem x "c" then
                        do
                            print (cadenaComprimida cad)
                    else
                        do
                            print (cadenaExpandida cad)