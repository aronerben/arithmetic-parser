# arithmetic-parser
Parser/interpreter/evaluator for arithmetic expressions, written in Java.
Written to learn. Not suitable for situation that requires fast and reliable parsing/evaluation.

## Class responsibilites by step
### 1. Lexer
The lexer (or tokenizer) splits up the given arithmetic expression into valid tokens. A valid token is a symbol or number, 
that can be used as a building block for a valid arithmetic expression. These can be operators (+, -, * etc) or numbers (2, 4.1 etc).
The tokens are stored in a list. The only check done in this step is to look for disallowed symbols.

### 2. Parser
The parser transforms the order of tokens into the postfix (or Reverse Polish Notation) notation. This is done using
Dijkstra's shunting-yard algorithm. The only check done in this step is to look for unbalanced parantheses.
Since the lexer already implicitly creates a parse-tree, the shunting-yard algorithm generates the abstract syntax tree for the evaluator.

### Other classes (used in all steps)
The other classes, like the Token class, build the base. Tokens enforce the correct type congruency so no invalid tokens can be created by the lexer. An enum of operators and an operator util class helps the parser to identify operator precedence and associativity.
