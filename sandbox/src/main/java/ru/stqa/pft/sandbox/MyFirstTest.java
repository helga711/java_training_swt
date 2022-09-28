package ru.stqa.pft.sandbox;

public class MyFirstTest {
	public static void main(String[] args){
		hello("world");
		hello("user");

        double l = 5;
        System.out.printf("The area of a square with side %f is %f.%n", l, area(l));

        double a = 4;
        double b = 6;
        System.out.printf("The area of a square with sides %f and %f is %f.%n", a, b, area(a, b));
	}

    public static void hello(String somebody){
        System.out.printf("Hello, %s!%n", somebody);
    }

    public static double area(double l){
        return l * l;
    }

    public static double area(double a, double b){
        return a * b;
    }
}