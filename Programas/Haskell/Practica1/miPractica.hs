--Para tener siempre activados patrones n+k
{-# LANGUAGE NPlusKPatterns #-}

--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-} 

--Ejercicio 1:

maximo :: (Integer, Integer) -> Integer
maximo (x,y) = if x > y then x else y 

maximoc :: Integer -> Integer -> Integer
maximoc x y = if x > y then x else y 

--Ejercicio 2:

cuadrado :: Float -> Float
cuadrado x = x*x

areaCirculo :: Float -> Float
areaCirculo x = (22/7) * cuadrado x

--Ejercicio 3:

fibonacci :: Integer -> Integer
fibonacci 0 = 0
fibonacci 1 = 1
fibonacci n = fibonacci (n-2) + fibonacci (n-1)

fibonaccig :: Integer -> Integer
fibonaccig n
    | n == 0 = 0
    | n == 1 = 1
    | otherwise = fibonaccig (n-1) + fibonaccig(n-2)

--Ejercicio 4:

absol :: Integer -> Integer
absol x = if x < 0 then -x else x

--Ejercicio 5:

cuadradoN :: Integer -> Integer
cuadradoN x = x*x

aumentar :: Integer -> Integer
aumentar x = (x+1) * (x+1)

aumentar2 :: Integer -> Integer
aumentar2 x = (x*x) + 1

sucesor :: Integer -> Integer
sucesor = (+1)

aumentarN :: Integer -> Integer
aumentarN = cuadradoN.sucesor

aumentar2N :: Integer -> Integer
aumentar2N = sucesor.cuadradoN

--Ejercicio 6:

nAnd :: Bool -> Bool -> Bool
nAnd x y = if x == True && y == True then False else True 

nAnd2 :: Bool -> Bool -> Bool
nAnd2 x y
    | x == True && y == True = False
    | otherwise = True

--Ejercicio 7:

xor :: Bool -> Bool -> Bool
xor x y = if x /= y then True else False

xor2 :: Bool -> Bool -> Bool
xor2 False False = False
xor2 True True = False
xor2 x y = True

--Ejercicio 8:

minimoTres :: Integer -> Integer -> Integer -> Integer
minimoTres x y z
    | x < y && x < z = x
    | y < x && y < z = y
    | otherwise = z

--Ejercicio 9:

maximoTres :: Integer -> Integer -> Integer -> Integer
maximoTres x y z
    | x > y && x > z = x
    | y > x && y > z = y
    | otherwise = z

--Ejercicio 10:

numeroCentral :: Integer -> Integer -> Integer -> Integer
numeroCentral x y z
    | maximoTres x y z /= x && minimoTres x y z /= x = x
    | maximoTres x y z /= y && minimoTres x y z /= y = y
    | otherwise = z

--Ejercicio 11:

productoRango :: Integer -> Integer -> Integer
productoRango m n 
    | n < m = 0
    | n == m = n
    | otherwise = m * productoRango(m+1) n

factorial :: Integer -> Integer
factorial 0 = 1
factorial n = productoRango 1 n

--Ejercicio 12:

suma :: Integer -> Integer -> Integer
suma x y = x + y

prod :: Integer -> Integer -> Integer
prod x y 
    | x == 0 || y == 0 = 0
    | otherwise = suma x (prod x (y-1))

