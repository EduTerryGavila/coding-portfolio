--Para tener siempre activados patrones n+k
{-# LANGUAGE NPlusKPatterns #-}

--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-}

ducci :: [Int] -> [[Int]]
ducci l
    | comprobacion l = [[0, 0, 0, 0]] 
    | otherwise = [l] ++ ducci d
        where d = [abs (l !! 0 - l !! 1), abs (l !! 1 - l !! 2), abs (l !! 2 - l !! 3), abs (l !! 3 - l !! 0)]

comprobacion :: [Int] -> Bool
comprobacion l = [e | e <- l, e /= 0] == []