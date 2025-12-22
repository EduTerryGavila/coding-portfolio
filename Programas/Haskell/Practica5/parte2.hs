--Para tener siempre activados patrones n+k
{-# LANGUAGE NPlusKPatterns #-}

--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-}

import PracticaHaskell5Solucion

--Ej 1:

cAplica :: (a -> [b]) -> [a] -> [b]
cAplica f xs = concat (map f xs)

--Ej 2:

rotaLista :: [a] -> [[a]]
rotaLista xs = map (crearRotacion xs) [0 .. length xs - 1]


crearRotacion :: [a] -> Int -> [a]
crearRotacion lista n = (drop n lista) ++ (take n lista)

--Ej 3:

quitaCabeza :: Eq a => [a] -> [[a]] -> [[a]]
quitaCabeza xs xxs = filter (not . null) (map (borra xxs xs) [0 .. length xxs - 1])

borra :: Eq a => [[a]] -> [a] -> Int -> [a]
borra xxs xs n
    | null (xxs !! n) = []
    | otherwise = if igualCabeza (xxs !! n) xs then [] else xxs !! n

igualCabeza :: Eq a => [a] -> [a] -> Bool
igualCabeza xs ys
    | null ys = False
    | otherwise = if (head xs) == (head ys) then True else igualCabeza xs (tail ys)

--Ej 4:

rotaTitulo :: String -> [String]
rotaTitulo s = map unwords (filter (not . null) (map (rotaPalabras s palTriviales) [0 .. (length (words s))-1]))    
    where palTriviales = ["la","las","como", "de", "a", "con", "su","el", "un", "en"]

rotaPalabras :: String -> [String] -> Int -> [String]
rotaPalabras xs ys n
    | elem (xss !! n) ys = []
    | otherwise = (drop n xss) ++ (take n xss)
        where xss = words xs

--Ej 5:

kwic :: [String] -> [String]
kwic xs = ordMezcla combinada
    where combinada = borraRepetidos(concat(map (procesa xs) [0 .. length xs - 1]))

procesa :: [String] -> Int -> [String]
procesa xs n = rotaTitulo (xs !! n)

borraRepetidos :: [String] -> [String]
borraRepetidos xs
    | null xs = []
    | otherwise = [head xs] ++ borraRepetidos (filter (/= (head xs)) (tail xs)) 