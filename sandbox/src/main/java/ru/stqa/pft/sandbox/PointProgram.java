package ru.stqa.pft.sandbox;

public class PointProgram {
    public static void main(String[] args){
        Point p1 = new Point(5, 6);
        Point p2 = new Point(7, 8);

        // Task 2.3
        System.out.printf("The distance between point %s and point %s is %f.%n", p1, p2, distance(p1, p2));

        // Task 2.4
        System.out.printf("The distance between point %s and point %s is %f.%n", p1, p2, p1.distance(p2));
    }

    public static double distance(Point p1, Point p2){
        return Math.sqrt(MathExt.square(p2.x - p1.x) + MathExt.square(p2.y - p1.y));
    }
}
