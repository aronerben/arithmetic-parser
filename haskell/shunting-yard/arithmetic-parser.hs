-- TODO use GADT to allow typeclass for number token (to allow float etc.)
import Data.List (find)
import Text.ParserCombinators.ReadP

data Token = Number Int | Operator Char
  deriving (Show)

data Associativity = ALeft | ARight
  deriving (Show)

type Precedence = Integer

data Operator = Op {
    sym :: Char,
    preced :: Precedence,
    assoc :: Associativity
  }
  deriving (Show)

operators :: [Operator]
operators = [(Op '+' 0 ALeft), (Op '-' 0 ALeft)]

main = do
      term <- getLine
      return $ tokenize term

operator :: ReadP Token
operator = do 
          skipSpaces
          sym <- satisfy $ (`elem` (map sym operators))
          return $ Operator sym

number :: ReadP Token
number = do
       skipSpaces
       nr <- munch1 (\char -> char >= '0' && char <= '9')
       return $ Number $ read nr

parse :: ReadP [Token]
parse = do
       parsed <- many1 (number +++ operator)
       return parsed

tokenize :: String -> Either String [Token]
tokenize inp = case res of 
                (parsed, "") -> Right parsed
                (_, rest@(tk:_)) -> Left $ "Invalid token: " ++ [tk] ++ " at position: " ++ show pos
          where res = last $ readP_to_S parse inp
                pos = (length inp) - (length $ snd res) + 1


