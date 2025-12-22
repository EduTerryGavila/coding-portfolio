--Para tener siempre activados patrones n+k
{-# LANGUAGE NPlusKPatterns #-}

--Para evitar que aparezcan los warnings debidos a tabuladores
{-# OPTIONS_GHC -fno-warn-tabs #-}

--takewhile, group, replicate

--Ej 1:

type Libro = String
type Persona = String
type BD = [(Persona,Libro)]

--Ej 2:

--BD = [("Juan", "Don Quijote"), ("Ana", "Harry Potter"), ("Pedro", "El Principito"), ("Ana", "Cien Años de Soledad"), ("Maria", "1984"), ("Luis", "Dune"), ("Juan", "Rayuela"), ("Sofia", "El Hobbit"), ("Carlos", "Ficciones"), ("Maria", "Crimen y Castigo"), ("Ana", "La Odisea"), ("Elena", "Matar a un ruiseñor"), ("Carlos", "El Aleph"), ("Lucia", "Dracula")]


libros :: BD -> Persona -> [Libro]
libros b p = concat [[(snd l)] |  l <- b, (fst l) == p]

lectores :: BD -> Libro -> [Persona]
lectores b l = concat [[(fst p)] |  p <- b, (snd p) == l]

prestado :: BD -> Libro -> Bool
prestado b l = null[[snd lp] | lp <- b, (snd lp) == l] == False

numPrestados :: BD -> Persona -> Int
numPrestados b p = length[(snd pp) | pp <- b, (fst pp) == p]

--Ej 3:

realizarPrestamo :: BD -> Persona -> Libro -> BD
realizarPrestamo b p l = b ++ [(p, l)]

devolverPrestamo :: BD -> Persona -> Libro -> BD
devolverPrestamo b p l = concat[[r] | r <- b, ((fst r) /= p) || ((snd r) /= l)]

--Ampliacion

type NumEjem = [(Libro, Int)]

--Añadir a la BD y eliminar de NumEjem
realizarPrestamo2 :: BD -> NumEjem -> Persona -> Libro -> (BD,NumEjem)
realizarPrestamo2 b n p l = (realizarPrestamo b p l, extraeEjemplar n l)

devolverPrestamo2 :: BD -> NumEjem -> Persona -> Libro -> (BD,NumEjem)
devolverPrestamo2 b n p l = (devolverPrestamo b p l, devuelveEjemplar n l)

catalogadoLibro :: NumEjem -> Libro -> Bool
catalogadoLibro n l = length[(fst li) | li <- n, (fst li) == l] > 0

disponibleLibro :: NumEjem -> Libro -> Bool
disponibleLibro n l = length[(fst li) | li <- n, (fst li) == l && (snd li) > 0] > 0

nuevoEjemplar :: NumEjem -> Libro -> NumEjem
nuevoEjemplar n l
    | catalogadoLibro n l == False = n ++ [(l, 1)]
    | otherwise = [((fst li), (if (fst li == l) then (snd li + 1) else (snd li))) | li <- n]

devuelveEjemplar :: NumEjem -> Libro -> NumEjem
devuelveEjemplar n l = [((fst li), (if (fst li == l) then (snd li + 1) else (snd li))) | li <- n]

extraeEjemplar :: NumEjem -> Libro -> NumEjem
extraeEjemplar n l
    | disponibleLibro n l == False || catalogadoLibro n l == False = n
    | otherwise = [((fst li), (if (fst li == l) then (snd li - 1) else (snd li))) | li <- n]