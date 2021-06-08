-- TODO use GADT to allow typeclass for number token (to allow float etc.)
import Data.List (find)
import Text.ParserCombinators.ReadP

-- Data
data Associativity
  = ALeft
  | ARight
  deriving (Show)

type Precedence = Integer

data Token
  = Number Integer
  | Operator
      { sym :: Char
      , preced :: Precedence
      , assoc :: Associativity
      , fn :: Integer -> Integer -> Integer
      }

operators :: [Token]
operators = [(Operator '+' 0 ALeft (+)), (Operator '-' 0 ALeft (-))]

-- Util
-- TODO missing pattern match
fetchop :: Char -> Token
fetchop char =
  let (Just op) = find ((== char) . sym) operators
   in op

-- Parsing
operator :: ReadP Token
operator = do
  skipSpaces
  symb <- satisfy $ (`elem` (map sym operators))
          -- TODO ugly fn call?
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
tokenize inp =
  case res of
    (parsed, "") -> Right parsed
    (_, tk:_) ->
      Left $ "Invalid token: " ++ [tk] ++ " at position: " ++ show pos
  where
    res = last $ readP_to_S parse inp
    pos = (length inp) - (length $ snd res) + 1

-- Transform and Eval
shunyard :: [Token] -> [Token]
shunyard tks =
  let (res, ops) = foldl transform ([], []) tks
   in reverse $ reverse ops ++ res
  where
    transform = (\acc cur -> applyToken cur acc)

applyToken :: Token -> ([Token], [Token]) -> ([Token], [Token])
-- Actual shunting yard algo runs here
applyToken all@(Number nr) (out, ops) = (all : out, ops)
applyToken op (out, ops) =
  if null pop
    then (out, op : ops)
    else (pop ++ out, op : (drop (length pop) ops))
  where
    pop = takeWhile ((>= preced op) . preced) ops

-- TODO error handling
eval :: [Token] -> Integer
eval tks =
  let [res] = foldl eval' [] tks
   in res
  where
    eval' =
      (\acc cur ->
         case cur of
           (Number nr) -> nr : acc
                        -- TODO beautify, dont use hardcoded index, try 
           op -> ((fn op) (acc !! 1) (acc !! 0)) : (drop 2 acc))

-- TODO delete
temp inp =
  case tokenize inp of
    (Right parsed) -> Right $ shunyard parsed
    (Left msg) -> Left msg --run :: String -> Either String Integer
--run inp = case tokenize inp of
--           (Right parsed) -> Right $ eval $ shunyard parsed
--           err -> err
