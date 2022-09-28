package ru.stqa.pft.sandbox;

import java.math.BigDecimal;

public class MathExt {
    public static double square(double x){
        return x * x;
    }

    public static BigDecimal toBigDecimal(double x, int precision) {
        return new BigDecimal(x).setScale(precision, BigDecimal.ROUND_HALF_UP);
    }
}
