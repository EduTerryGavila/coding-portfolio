--Para tener siempre activados patrones n+k
{-# LANGUAGE NPlusKPatterns #-}

--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-}

primo :: Int -> Bool
primo x
    | x < 2 = False
    | otherwise = calculoPrimo x (x - 1)

calculoPrimo :: Int -> Int -> Bool
calculoPrimo x y
    | y > 1 = if x `mod` y == 0 then False else calculoPrimo x (y-1)
    | otherwise = True 

primos :: [Int]
primos = [x | x <- [1..], primo x]

productoDigitos :: Int -> Int
productoDigitos n = opera n 10

opera :: Int -> Int -> Int
opera x y
    | x < 10 = x
    | otherwise = x `mod` y * opera (x `div` 10) y

inverso :: Int -> Int
inverso = read.reverse.show

esPrimoSheldon :: Int -> Bool
esPrimoSheldon n
    | primo n == False = False
    | n /= head(reverse(take (productoDigitos n) primos)) = False
    | inverso n /= head(reverse(take (inverso(productoDigitos n)) primos)) = False
    | otherwise = True

primoSheldon :: Int
primoSheldon = head[x | x <- [1..], esPrimoSheldon x]