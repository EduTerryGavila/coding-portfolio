--Para tener siempre activados patrones n+k
{-# LANGUAGE NPlusKPatterns #-}

--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-}

module ListaPar where 

import Data.List

data ListaP a = Lp [(Int, a)]
type ListaP2 a = [(Int, a)]

instance (Show a) => Show (ListaP a) where
    show :: (Show a) => (ListaP a) -> String
    show (Lp l) = concat[show(fst e) ++ "->" ++ show(snd e) ++ "\n" | e <- l]

comprimida :: Eq a => [a] -> ListaP2 a
comprimida l =  ajustar (concat[if x == (length l) || l!!x /= l!!(x-1) then [(x,y)] else [] | (x,y) <- zip [1..] l]) 0
          
ajustar :: Eq a => ListaP2 a -> Int -> ListaP2 a
ajustar l n
    | null l = []
    | otherwise = [((fst(head l))-n, (snd (head l)))] ++ ajustar (tail l) (fst(head l))

expandida :: Eq a => ListaP2 a -> [a]
expandida l = concat[aumentar x y | (x,y) <- zip (fst z) (snd z)]
            where z = unzip l

aumentar ::  Eq a => Int -> a -> [a]
aumentar n a
    | n == 0 = [] 
    | otherwise = [a] ++ aumentar (n-1) a