--Para tener siempre activados patrones n+k
{-# LANGUAGE NPlusKPatterns #-}

--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-}

data ArbolB a = Hoja | Rama (ArbolB a) a (ArbolB a)
    deriving Show

a = Rama (Rama (Rama (Hoja) 1 (Hoja)) 2 (Rama (Hoja) 3 (Hoja))) 4 (Rama (Rama (Hoja) 5 (Hoja)) 6 (Rama (Hoja) 7 (Hoja)))

tama :: ArbolB a -> Int
tama (Hoja) = 1
tama (Rama x n y) = 1 + tama x + tama y

aplanar :: ArbolB a -> [a]
aplanar (Rama (Hoja) n (Hoja)) = [n]
aplanar (Rama (Hoja) n (x)) = [n] ++ aplanar x
aplanar (Rama (x) n (Hoja)) = aplanar x ++ [n]
aplanar (Rama x n y) = aplanar x ++ [n] ++ aplanar y

pertenece :: Eq a => a -> ArbolB a -> Bool
pertenece e (Rama (Hoja) n (Hoja)) = n == e
pertenece e (Rama (Hoja) n (x)) = if n == e then True else pertenece e x
pertenece e (Rama (x) n (Hoja)) = if n == e then True else pertenece e x
pertenece e (Rama x n y) = if e == n then True else (pertenece e x || pertenece e y)

insertar :: (Ord a) => a -> ArbolB a -> ArbolB a
insertar e Hoja = (Rama Hoja e Hoja)
insertar e (Rama iz x dr) 
		| e <= x = Rama (insertar e iz) x dr
		| e > x = Rama iz x (insertar e dr)

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

crearArbolL :: Ord a => [a] -> ArbolB a
crearArbolL = foldr insertar Hoja

ordenarConArbol :: Ord a => [a] -> [a]
ordenarConArbol l = aplanar(crearArbolL l)

subconjunto :: Eq a => [a] -> [a] -> Bool
subconjunto xs ys
    | null xs = True
    | otherwise = if elem (head xs) ys then subconjunto (tail xs) ys else False

igualesC :: Eq a => [a] -> [a] -> Bool
igualesC xs ys = subconjunto xs ys && subconjunto ys xs