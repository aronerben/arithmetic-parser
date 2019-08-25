package util;

import exception.ExceptionCollection;

import java.math.RoundingMode;
import java.text.DecimalFormat;

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

    public static double round(Double number, int accuracy) {
        StringBuilder format = new StringBuilder("#.");
        for(int i = 0; i < accuracy; i++) {
            format.append('#');
        }
        DecimalFormat df = new DecimalFormat(format.toString());
        df.setRoundingMode(RoundingMode.HALF_UP);
        return Double.valueOf(df.format(number));
    }

    private static boolean hasDecimalExpansion(Double number) {
        return !(number == Math.floor(number)) || Double.isInfinite(number);
    }
}
