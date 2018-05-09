# arithmetic-parser
Parser &amp; interpreter for arithmetic expressions, written in Java.

## Class responsibilites by step
### Lexer
The lexer (or tokenizer) splits up the given arithmetic expression into valid tokens. A valid token is a symbol or number, 
that can be used as a building block for a valid arithmetic expression. These can be operators (+, -, * etc) or numbers (2, 4.1 etc).
The tokens are stored in a list. The only check done in this step is to look for disallowed symbols.

### Parser
The parser transforms the order of tokens into the postfix (or Reverse Polish Notation) notation. This is done using
Dijkstra's shunting-yard algorithm. The only check done in this step is to look for unbalanced parantheses.
