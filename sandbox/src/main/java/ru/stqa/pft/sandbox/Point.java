package ru.stqa.pft.sandbox;

public class Point {
    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%f, %f)", x, y);
    }

    public double distance(Point p){
        double x1 = p.x - this.x;
        double x2 = p.y - this.y;
        double v = MathExt.square(x1) + MathExt.square(x2);
        double sqrt = Math.sqrt(v);
        return sqrt;
    }
}
