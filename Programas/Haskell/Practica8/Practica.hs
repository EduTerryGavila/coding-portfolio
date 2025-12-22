--Para tener siempre activados patrones n+k
{-# LANGUAGE NPlusKPatterns #-}

--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-}

module Practica where

import PracticaHaskell7Solucion


main = do 
        putStr "Fichero de entrada: "
        fentrada <- getLine
        cad <- readFile fentrada
        putStr "Dato para percentil: "
        p <- getLine
        putStr "El dato "
        putStr p
        putStr " se encuentra en el percentil "
        print (getPercent (read p) (recogeDatos cad))
        putStrLn " de la lista: "
        putStrLn (imprimeLista (recogeDatos cad))


imprimeLista :: [Int] -> String
imprimeLista l
    | length l == 1 = show (head l)
    | otherwise = show (head l) ++ " " ++ imprimeLista (tail l)

getPercent :: Int -> [Int] -> Int
getPercent n l = ((menores n l) * 100) `div` (length l)

menores :: Int -> [Int] -> Int
menores n l = length[e | e <- l, e <= n]

recogeDatos :: String -> [Int]
recogeDatos  = ordenarConArbol.(map read).words

