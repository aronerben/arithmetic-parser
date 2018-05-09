package util;

public class MathUtil {

    public static double factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Illegal argument: Smaller than 0.");
        }
        int factorial = 1;
        for(int i = 2; i <= n; i++) {
            factorial *= i;
        }
        return (double)factorial;
    }
}
