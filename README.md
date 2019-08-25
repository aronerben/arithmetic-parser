# arithmetic-parser
Parser/interpreter/evaluator for arithmetic expressions, written in once in Java and twice in Haskell (2019 update).
Written to learn. Not suitable for situations that require fast and reliable parsing/evaluation.

## Haskell
TODO: Write description

## Java
Some expressions that can be (correctly) evaluated (output is always a double):
- "2-1"
  - result: 1
- "2^3-1"
  - result: 7
- "4!%2"
  - result: 0
- "1.5(18/(10-1))"
  - result: 3

### Class responsibilities by steps
#### 1. Lexer
The lexer (or tokenizer) splits up the given arithmetic expression into valid tokens. A valid token is a symbol or number, 
that can be used as a building block for a valid arithmetic expression. These can be operators (+, -, * etc) or numbers (2, 4.1 etc).
The tokens are stored in a list. The only check done in this step is looking for disallowed symbols.

#### 2. Parser
The parser transforms the order of tokens into the postfix (or Reverse Polish Notation) notation. This is done using
Dijkstra's shunting-yard algorithm. The only check done in this step is looking for unbalanced parantheses.
Since the lexer already implicitly creates a parse-tree, the shunting-yard algorithm generates the abstract syntax tree for the evaluator.

#### 3. Evaluator
The evaluator takes the expression in AST form and runs it through a stack-based evaluation algorithm. This last step implicitly checks
the validity of the expression. Each binary operator (+, -, * etc) needs a mapping to two numbers/sub-expressions, each unary operator (only ! for now) needs a mapping to one number/sub-expression. If this condition isn't fulfilled, the evaluator fails and exits.

#### Other classes (used in all steps)
The other classes, like the Token class, build the base. Tokens enforce the correct type congruency so no invalid tokens can be created by the lexer. An enum of operators and an operator util class helps the parser to identify operator precedence and associativity. Each operator in the enum is mapped to a function (e.g. ADD operator is mapped to (a,b) -> a+b). A math util class implements the factorial function and a way to round double numbers to a given accuracy.

### More info
- The modulo operator (%) works with float numbers.
- The expression "5--2" is valid. Tokens generated: 5, - and -2. Equivalent to: 5+2
- The expression "3(4(5))" is valid. This is parsed as implicit multiplication and is equivalent to: 3\*4\*5
- The expression "4:(2)" is valid. Equivalent to 4/2
- : and / can be used interchangably.
- While the factorial operator has a precedence, it is never used to parse the order of operations.
- The factorial operator can only be applied to whole numbers.
