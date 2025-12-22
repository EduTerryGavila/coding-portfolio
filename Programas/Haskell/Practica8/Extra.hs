--Para tener siempre activados patrones n+k
{-# LANGUAGE NPlusKPatterns #-}

--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-}

module Extra (Monton(Mt), Tablero, xor, sumNim) where

import Practica

data Monton a = Mt [a]

type Tablero = Monton Int

type Binario = [Int]

type Binarios = Monton Binario

instance Show Tablero where
	show (Mt t) = concat[show f ++ " -> " ++ concat (replicate n " *") ++ "\n" |(f,n)<-zip [1..] t]

instance Show Binarios where
    show (Mt bn) = concat[show f ++ " -> " ++ (imprimeLista n) ++ "\n" |(f,n)<-zip [1..] bn]

entero2binario :: Int -> Binario
entero2binario n
    | n == 0 = [0]
    | n == 1 = [1]
    | otherwise = if n `mod` 2 == 0 then entero2binario (n `div` 2) ++ [0] else entero2binario (n `div` 2) ++ [1]

binario2entero :: Binario -> Int
binario2entero b = sum[n * 2^e |(e,n) <- zip [0..] (reverse b)]

xor :: Int -> Int -> Int
xor x y = binario2entero [(if x == y then 0 else 1) |(x,y) <- zip (cerosIzda(entero2binario x)) (cerosIzda(entero2binario y))]
    where cerosIzda l = replicate ((max (length (entero2binario x)) (length (entero2binario y))) - (length l)) 0 ++ l

sumNim :: [Int] -> Int
sumNim l = foldl xor (xor (head l) (l!!1)) (drop 2 l)

