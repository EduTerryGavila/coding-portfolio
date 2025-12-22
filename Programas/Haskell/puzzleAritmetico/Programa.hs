{-# OPTIONS_GHC -fno-warn-tabs #-}

import Expresiones
import ManipulaListas

ejExpr :: Expr
ejExpr = Apl Mul e1 e2 where e1 = Apl Sum (Num 1) (Num 50)
                             e2 = Apl Res (Num 25) (Num 10)

solucion :: Expr -> [Int] -> Int -> Bool
solucion e ns n = elem (numeros e) (elecciones ns) && valor e == [n]

combina :: Expr -> Expr -> [Expr]
combina e1 e2 = [Apl o e1 e2 | o <- ops]

expresiones :: [Int] -> [Expr]
expresiones [] = []
expresiones [n] = [Num n]
expresiones ns = [e | (is,ds) <- particiones ns
			, i <- expresiones is
			, d <- expresiones ds
			, e <- combina i d]


soluciones :: [Int] -> Int -> [Expr]
soluciones ns n = [e | ns' <- elecciones ns
		, e <- expresiones ns'
		, valor e == [n]]

main :: IO()
main = do   
            putStrLn "Introduce la lista de numeros separados por un espacio"
            entr <- getLine
            let list = [read e | e <- (words entr)] :: [Int]
            putStrLn "Introduce el numero objetivo"
            entr <- getLine
            let n = read entr :: Int
            putStrLn "Las soluciones son:"
            let sol = soluciones list n
            imprime sol

imprime :: [Expr] -> IO()
imprime [] = return ()
imprime e = do
                print (head e)
                imprime (tail e)