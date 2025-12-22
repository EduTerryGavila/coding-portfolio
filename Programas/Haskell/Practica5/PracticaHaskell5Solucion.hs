--Para tener siempre activados patrones n+k
{-# LANGUAGE NPlusKPatterns #-}

--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-}

module PracticaHaskell5Solucion where

--Ej 1:

ordenada :: Ord a => [a] -> Bool
ordenada xs 
    | null xs = True
    | length xs == 1 = True
    | otherwise = if menorIgual ((head xs), (head (tail xs))) then ordenada (drop 1 xs) else False
    where menorIgual (x,y) = (x <= y)

--Ej 2:

borrar :: Eq a => a -> [a] -> [a]
borrar n xs
    | null xs = xs
    | otherwise = if head xs == n then reverse(init(reverse xs)) else (take 1 xs) ++ (borrar n (tail xs))

--Ej 3:

insertar :: Ord a => a -> [a] -> [a]
insertar n xs
    | null xs = [n]
    | otherwise = if head xs >= n then [n] ++ xs else (take 1 xs) ++ (insertar n (tail xs))

--Ej 4:

ordInsercion :: Ord a => [a] -> [a]
ordInsercion xs
    | null xs = []
    | otherwise = insertar (head xs) (ordInsercion (tail xs))

--Ej 5:

minimo :: Ord a => [a] -> a
minimo xs = head (ordInsercion xs)

--Ej 6:

mezcla :: Ord a => [a] -> [a] -> [a]
mezcla xs ys = ordInsercion(xs ++ ys)

--Ej 7:

mitades :: [a] -> ([a], [a])
mitades xs = (take mitad xs, drop mitad xs)
    where mitad = div (length xs) 2

--Ej 8:

ordMezcla :: Ord a => [a] -> [a]
ordMezcla xs = mezcla xs []

--Ej 9: 

esPermutacion :: Ord a => [a] -> [a] -> Bool
esPermutacion xs ys = ordInsercion xs == ordInsercion ys