package base;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Symbols {
    Set<Character> GROUPING = new HashSet<>(Arrays.asList('(', ')'));
    //TODO(functions, but characters?)
    Set<Character> FUNCTIONS = new HashSet<>(Arrays.asList());
    Set<Character> UNARY_OPERATORS = new HashSet<>(Arrays.asList('!'));
    Set<Character> OPERATORS = new HashSet<>(Arrays.asList('+', '-', '*', ':', '/', '^', '%'));
    Set<Character> PARTS =
            Stream.concat(Stream.concat(Stream.concat(GROUPING.stream(), FUNCTIONS.stream()), UNARY_OPERATORS.stream()), OPERATORS.stream())
                    .collect(Collectors.toSet());
    Set<Character> PARTS_NO_UNARY =
            Stream.concat(Stream.concat(GROUPING.stream(), FUNCTIONS.stream()), OPERATORS.stream())
                    .collect(Collectors.toSet());

}
