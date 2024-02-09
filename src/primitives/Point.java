package primitives;


/**
 * The Point class represents a point in three-dimensional space.
 * It encapsulates the coordinates of the point and provides various operations on points.
 */
public class Point {

    /**
     * The coordinates of the point stored in a Double3 object.
     */
    protected final Double3 xyz;

    /**
     * Constructs a point with specified X, Y, and Z coordinates.
     *
     * @param x The value of the X coordinate.
     * @param y The value of the Y coordinate.
     * @param z The value of the Z coordinate.
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * Constructs a point from a Double3 object.
     *
     * @param xyz The Double3 object representing the coordinates of the point.
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * A constant representing the zero point (0, 0, 0).
     */
    public final static Point ZERO = new Point(Double3.ZERO);

    /**
     * Checks if the point is equal to another object.
     *
     * @param o The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;
        return xyz.equals(point.xyz);
    }

    /**
     * Subtracts a point from the current point to create a vector.
     *
     * @param point The point to subtract.
     * @return A vector representing the subtraction of the two points.
     */
    public Vector subtract(Point point) {
        return new Vector(this.xyz.subtract(point.xyz));
    }

    /**
     * Adds a vector to the current point to create a new point.
     *
     * @param vector The vector to add.
     * @return A new point representing the addition of the current point and the vector.
     */
    public Point add(Vector vector) {
        return new Point(this.xyz.add(vector.xyz));
    }

    /**
     * Returns a string representation of the point.
     *
     * @return A string representation of the point.
     */
    @Override
    public String toString() {
        return "Point{" +
                "point=" + xyz +
                '}';
    }

    /**
     * Computes the squared distance between two points.
     *
     * @param point The other point to compute the squared distance to.
     * @return The squared distance between the two points.
     */
    public double distanceSquared(Point point) {
        return ((point.xyz.d1-this.xyz.d1)*(point.xyz.d1-this.xyz.d1)+
                (point.xyz.d2-this.xyz.d2)*(point.xyz.d2-this.xyz.d2)+
                (point.xyz.d3-this.xyz.d3)*(point.xyz.d3-this.xyz.d3));
    }

    /**
     * Computes the distance between two points.
     *
     * @param point The other point to compute the distance to.
     * @return The distance between the two points.
     */
    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }
}
