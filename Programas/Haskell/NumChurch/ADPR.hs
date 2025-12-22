{-# OPTIONS_GHC -fno-warn-tabs #-}

module ADPR (Simbolo, Entrada, Regla, Gramatica, adpr) where

import Data.List

type Simbolo = Char

type Entrada = String

data Regla = R {letra::Simbolo, valores::[Simbolo]}
    
instance Show Regla where
    show :: Regla -> String
    show r = show(letra r) ++ " -> " ++ show (valores r)

data Gramatica = G [Regla]

gr = G [(R 'S' "AB"), (R 'A' "a"), (R 'A' "C"), (R 'B' "b"), (R 'C' "c"), (R 'C' "d")]

instance Show Gramatica where
    show :: Gramatica -> String
    show (G g) = concat[show(letra r) ++ " -> " ++ show(valores r) ++ "\n"| r <- g]

inicial :: Gramatica -> Simbolo
inicial (G g) = letra (head g)

esNT :: Simbolo -> Gramatica -> Bool
esNT s (G g) = elem s (noTerminalesG (G g))

reglasNT :: Simbolo -> Gramatica -> [Regla]
reglasNT s (G g) = [r | r <- g, letra r == s]

predict :: Gramatica -> Regla -> [Simbolo]
predict (G g) r = limpiaRepetidas(concat[if esNT (head y) (G g) then concat[predict (G g) t |t <- (reglasNT (head y) (G g))] else y | y <- p])
        where x = [e | e <- g, letra e == letra r]
              p = [valores z |z <- x]

limpiaRepetidas :: [Simbolo] -> [Simbolo]
limpiaRepetidas s = [y | (x,y) <- zip [0..] s, elem y (take (x) s) == False ]

noTerminalesG :: Gramatica -> [Simbolo]
noTerminalesG (G g) = [y | (x,y) <- zip [0..] l, (elem y (take (x) l)) == False]
        where l = [letra r | r <- g]

coincide :: Simbolo -> Entrada -> Entrada
coincide s e = if e!!0 == s then dropWhile (== s) e else error "*** Exception: No coincide s´ımbolo de prean´alisis con el de entrada"

seleccionarR :: Gramatica -> Simbolo -> Simbolo -> Regla
seleccionarR (G g) s1 s2 = if length reglas == 1 then head reglas else head[r | r <- reglas, elem s1 (predict (G g) r),esNT (head(valores r)) (G g) == True || (head(valores r)) == s2]
                            where reglas = reglasNT s2 (G g)

procedimiento :: Gramatica -> Simbolo -> Entrada -> Entrada
procedimiento (G g) s e = e

adpr :: Gramatica -> Entrada -> Bool
adpr g e = True