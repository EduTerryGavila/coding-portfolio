--Para tener siempre activados patrones n+k
{-# LANGUAGE NPlusKPatterns #-}

--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-} 

module tema4funcs.hs where

-- FUNCIONES TEMA 4 --

cuadrado :: Integer -> Integer
cuadrado x = x * x

minimo ::(Integer,Integer) -> Integer
minimo (x,y) = if x <= y then x else y

tres :: Integer -> Integer
tres x = 3

infinito :: Integer
infinito = infinito + 1

multiplicar :: (Integer,Integer) -> Integer
multiplicar (x,y)= if x==0 then 0 else x*y

doble,doble' :: Integer -> Integer
doble x = x + x
doble' x = 2 * x

dosVeces :: (Integer -> Integer, Integer) -> Integer
dosVeces (f,x) = f (f x)

minimoc :: Integer -> Integer -> Integer
minimoc x y  = if x <= y then x else y

suma :: (Integer,Integer) -> Integer
suma (x,y) = x+y

sumac :: Integer -> (Integer -> Integer)
sumac x y = x+y

sucesor = sumac 1

dosVecesc :: (Integer -> Integer)->(Integer -> Integer)
dosVecesc f x = f (f x)

cuarta :: Integer -> Integer 
cuarta = dosVecesc cuadrado

curry' :: ((a,b) -> c)-> (a -> b -> c)
curry' f x y = f(x,y)

sumac' ::  Integer -> (Integer -> Integer)
sumac' = curry' suma

--(.) :: (b -> c) ->(a -> b) ->(a -> c)
--(f . g) x = f (g x)

cuarta' :: Integer -> Integer
cuarta' = cuadrado . cuadrado

cuarta'' :: Integer -> Integer
cuarta'' x = cuadrado (cuadrado x)

pi' :: Float
pi' = 3.14159

fact n = product [1..n]

fact' 0 = 1
fact' x = x * fact (x-1)

not' True = False
not' False = True

cabeza (x:_) = x
cola (_:xs) = xs

f (x:xs) = x:x:xs

f' p@(x:xs) = x:p

x `elev` 0 = 1
x `elev` (n+1) = x * (x `elev` n)

fact'' 0 = 1
fact'' (x+1) = (x+1) * fact x

--Incorrecto:
--son_iguales x x = True
--son_iguales x y = False

--Correcto:
son_iguales x y = if x==y then True else False

minimo' :: (Integer,Integer) -> Integer
minimo' (x,y)
	| x <= y = x
	| x > y = y

minimo'' x y | x <= y = x
 		  | otherwise = y

signo :: Integer -> Integer
signo x
	| x < 0 = -1
	| x == 0 = 0
	| x > 0 = 1

signo' :: Integer -> Integer
signo' x = if x < 0 then -1 else
		if x == 0 then 0 else 1


fact''' :: Integer -> Integer
fact''' n
   | n==0 = 1
   | n>0 = n * fact'''(n-1)
   | n<0 = error "No se puede calcular el factorial de un numero negativo" 

f'' :: (Float, Float) -> Float
f''(x,y) = (a + 1) * (a + 2) where a = (x + y) / 2

f''' :: (Float, Float) -> Float
f'''(x,y) = (a + 1) * (b + 2)
			where a = (x + y) / 2
			      b = (x + y) / 3

f'''' :: (Float, Float) -> Float
f''''(x,y) = (a + 1) * (b + 2)
				where a = (x + y) / 2 ; b = (x + y) / 3

f''''' :: Integer -> Integer -> Integer
f''''' x y
	| x <= 10 = x + a
	| x > 10 = x - a
	  where a = cuadrado (y + 1)

paridad x = case (x `mod` 2) of
 0 -> "par"
 1 -> "impar"

paridad' x
     | (x `mod` 2)==0 = "par" 
     | (x `mod` 2)==1 = "impar"

fact'''' :: Integer -> Integer
fact'''' n = case n of
	       0 -> 1
	       otherwise -> n * fact'''' (n-1)

