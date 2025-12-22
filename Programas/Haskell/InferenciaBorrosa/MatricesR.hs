--Para tener siempre activados patrones n+k
{-# LANGUAGE NPlusKPatterns #-}

--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-}

module MatricesR (Matriz, productoCartesianoF, combinaMatrices, funv) where
import Data.List

type Matriz a = [[a]]

productoCartesianoF :: (a-> a-> a) -> [a] -> [a] -> Matriz a
productoCartesianoF f v1 v2 = [[f e g | g <- v2] | e <- v1]

combinaMatrices :: (a->a->a)->(a->a->a)-> [a] -> Matriz a -> [a]
combinaMatrices mx mn m1 m2 = [funv mx [mn x y |(x,y) <- zip m1 f] | f <- (transpose m2)] 

funv :: (a->a->a) -> [a] -> a
funv f l = foldr f (head l) (tail l)