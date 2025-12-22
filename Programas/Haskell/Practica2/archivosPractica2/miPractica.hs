--Para tener siempre activados patrones n+k
{-# LANGUAGE NPlusKPatterns #-}

--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-} 

import ImagenesSVG

--Ej 1:

cuatroImg :: Imagen -> Imagen
cuatroImg imagen = encima (imagen `junto_a` (giraV (invierte_color imagen))) ((invierte_color imagen) `junto_a` (giraV imagen))

--Ej 2:

negroBlanco :: Integer -> Imagen
negroBlanco n
    | n <= 1 = negro
    | otherwise = negro `junto_a` blancoNegro(n-1)

blancoNegro :: Integer -> Imagen
blancoNegro n
    | n <= 1 = blanco
    | otherwise = blanco `junto_a` negroBlanco(n-1)

ajedrezBlanco :: Integer -> Integer -> Imagen
ajedrezBlanco n m
	| n <= 1 = negroBlanco m
	| otherwise = negroBlanco m `encima` ajedrez (n-1) m

ajedrez :: Integer -> Integer -> Imagen
ajedrez n m 
    | n <= 1 = blancoNegro m
	| otherwise = blancoNegro m `encima` ajedrezBlanco (n-1) m

--Ej 3:

negroBlanco' :: Integer -> Integer -> Imagen
negroBlanco' m n
	| m <=1 && m==(8-n+1) = negro'
	| m <= 1 = negro
	| m==(8-n+1)  = negro' `junto_a` blancoNegro' (m-1) n
	| otherwise = negro `junto_a` blancoNegro' (m-1) n

blancoNegro' :: Integer -> Integer -> Imagen
blancoNegro' m n
	| m <=1 && m==n = blanco'
	| m <= 1 = blanco  
	| m==n = blanco' `junto_a` negroBlanco' (m-1) n
	| otherwise = blanco `junto_a` negroBlanco' (m-1) n

ajedrezNegro' :: Integer -> Integer -> Imagen
ajedrezNegro' n m
	| n <= 1 = negroBlanco' m n
	| otherwise = negroBlanco' m n `encima` ajedrezBlanco' (n-1) m

ajedrezBlanco' :: Integer -> Integer -> Imagen
ajedrezBlanco' n m
	| n <= 1 = blancoNegro' m n
	| otherwise = blancoNegro' m n `encima` ajedrezNegro' (n-1) m

ajedrez' :: Imagen
ajedrez'= ajedrezBlanco' 8 8

blanco' = caballoPequeño 
negro' = invierte_color (giraV caballoPequeño)