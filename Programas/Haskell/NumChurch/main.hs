{-# OPTIONS_GHC -fno-warn-tabs #-}

import ADPR

gramNC = G [(R 'N' "(F.(X.C))"), (R 'F' "/f"), (R 'X' "/x"), (R 'C' "(fC)"), (R 'C' "x")] :: Gramatica