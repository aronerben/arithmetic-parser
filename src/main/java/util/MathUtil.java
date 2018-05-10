package util;

import exception.ExceptionCollection;

public class MathUtil {

    public static double factorial(Double number) {
        if(hasDecimalExpansion(number)) {
            throw new ExceptionCollection.EvaluatorException("Illegal factorial argument. Not an integer: " +
                    number);
        }
        int n = number.intValue();
        if (n < 0) {
            throw new ExceptionCollection.EvaluatorException("Illegal factorial argument. Smaller than 0: " + n);
        }
        int factorial = 1;
        for(int i = 2; i <= n; i++) {
            factorial *= i;
        }
        return (double)factorial;
    }

    private static boolean hasDecimalExpansion(Double number) {
        return !(number == Math.floor(number)) || Double.isInfinite(number);
    }
}
