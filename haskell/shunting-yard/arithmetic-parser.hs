-- TODO use GADT to allow typeclass for number token (to allow float etc.)
import Data.List (find)
import Text.ParserCombinators.ReadP

-- Data
data Associativity = ALeft | ARight
  deriving (Show)

type Precedence = Integer

data Token = Number Int | Operator {
    sym :: Char,
    preced :: Precedence,
    assoc :: Associativity
  }
  deriving (Show)

operators :: [Token]
operators = [(Operator '+' 0 ALeft), (Operator '-' 0 ALeft)]

-- Util
-- TODO missing pattern match
fetchop :: Char -> Token
fetchop char = let (Just op) = find ((== char) . sym) operators in op

-- Parsing
operator :: ReadP Token
operator = do 
          skipSpaces
          symb <- satisfy $ (`elem` (map sym operators))
          return $ fetchop symb

number :: ReadP Token
number = do
       skipSpaces
       nr <- munch1 (\char -> char >= '0' && char <= '9')
       return $ Number $ read nr

parse :: ReadP [Token]
parse = do
       parsed <- many (number +++ operator)
       return parsed

tokenize :: String -> Either String [Token]
tokenize inp = case res of 
                (parsed, "") -> Right parsed
                (_, tk:_) -> Left $ "Invalid token: " ++ [tk] ++ " at position: " ++ show pos
          where res = last $ readP_to_S parse inp
                pos = (length inp) - (length $ snd res) + 1

-- Transform and Eval
shunyard :: [Token] -> [Token]
shunyard tks = foldl f ([], []) tks
          where f = (\acc cur -> case cur of
                    (Number nr) -> (nr:(fst acc), snd acc)
                    op -> applyOperator op acc)

-- TODO delete
temp inp = case tokenize inp of
            (Right parsed) -> shunyard parsed

--
--eval :: [Token] -> Int
--eval = 1
--
--run :: String -> Either String Integer
--run inp = case tokenize inp of
--           (Right parsed) -> Right $ eval $ shunyard parsed
--           err -> err


