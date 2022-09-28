package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;
import java.math.BigDecimal;

public class PointTests {

    @Test
    public void testDistance_PositivePoints() {
        Point p1 = new Point(4.8, 5.7);
        Point p2 = new Point(5.5, 6.2);
        BigDecimal actualDistance = MathExt.toBigDecimal(p1.distance(p2), 3);
        BigDecimal expectedDistance = MathExt.toBigDecimal(0.8602325267042627, 3);
        Assert.assertEquals(actualDistance, expectedDistance, "Invalid distance.");
    }

    @Test
    public void testDistance_TheSameX() {
        Point p1 = new Point(4.8, 8.7);
        Point p2 = new Point(4.8, 6.2);
        BigDecimal actualDistance = MathExt.toBigDecimal(p1.distance(p2), 3);
        BigDecimal expectedDistance = MathExt.toBigDecimal(2.5, 3);
        Assert.assertEquals(actualDistance, expectedDistance, "Invalid distance.");
    }

    @Test
    public void testDistance_TheSameY() {
        Point p1 = new Point(100, 50);
        Point p2 = new Point(120, 50);
        BigDecimal actualDistance = MathExt.toBigDecimal(p1.distance(p2), 3);
        BigDecimal expectedDistance = MathExt.toBigDecimal(20.0, 3);
        Assert.assertEquals(actualDistance, expectedDistance, "Invalid distance.");
    }

    @Test
    public void testDistance_ZeroPoints() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 0);
        BigDecimal actualDistance = MathExt.toBigDecimal(p1.distance(p2), 6);
        BigDecimal expectedDistance = MathExt.toBigDecimal(0.0, 6);
        Assert.assertEquals(actualDistance, expectedDistance, "Invalid distance.");
    }

    @Test
    public void testDistance_NegativePoint() {
        Point p1 = new Point(-5, -10);
        Point p2 = new Point(5, 10);
        BigDecimal actualDistance = MathExt.toBigDecimal(p1.distance(p2), 3);
        BigDecimal expectedDistance = MathExt.toBigDecimal(22.3606797749979, 3);
        Assert.assertEquals(actualDistance, expectedDistance, "Invalid distance.");
    }
}
