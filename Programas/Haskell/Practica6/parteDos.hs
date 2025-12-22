--Para tener siempre activados patrones n+k
{-# LANGUAGE NPlusKPatterns #-}

--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-}

import Data.Char
import Data.List
import System.IO

-- 1

type Mensaje = String

-- 2

minusculaAint :: Char -> Int
minusculaAint c = ord c - ord 'a'

mayusculaAint :: Char -> Int
mayusculaAint c = ord c - ord 'A'

intAminuscula :: Int -> Char
intAminuscula e = chr (e + ord 'a')

intAmayuscula :: Int -> Char
intAmayuscula e = chr (e + ord 'A')

-- 3

desplaza :: Int -> Char -> Char
desplaza n c 
    | elem c ['a'..'z'] = int2minuscula ((minuscula2int c+n) `mod` 26)
    | elem c ['A'..'Z'] = int2mayuscula ((mayuscula2int c+n) `mod` 26)
    | otherwise = c