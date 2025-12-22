{-# OPTIONS_GHC -fno-warn-tabs #-}

module Expresiones where
data Op = Sum | Res | Mul | Div

instance Show Op where
	show Sum = "+"
	show Res = "-"
	show Mul = "*"
	show Div = "/"

--ops es la lista de las operaciones

ops :: [Op]
ops = [Sum,Res,Mul,Div]

valida :: Op -> Int -> Int -> Bool
valida Sum _ _ = True
valida Res x y = x > y
valida Mul _ _ = True
valida Div x y = y /= 0 && x `mod` y == 0

aplica :: Op -> Int -> Int -> Int
aplica Sum x y = x + y
aplica Res x y = x - y
aplica Mul x y = x * y
aplica Div x y = x `div` y

data Expr = Num Int | Apl Op Expr Expr

instance Show Expr where
	show (Num n) = show n
	show (Apl o i d) = parentesis i ++ show o ++ parentesis d
		where
			parentesis (Num n) = show n
			parentesis e = "(" ++ show e ++ ")"

numeros :: Expr -> [Int]
numeros (Num n) = [n]
numeros (Apl _ l r) = numeros l ++ numeros r

valor :: Expr -> [Int]
valor (Num n) = [n | n > 0]
valor (Apl o i d) = [aplica o x y | x <- valor i
				, y <- valor d
				, valida o x y]


