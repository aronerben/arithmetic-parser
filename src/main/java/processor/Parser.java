package processor;

import base.Token;

import java.util.List;

public class Parser {
    public static void main(String[] args) {
        List<Token> tokens = Lexer.tokenize("(22.3+324322/2)");
        System.out.println(tokens);
    }
}
