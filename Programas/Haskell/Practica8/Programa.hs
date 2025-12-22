--Para tener siempre activados patrones n+k
{-# LANGUAGE NPlusKPatterns #-}

--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-}

import Extra
import Data.Char

type Fila = Int

type Elemento = Int

type Jugador = Int

inicial :: Tablero
inicial = Mt [3,4,5]::Tablero

terminado :: Tablero -> Bool
terminado (Mt l) = if length[e | e <- l, e /= 0] > 0 then False else True

valida :: Tablero -> Fila -> Elemento -> Bool
valida (Mt l) f e = if l!!(f-1) >= 1 then True else False

movimiento :: Tablero -> Fila -> Elemento -> Tablero
movimiento (Mt l) f e = (Mt [if (f-1) == x then y-e else y | (x,y) <- zip [0..] l])

esNumero :: String -> Bool
esNumero st = and[isDigit x | x <- st] 

leeNumero :: String -> IO Int
leeNumero s = do 
                putStr s
                p <- getLine
                if (esNumero p) then 
                    return (read p) 
                else 
                    do  
                        putStrLn "ERROR: Numero incorrecto"
                        leeNumero s

calculaJugada :: Tablero -> (Fila, Elemento)
calculaJugada (Mt t) = if totalSumNim == 0
                       then head [(i, 1) | (i, p) <- zip [1..] t, p > 0]
                       else head [(i, p - (p `xor` totalSumNim)) | (i, p) <- zip [1..] t, (p `xor` totalSumNim) < p]
                                 where totalSumNim = sumNim t

juego :: Tablero -> IO ()
juego (Mt t) = do    
                print (Mt t)
                putStrLn "-- Tu turno --"
                putStr "Elige numero de fila: "
                f <- getLine
                putStr "Estrellas que borras: "
                e <- getLine
                let fila = (read f) :: Int
                let estrellas = (read e) :: Int
                if (valida (Mt t) fila estrellas)
                    then do
                            let tableroAct = (movimiento (Mt t) fila estrellas)
                            print (tableroAct)
                            if (terminado tableroAct)
                                then do
                                        putStrLn "Has ganado!!"
                            else 
                                do
                                    putStrLn "-- Turno del ordenador --"
                                    let resOrd = (calculaJugada tableroAct)
                                    putStrLn ("El ordenador elimina " ++ show (fst resOrd) ++ " * de la fila " ++ show (snd resOrd))
                                    let tableroOrdF = (movimiento tableroAct (fst(resOrd)) (snd(resOrd)))
                                    if (terminado tableroOrdF)
                                        then do
                                                putStrLn "Has perdido!!"
                                    else
                                        do
                                            juegoSinPrint tableroOrdF
                else
                    do
                        putStrLn "ERROR: Jugada incorrecta"
                        juegoSinPrint (Mt t)

juegoSinPrint :: Tablero -> IO ()
juegoSinPrint (Mt t) = do    
                        putStrLn "-- Tu turno --"
                        putStr "Elige numero de fila: "
                        f <- getLine
                        putStr "Estrellas que borras: "
                        e <- getLine
                        let fila = (read f) :: Int
                        let estrellas = (read e) :: Int
                        if (valida (Mt t) fila estrellas)
                            then do
                                    let tableroAct = (movimiento (Mt t) fila estrellas)
                                    print (tableroAct)
                                    if (terminado tableroAct)
                                        then do
                                                putStrLn "Has ganado!!"
                                    else 
                                        do
                                            putStrLn "-- Turno del ordenador --"
                                            let resOrd = (calculaJugada tableroAct)
                                            putStrLn ("El ordenador elimina " ++ show (fst resOrd) ++ " * de la fila " ++ show (snd resOrd))
                                            print (snd(resOrd))
                                            let tableroOrdF = (movimiento tableroAct (fst(resOrd)) (snd(resOrd)))
                                            if (terminado tableroOrdF)
                                                then do
                                                        putStrLn "Has perdido!!"
                                            else
                                                do
                                                    juegoSinPrint tableroOrdF
                        else
                            do
                                putStrLn "ERROR: Jugada incorrecta"
                                juegoSinPrint (Mt t)

nim :: IO ()
nim = juego inicial
