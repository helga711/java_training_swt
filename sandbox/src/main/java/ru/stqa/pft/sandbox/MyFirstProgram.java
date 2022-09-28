package ru.stqa.pft.sandbox;

import java.math.BigDecimal;

public class MyFirstProgram {
	public static void main(String[] args){
		hello("world");
		hello("user");

        Square s = new Square(5);
        System.out.printf("The area of a square with side %f is %f.%n", s.l, s.area());

        Rectangle r = new Rectangle(4, 6);
        System.out.printf("The area of a square with sides %f and %f is %f.%n", r.a, r.b, r.area());
	}

    public static void hello(String somebody){
        System.out.printf("Hello, %s!%n", somebody);
    }
}