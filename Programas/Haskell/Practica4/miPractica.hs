--Para tener siempre activados patrones n+k
{-# LANGUAGE NPlusKPatterns #-}

--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-} 

--Racionales:
--Ej 1:

type Racional = (Integer,Integer)

simplificaRac :: Racional -> Racional
simplificaRac r 
    | signum(snd r) == -1 && signum(fst r) == 1 = (((fst r) `div` d) * (-1), abs((snd r) `div` d))
    | signum(snd r) == -1 && signum(fst r) == -1 = (((fst r) `div` d) * (-1), ((snd r) `div` d) * (-1))
    | otherwise = ((fst r) `div` d, (snd r) `div` d)
    where d = gcd (fst r) (snd r)

multRac :: Racional -> Racional -> Racional
multRac x y = simplificaRac(((fst x) * (fst y)) , ((snd x) * (snd y)))

divRac :: Racional -> Racional -> Racional
divRac x y = simplificaRac(((fst x) * (snd y), (snd x) * (fst y)))

sumRac :: Racional -> Racional -> Racional
sumRac x y
    | (snd x) == (snd y) = simplificaRac((fst x) + (fst y), (snd x))
    | otherwise = simplificaRac((((fst x) * snd(y)) + ((fst y) * snd(x))) , ((snd x) * (snd y)))

resRac :: Racional -> Racional -> Racional
resRac x y
    | (snd x) == (snd y) = simplificaRac((fst x) - (fst y), (snd x))
    | otherwise = simplificaRac((((fst x) * snd(y)) - ((fst y) * snd(x))) , ((snd x) * (snd y)))

muestraRac :: Racional -> String
muestraRac r = show(fst d) ++ "/" ++ show(snd d)
    where d = simplificaRac r

--Ej 2:

data Racional2 = Rac {p::Integer, s::Integer}

showRacional2 :: Racional2 -> String
showRacional2 (Rac x y) = show x ++ "/" ++ show y

instance Show Racional2 where
    show (Rac x y) = showRacional2 (Rac x y)

simplificaRac2 :: Racional2 -> Racional2
simplificaRac2 r 
    | signum(s r) == -1 && signum(p r) == 1 = Rac (((p r) `div` d) * (-1)) (abs((s r) `div` d))
    | signum(s r) == -1 && signum(p r) == -1 = Rac (((p r) `div` d) * (-1)) (((s r) `div` d) * (-1))
    | otherwise = Rac ((p r) `div` d) ((s r) `div` d)
    where d = gcd (p r) (s r)

multRac2 :: Racional2 -> Racional2 -> Racional2
multRac2 x y = simplificaRac2(Rac ((p x) * (p y)) ((s x) * (s y)))

divRac2 :: Racional2 -> Racional2 -> Racional2
divRac2 x y = simplificaRac2(Rac ((p x) * (s y)) ((s x) * (p y)))

sumRac2 :: Racional2 -> Racional2 -> Racional2
sumRac2 x y
    | (s x) == (s y) = simplificaRac2(Rac ((p x) + (p y)) (s x))
    | otherwise = simplificaRac2(Rac (((p x) * (s y)) + ((p y) * (s x))) ((s x) * (s y)))

resRac2 :: Racional2 -> Racional2 -> Racional2
resRac2 x y
    | (s x) == (s y) = simplificaRac2(Rac ((p x) - (p y)) (s x))
    | otherwise = simplificaRac2(Rac (((p x) * (s y)) - ((p y) * (s x))) ((s x) * (s y)))

--Ej 3:

instance Num Racional where
    (*) = multRac
    (+) = sumRac
    (-) = resRac
    negate (n1,d1) = simplificaRac(-n1,d1)
    fromInteger n = (n, 1)
    signum (n1,d1) = if (n1 * d1) < 0 then -1 else 1
    abs (n1,d1) = if (n1*d1)<0 then simplificaRac((-n1), d1) else simplificaRac(n1, d1)

--Naturales:

data Nat = Cero | Succ Nat
    deriving Eq

--Ej 1:

instance Num Nat where
    n + Cero = n
    n + Succ m = Succ (n + m)
    n * Cero = Cero
    n * Succ Cero = n
    n * Succ m = n + (n * m)
    n - Cero = n
    Cero - Succ m = Cero
    Succ n - Succ m = n - m
    signum Cero = Cero
    signum (Succ n) = Succ Cero
    abs n = n
    fromInteger x 
        | x <= 0 = Cero
        | x == 1 = Succ Cero
        | otherwise = Succ (fromInteger (x - 1))

--Ej 2:

instance Ord Nat where
    Cero < Cero = False
    Cero < Succ n = True 
    Succ n < Cero = False
    Succ n < Succ m = n < m

--Ej 3:

divModN :: Nat -> Nat -> (Nat,Nat)
divModN x y
    | y == Succ Cero = (x,Cero)
    | x < y = (Cero,x)
    | otherwise = (Succ Cero + fst(divModN (x - y) y), snd(divModN (x - y) y))

--Ej 4:

instance Show Nat where
    show x = show (toIntegerN x)

toIntegerN :: Nat -> Integer
toIntegerN x
    | x == Cero = 0
    | x == Succ Cero = 1
    | otherwise = 1 + toIntegerN(x-1)
