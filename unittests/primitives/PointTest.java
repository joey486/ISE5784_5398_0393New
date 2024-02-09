package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The PointTest class contains JUnit tests for the Point class.
 */
class PointTest {

    /**
     * Tests the subtract method of the Point class.
     */
    @Test
    void Subtract() {
        Point point1 = new Point(3, 4, 5);
        Point point2 = new Point(1, 2, 3);
        Vector result = point1.subtract(point2);
        assertEquals(new Vector(2, 2, 2), result, "Wrong subtraction result for Point - Point");
    }

    /**
     * Tests the add method of the Point class.
     */
    @Test
    public void Add() {
        Point point1 = new Point(1, 2, 3);
        Vector vector = new Vector(4, 5, 6);
        Point result = point1.add(vector);
        assertEquals(new Point(5, 7, 9), result, "Wrong addition result for Point + Vector");
    }

    /**
     * Tests the distanceSquared method of the Point class.
     */
    @Test
    public void DistanceSquared() {
        Point point1 = new Point(1, 2, 3);
        Point point2 = new Point(4, 5, 6);
        double result = point1.distanceSquared(point2);
        assertEquals(27.0, result, 0.001, "Wrong squared distance result for Point1 to Point2");
    }

    /**
     * Tests the distance method of the Point class.
     */
    @Test
    public void Distance() {
        Point point1 = new Point(1, 2, 3);
        Point point2 = new Point(4, 5, 6);
        double result = point1.distance(point2);
        assertEquals(5.196, result, 0.001, "Wrong distance result for Point1 to Point2");
    }
}
