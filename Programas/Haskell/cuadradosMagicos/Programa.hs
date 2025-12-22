--Para tener siempre activados patrones n+k
{-# LANGUAGE NPlusKPatterns #-}

--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-}

import Matrices
import Data.List

todosIguales :: Eq a => [a] -> Bool
todosIguales l = if length[e | e <- l, e /= el ] > 0 then False else True
                where el = head l

esPermutacion :: Eq a => [a] -> [a] -> Bool
esPermutacion l s = if length[e | e <- (permutations l), null(e \\ s)] > 0 || length[e | e <- (permutations s), null(e \\ l)] > 0 then True else False

esCuadradoMagico :: Matriz -> Bool
esCuadradoMagico (Producto l) = if matrizCuadrada (Producto l) && num == sum(diagonalSec (Producto l)) && todosIguales(sumasDeFilas (Producto l)) && (head (sumasDeFilas (Producto l))) == num && todosIguales(sumasDeColumnas (Producto l)) && (head (sumasDeColumnas (Producto l))) == num && numCorrect (Producto l) then True else False
                        where num = sum (diagonalPral (Producto l))

cuadradosMagicos :: [Matriz]
cuadradosMagicos = [Producto e | e <- [[x,y,z] | x <- vec, y <- vec, z <- vec],numCorrect (Producto e), esCuadradoMagico (Producto e)]
                                        where vec = [[x,y,z] | x <- [1..9], y <- [1..9], z <- [1..9],x /= y && x/= z && y /= z,x+y+z == 15]


numCorrect :: Matriz -> Bool
numCorrect (Producto l) = if length[e | e <- (permutations(concat l)), null([1..nuM] \\ e)] > 0 then True else False
                                    where nuM = (length (head l)) * (length (head l))

main :: IO()
main = do 
            putStrLn "Introduce una matriz, una fila por linea (una linea vacia para finalizar):"
            f11 <- getChar
            esp <- getChar
            f12 <- getChar
            esp <- getChar
            f13 <- getChar
            esp <- getChar
            let f1 = [fromEnum f11 - 48, fromEnum f12 - 48, fromEnum f13 - 48]
            
            f21 <- getChar
            esp <- getChar
            f22 <- getChar
            esp <- getChar
            f23 <- getChar
            esp <- getChar
            let f2 = [fromEnum f21 - 48, fromEnum f22 - 48, fromEnum f23 - 48]

            f31 <- getChar
            esp <- getChar
            f32 <- getChar
            esp <- getChar
            f33 <- getChar
            esp <- getChar
            let f3 = [fromEnum f31 - 48, fromEnum f32 - 48, fromEnum f33 - 48]

            let mat = [f1,f2,f3]

            putStrLn "La matriz"

            print (Producto mat)

            if esCuadradoMagico (Producto mat) 
                then 
                    do
                        putStrLn "es un cuadrado magico"
                else
                    do
                        putStrLn "no es un cuadrado magico"