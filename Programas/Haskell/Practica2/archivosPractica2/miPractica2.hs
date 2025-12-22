--Para tener siempre activados patrones n+k
{-# LANGUAGE NPlusKPatterns #-}

--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-} 

import ImagenesSVG

--Ej 1:

cuatroImg :: Imagen -> Imagen
cuatroImg imagen = encima (imagen `junto_a` (giraV (invierte_color imagen))) ((invierte_color imagen) `junto_a` (giraV imagen))

--Ej 2:

cuadroBlanco :: Int -> Imagen
cuadroBlanco n
    | n <= 1 = blanco
    | otherwise = blanco `encima` cuadroNegro(n-1)

cuadroNegro :: Int -> Imagen
cuadroNegro n
    | n <= 1 = negro
    | otherwise = negro `encima` cuadroBlanco(n-1)

ajedrezB :: Int -> Int -> Imagen
ajedrezB n m
    | m <= 1 = cuadroBlanco(n)
    | otherwise = cuadroBlanco(n) `junto_a` ajedrezN n (m-1)


ajedrezN :: Int -> Int -> Imagen
ajedrezN n m
    | m <= 1 = cuadroNegro(n)
    | otherwise = cuadroNegro(n) `junto_a` ajedrezB n (m-1)

--Ej 3:

lineaB :: Int -> Int -> Imagen  
lineaB y x  -- x filas | y columnas
    | y <= 1 && y == (8-x+1) = caballo
    | y <= 1 = blanco 
    | y == x = caballo `junto_a` lineaN (y-1) x
    | otherwise = blanco `junto_a` lineaN (y-1) x

lineaN :: Int -> Int -> Imagen  
lineaN y x  -- x filas | y columnas
    | y <= 1 && y == (8-x+1) = giraV(invierte_color caballo)
    | y <= 1 = negro 
    | y == x = giraV(invierte_color caballo) `junto_a` lineaB (y-1) x
    | otherwise = negro `junto_a` lineaB (y-1) x