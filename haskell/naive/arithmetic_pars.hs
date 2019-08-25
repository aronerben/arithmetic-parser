--TODO: define appropriate data types for failure/success
--TODO: add support for more operations
main = undefined

data Expr
  = Val Integer
  | Add Expr
        Expr
  | Mul Expr
        Expr
  | Sub Expr
        Expr
  deriving (Show)

data Token
  = Token Expr
  | Op String
  deriving (Show)

ops :: [(Char, Integer)]
ops = [('+', 0), ('-', 0), ('*', 1)]

eval :: String -> Integer
eval str = evalh $ unwrap $ transform $ tokenize $ normalize str

evalh :: Expr -> Integer
evalh (Val val) = val
evalh (Add expr1 expr2) = evalh expr1 + evalh expr2
evalh (Mul expr1 expr2) = evalh expr1 * evalh expr2
evalh (Sub expr1 expr2) = evalh expr1 - evalh expr2

unwrap :: [Token] -> Expr
unwrap [Token expr] = expr

transform :: [Token] -> [Token]
transform tks =
  if (idx == -1)
    then tks
    else transform
           ((take (idx - 1) tks) ++
            [binaryTransform (tks !! (idx - 1)) (tks !! idx) (tks !! (idx + 1))] ++
            (drop (idx + 2) tks))
  where
    idx = highestPrecedenceIdx tks

binaryTransform :: Token -> Token -> Token -> Token
binaryTransform (Token left) (Op "*") (Token right) = Token (Mul left right)
binaryTransform (Token left) (Op "+") (Token right) = Token (Add left right)
binaryTransform (Token left) (Op "-") (Token right) = Token (Sub left right)

highestPrecedenceIdx :: [Token] -> Int
highestPrecedenceIdx tks =
  if (maximum xs == -1)
    then -1
    else head $ filter ((== maximum xs) . (xs !!)) [0 ..]
  where
    xs = precedenceMap tks

precedenceMap :: [Token] -> [Integer]
precedenceMap tks =
  map
    (\t ->
       case t of
         (Op [a]) -> getPrecedence a
         t -> -1)
    tks

getPrecedence :: Char -> Integer
getPrecedence c = snd $ head $ filter (\(op, prec) -> c == op) ops

tokenize :: String -> [Token]
tokenize str
  | length str == matchL = [Token (Val (read str))]
  | otherwise =
    (Token (Val (read val))) :
    (Op [str !! matchL]) : (tokenize (drop (matchL + 1) str))
  where
    val = takeWhile (\c -> null (filter (\(op, prec) -> c == op) ops)) str
    matchL = length val

normalize :: String -> String
normalize [] = ""
normalize (s:str)
  | s == ' ' = normalize str
  | otherwise = s : (normalize str)
