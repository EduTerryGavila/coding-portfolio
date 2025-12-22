--Para tener siempre activados patrones n+k
{-# LANGUAGE NPlusKPatterns #-}

--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-}

module Matrices where
import Data.List

data Matriz = Producto [[Int]]

instance Show Matriz where
    show :: Matriz -> String
    show (Producto l) = concat[(mostrarComp y) ++ "\n" | (x,y) <- zip [1..] l]

matriz15 = (Producto [[2,9,4],[7,5,3],[6,1,8]])
matrizB = (Producto [[2,9,4],[7,5,3],[6,1,9]])

mostrarComp :: [Int] -> String
mostrarComp l = concat[if x == 1 then "(" ++ show y ++ " " else if x == length l then show y ++ ")" else show y ++ " " | (x,y) <- zip [1..] l]

mAl :: Matriz -> [[Int]]
mAl (Producto l) = l

lAm :: [[Int]] -> Matriz
lAm l = (Producto l)

sumasDeFilas :: Matriz -> [Int]
sumasDeFilas (Producto l) = [sum y | (x,y) <- zip [1..] (mAl (Producto l))]

sumasDeColumnas :: Matriz -> [Int]
sumasDeColumnas (Producto l) = sumasDeFilas(Producto (transpose l))

matrizCuadrada :: Matriz -> Bool
matrizCuadrada (Producto l) = if length l == length(head l) && mismaLong l then True else False

mismaLong :: [[Int]] -> Bool
mismaLong l = if length[e | e <- l, tam /= length e] > 0 then False else True
                    where tam = length (head l)

diagonalPral :: Matriz -> [Int]
diagonalPral (Producto l) = if matrizCuadrada (Producto l) then [y!!(x-1) | (x,y) <- zip [1..] l] else error "La matriz no es cuadrada"

diagonalSec :: Matriz -> [Int]
diagonalSec (Producto l) = if matrizCuadrada (Producto l) then [y!!(x-1)|(x,y) <- zip (reverse[1..(length l)]) (transpose l)] else error "La matriz no es cuadrada"

