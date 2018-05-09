package util;

public class MathUtil {

    public static double factorial( int iNo ) {
        if (iNo < 0) {
            throw new IllegalArgumentException("iNo must be >= 0");
        }
        int factorial = 1;
        for(int i = 2; i <= iNo; i++) {
            factorial *= i;
        }
        return (double)factorial;
    }
}
