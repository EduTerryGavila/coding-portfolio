--Para tener siempre activados patrones n+k
{-# LANGUAGE NPlusKPatterns #-}

--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-}

import MatricesR
import Data.List

type Nombre = String

type Etiqueta = String

type FSet = [(Int, Float)]

type M = [(Etiqueta, FSet)]

data VLing = Vl {nombre::Nombre, numCon::Integer, et::[Etiqueta], m::M}

type Proposicion = (Nombre, Etiqueta)

data Regla = Prod {p1::Proposicion, p2::Proposicion}

vl1 :: VLing
vl1 = Vl {nombre = "X", numCon = 4, et = ["Grande", "Pequena"], m = [("Grande",  [(1, 0.0), (2, 0.2), (3, 0.7), (4, 1.0)]),("Pequena", [(1, 1.0), (2, 0.8), (3, 0.7), (4, 0.0)])]}
vl2 :: VLing
vl2 = Vl {nombre = "Y", numCon = 3, et = ["Pequena"], m = [("Pequena", [(1, 1.0), (2, 0.5), (3, 0.1)])]}

r1 = (Prod ("X", "Grande") ("Y", "Pequena"))

instance Show VLing where
    show :: VLing -> String
    show (Vl n i e m) = n ++ " toma valores en {" ++ fst(head m) ++ "} y puede ser {" ++ impl e ++ "}"

impl :: [Etiqueta] -> String
impl l
    | length l == 1 = (head l)
    | otherwise = (head l) ++ ", " ++ impl (tail l)

instance Show Regla where
    show :: Regla -> String
    show (Prod p1 p2) = fst p1 ++ " es " ++ snd p1 ++ " => " ++ fst p2 ++ " es " ++ snd p2

buscaM :: Nombre -> [VLing] -> M
buscaM n v = head[m e | e <- v, n == nombre e]

buscaSet :: Etiqueta -> M -> FSet
buscaSet et m = head[snd e | e <- m, fst e == et]

creaMatrizRelacion :: (Float->Float->Float) -> [VLing] -> Regla -> Matriz Float
creaMatrizRelacion g lv r = productoCartesianoF g [snd e | e <- op1] [snd e | e <- op2]
                        where op1 = (buscaSet (snd(p1 r)) (buscaM (fst(p1 r)) lv)) 
                              op2 = (buscaSet (snd (p2 r)) (buscaM (fst(p2 r)) lv))

creaRelacionMandami :: [VLing] -> Regla -> Matriz Float
creaRelacionMandami lv r = creaMatrizRelacion min lv r

creaRelacionLarsen :: [VLing] -> Regla -> Matriz Float
creaRelacionLarsen lv r = creaMatrizRelacion producto lv r

producto :: Float->Float->Float
producto x y = x * y

modusPonensG :: (Float->Float->Float) -> [VLing] -> Proposicion -> Regla -> FSet
modusPonensG g lv p r = [(x,y) |(x,y) <- zip [1..] (combinaMatrices max g [snd e | e <- op1] (creaMatrizRelacion g lv r))]
                    where op1 = (buscaSet (snd(p)) (buscaM (fst(p)) lv))

main :: IO()
main = do 
         putStrLn "Introduce un fichero con variables linguisticas: "
         fentrada <- getLine
         vars <- readFile fentrada
         let lv = words vars
         putStrLn "Introduce un fichero con la especificacion de una regla: "
         fentrada <- getLine
         vars <- readFile fentrada
         let r = vars
         putStrLn "Introduce un fichero con la especificacion de una composicion: "
         fentrada <- getLine
         vars <- readFile fentrada
         let c = vars
         let mp = modusPonensG min lv p c 
         print mp
