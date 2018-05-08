package base;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public interface Symbols {
    Set<Character> OPERATORS = new HashSet<>(Arrays.asList('+', '-', '*', ':', '/', '^', '%', '(', ')'));
}
