--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-} 

module PracticaHaskell7Solucion (ArbolB(Hoja,Rama),tam,aplanar,pertenece, insertar,crearArbolL,ordenarConArbol) where

-----RESOLUCI�N PR�CTICA 7 (primera parte) HASKELL

--1. �RBOLES BINARIOS DE B�SQUEDA

data ArbolB a = Hoja
 | Rama (ArbolB a) a (ArbolB a)
  deriving Show

ejArbol = Rama 
		(Rama 
			(Rama Hoja 1 Hoja) 
			2 
			(Rama Hoja 3 Hoja))
		 4 
		(Rama 
			(Rama Hoja 5 Hoja) 
			6 
			(Rama Hoja 7 Hoja))

tam :: (Ord a) => ArbolB a -> Int
tam Hoja = 0
tam (Rama a1 x a2) = tam a1 + 1 + tam a2 

aplanar :: (Ord a) => ArbolB a -> [a]
aplanar Hoja = []
aplanar (Rama iz x dr) = aplanar iz ++ [x] ++ aplanar dr

pertenece :: (Ord a) => a -> ArbolB a -> Bool
pertenece e Hoja = False
pertenece e (Rama iz x dr) 
		| e==x = True
		| e < x = pertenece e iz
		| e > x = pertenece e dr

insertar :: (Ord a) => a -> ArbolB a -> ArbolB a
insertar e Hoja = (Rama Hoja e Hoja)
insertar e (Rama iz x dr) 
		| e <= x = Rama (insertar e iz) x dr
		| e > x = Rama iz x (insertar e dr)

-----Para borrar un elemento:---------------------------------------------------------

borrar :: (Ord a) => a -> ArbolB a -> ArbolB a
borrar e Hoja = Hoja
borrar e (Rama iz x dr)
	| e < x = Rama  (borrar e iz) x dr
	| e > x = Rama iz x (borrar e dr)
	| vacio dr = iz
	| vacio iz = dr
	| otherwise = juntar iz dr 

vacio :: ArbolB a -> Bool
vacio Hoja = True
vacio _ = False

--Si nos fijamos, siempre se llama a esta funci�n con hijos iz y dr no vac�os (es decir, no son Hoja)

juntar :: Ord a => ArbolB a -> ArbolB a -> ArbolB a
juntar iz dr = Rama iz minimoD (borrar minimoD dr)
	where minimoD = minArbol dr 


minArbol :: Ord a => ArbolB a ->  a
minArbol t
	| vacio (hijoIzq t) = valorRaiz t
	| otherwise = minArbol (hijoIzq t)

hijoIzq ::  Ord a => ArbolB a -> ArbolB a
hijoIzq Hoja = error "No tiene hijo izquierdo\n"
hijoIzq (Rama t1 _ _) = t1

valorRaiz :: Ord a => ArbolB a -> a
valorRaiz Hoja = error "No hay valor en la raiz porque el arbol es vacio\n"
valorRaiz (Rama _ x _) = x
------------------------------------------------------------------------------

crearArbolL :: (Ord a) => [a] -> ArbolB a
crearArbolL = foldr insertar Hoja

crearArbolL2 :: (Ord a) => [a] -> ArbolB a
crearArbolL2 [] = Hoja
crearArbolL2 (x:xs) = insertar x (crearArbolL2 xs)

ordenarConArbol :: (Ord a) => [a] -> [a]
ordenarConArbol = aplanar . crearArbolL

-----------------------------------------------------------------  
--2. CONJUNTOS

subconjunto :: Eq a => [a] -> [a] -> Bool
subconjunto [] _ = True
subconjunto (x:xs) ys = elem x ys && subconjunto xs ys

igualesC :: Eq a => [a] -> [a] -> Bool
igualesC xs ys = subconjunto xs ys && subconjunto ys xs