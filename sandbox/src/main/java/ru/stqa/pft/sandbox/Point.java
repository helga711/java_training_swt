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
        return Math.sqrt(MatExt.square(p.x - this.x) + MatExt.square(p.y - this.y));
    }
}
