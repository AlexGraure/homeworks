module Interpreter
  (
    -- * Types
    Prog,
    Asgn,

    -- * Functions
    evalRaw,
    evalAdt,
  ) where

-------------------------------------------------------------------------------
--------------------------------- The Expr ADT  -------------------------------
-------------------------------------------------------------------------------
data Expr = Add Expr Expr
          | Sub Expr Expr
          | Mult Expr Expr
          | Equal Expr Expr
          | Smaller Expr Expr
          | Symbol String
          | Value Int deriving (Show, Read)

-- TODO Implement a parser for the Expr ADT.
--

-------------------------------------------------------------------------------
---------------------------------- The Prog ADT -------------------------------
-------------------------------------------------------------------------------
data Asgn = Asgn String Expr deriving (Show, Read)

data Prog = Eq Asgn
          | Seq Prog Prog
          | If Expr Prog Prog
          | For Asgn Expr Asgn Prog
          | Assert Expr
          | Return Expr deriving (Show, Read)


-- TODO Implement a parser for the Prog ADT.
--

-- TODO The *parse* function. It receives a String - the program in
-- a "raw" format and it could return *Just* a program as an instance of the
-- *Prog* data type if no parsing errors are encountered, or Nothing if parsing
-- failed.
--
-- This is composed with evalAdt to yield the evalRaw function.
parse :: String -> Maybe Prog
parse = undefined

-------------------------------------------------------------------------------
-------------------------------- The Interpreter ------------------------------
-------------------------------------------------------------------------------

-- TODO The *evalAdt* function.  It receives the program as an instance of the
-- *Prog* data type and returns an instance of *Either String Int*; that is,
-- the result of interpreting the program.
--
-- The result of a correct program is always an Int.  This is a simplification
-- we make in order to ease the implementation.  However, we wrap this Int with
-- a *Either String* type constructor to handle errors.  The *Either* type
-- constructor is defined as:
--
-- data Either a b = Left a | Right b
--
-- and it is generally used for error handling.  That means that a value of
-- *Left a* - Left String in our case - wraps an error while a value of *Right
-- b* - Right Int in our case - wraps a correct result (notice that Right is a
-- synonym for "correct" in English).
-- 
-- For further information on Either, see the references in the statement of
-- the assignment.
--
type Archive = [(String, Int)]

-- Functie ce intoarce un numar din arhiva -- 
getVal :: String -> Archive -> Either String Int
getVal symbol [] = (Left "Uninitialized variable")
getVal symbol ((x, value) : arch) = if(symbol == x)
                                        then Right value
                                        else getVal symbol arch

okay = 1
wrong = 0

-- Functie ce evalueaza o expresie --
evalExpr :: Expr -> Archive -> Either String Int

evalExpr (Value val) _ = Right val
evalExpr (Symbol s) a = (getVal s a)
evalExpr (Smaller x y ) a = case (evalExpr x a) of
                              Left error1 -> Left error1
                              Right good1 -> case (evalExpr y a) of
                                                Left error2 -> Left error2
                                                Right good2 -> if(good1 < good2)
                                                                  then Right okay
                                                                  else Right wrong
evalExpr (Equal x y) a = case (evalExpr x a) of
                              Left error1 -> Left error1
                              Right good1 -> case (evalExpr y a) of
                                                Left error2 -> Left error2
                                                Right good2 -> if(good1 == good2)
                                                                  then Right okay
                                                                  else Right wrong
evalExpr (Mult x y) a = case (evalExpr x a) of
                              Left error1 -> Left error1
                              Right good1 -> case (evalExpr y a) of
                                                Left error2 -> Left error2
                                                Right good2 -> Right (good1 * good2)

evalExpr (Sub x y) a = case (evalExpr x a) of
                              Left error1 -> Left error1
                              Right good1 -> case (evalExpr y a) of
                                                Left error2 -> Left error2
                                                Right good2 -> Right (good1 - good2)

evalExpr (Add x y) a = case (evalExpr x a) of
                              Left error1 -> Left error1
                              Right good1 -> case (evalExpr y a) of
                                                Left error2 -> Left error2
                                                Right good2 -> Right (good1 + good2)
                                                
-- Functie ce adauga in arhiva un tuplu (simbol, numar) --
addTuplu :: String -> Int -> Archive -> Archive
addTuplu symbol value [] = (symbol, value) : []
addTuplu symbol value ((s, v) : arch) = if (symbol == s)
                                            then ((symbol, value) : arch)
                                            else ((s, v) : (addTuplu symbol value arch))
                                            
-- Functie ce evalueaza un asgn --
evalAsgn :: Asgn -> Archive -> (Either String (Maybe Int), Archive)
evalAsgn (Asgn symbol expr) a = case (evalExpr expr a) of
                                  Left uninitialized -> (Left uninitialized, a)
                                  Right value -> (Right Nothing, (addTuplu symbol value a))

-- Functie ce realizeaza bucla din for --
loop :: Expr -> Asgn -> Prog -> Archive -> (Either String (Maybe Int), Archive)
loop cond asgn prog arch = case (evalExpr cond arch) of
                          (Left err) -> (Left err, arch)
                          (Right res) -> if(res == okay)
                                            then case (evalAsgn asgn arch) of
                                                    (Left err2, a2) -> (Left err2, a2)
                                                    (Right Nothing, a3) -> case (evalProg prog a3) of
                                                                             (Left err3, a4) -> (Left err3, a4)
                                                                             (Right Nothing, a5) -> (loop cond asgn prog a5)
                                                                             (Right (Just res2), a6) -> (Right (Just res2), a6)

                                            else (Right Nothing, arch)

-- Functie ce evalueaza un program -- 
evalProg :: Prog -> Archive -> (Either String (Maybe Int), Archive)
evalProg (Return expr) arch = case (evalExpr expr arch) of
                                  (Left err) -> (Left err, arch)
                                  (Right res) -> (Right (Just res), arch)
                                  
evalProg (Assert expr) arch = case (evalExpr expr arch) of
                                  Left error -> (Left error, arch)
                                  Right res -> if(res == okay)
                                                  then (Right Nothing, arch)
                                                  else (Left "Assert failed", arch)

evalProg (If cond prog1 prog2) arch = case (evalExpr cond arch) of
                                          (Left error) -> (Left error, arch)
                                          (Right res) -> if(res == okay)
                                                          then case (evalProg prog1 arch) of
                                                                  (Left err1, a1) -> (Left err1, a1)
                                                                  (Right (Just right1), a2) -> (Right (Just right1), a2)
                                                                  (Right Nothing, a3) -> (Right Nothing, a3)
                                                          else case (evalProg prog2 arch) of
                                                                  (Left err2, a4) -> (Left err2, a4)
                                                                  (Right (Just right2), a5) -> (Right (Just right2), a5)
                                                                  (Right Nothing, a6) -> (Right Nothing, a6)
                                                                  
evalProg (Seq prog1 prog2) arch = case (evalProg prog1 arch) of
                                      (Left err1, a11) -> (Left err1, a11)
                                      (Right good1, a12) -> case (good1) of
                                                              (Nothing) -> case (evalProg prog2 a12) of
                                                                          (Left err2, a21) -> (Left err2, a21)
                                                                          (Right (Just good2), a22) -> (Right (Just good2), a22)
                                                                          (Right Nothing, a23) -> (Right Nothing, a23)
                                                              (Just good) -> (Right (Just good), a12)
                                                              
evalProg (Eq asgn) arch = case (evalAsgn asgn arch) of
                        (Left err, a2) -> (Left err, a2)
                        (Right Nothing, a3) -> (Right Nothing, a3)

evalProg (For asgn1 expr asgn2 prog) a = case (evalAsgn asgn1 a) of
                                          (Left err1, a2) -> (Left err1, a2)
                                          (Right Nothing, a3) -> case (loop expr asgn2 prog a3) of
                                                                    (Left err2, a4) -> (Left err2,a4)
                                                                    (Right (Just res), a5) -> (Right (Just res), a5)
                                                                    (Right Nothing, a6) -> (Right Nothing, a6)

evalAdt :: Prog -> Either String Int
evalAdt prog = case (evalProg prog []) of
                  (Left error, erArch) -> (Left error)
                  (Right Nothing, nothArch) -> (Left "Missing return")
                  (Right (Just result), goodArch) -> (Right result)

-- The *evalRaw* function is already implemented, but it relies on the *parse*
-- function which you have to implement.
--
-- Of couse, you can change this definition.  Only its name and type are
-- important.
evalRaw :: String -> Either String Int
evalRaw rawProg =
    case parse rawProg of
        Just prog -> evalAdt prog
        Nothing   -> Left "Syntax error"
