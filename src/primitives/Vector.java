package primitives;

/**
 * The Vector class represents a 3D vector in space.
 * It inherits from the Point class and contains operations and properties related to vector operations in 3D space.
 */
public class Vector extends Point {

    /**
     * Constructs a vector with specified X, Y, and Z values.
     *
     * @param x The value of the X coordinate.
     * @param y The value of the Y coordinate.
     * @param z The value of the Z coordinate.
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        validateNonZeroVector();
    }

    /**
     * Constructs a vector from a Double3 object.
     *
     * @param vector The Double3 object representing the vector's coordinates.
     */
    public Vector(Double3 vector) {
        super(vector);
        validateNonZeroVector();
    }

    /**
     * Validates that the vector is not the zero vector (0, 0, 0).
     *
     * @throws IllegalArgumentException if the vector is the zero vector.
     */
    public void validateNonZeroVector() {
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Zero vector is not allowed");
        }
    }

    /**
     * Adds the given vector to the current vector.
     *
     * @param vector The vector to add.
     * @return A new vector representing the sum of the current vector and the given vector.
     */
    public Vector add(Vector vector) {
        return new Vector(this.xyz.add(vector.xyz));
    }

    /**
     * Scales the vector by a scalar value.
     *
     * @param scalar The scalar value to multiply the vector by.
     * @return A new vector representing the scaled vector.
     */
    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    /**
     * Computes the dot product of two vectors.
     *
     * @param vector The vector to compute the dot product with.
     * @return The result of the dot product operation.
     */
    public double dotProduct(Vector vector) {
        return (xyz.d1 * vector.xyz.d1) + (xyz.d2 * vector.xyz.d2) + (xyz.d3 * vector.xyz.d3);
    }

    // Additional methods...

    /**
     * Computes the cross product of two vectors.
     *
     * @param vector The vector to compute the cross product with.
     * @return A new vector representing the cross product of the current vector and the given vector.
     */
    public Vector crossProduct(Vector vector) {
        double x = xyz.d2 * vector.xyz.d3 - xyz.d3 * vector.xyz.d2;
        double y = xyz.d3 * vector.xyz.d1 - xyz.d1 * vector.xyz.d3;
        double z = xyz.d1 * vector.xyz.d2 - xyz.d2 * vector.xyz.d1;
        return new Vector(x, y, z);
    }

    /**
     * Computes the squared length of the vector.
     *
     * @return The squared length of the vector.
     */
    public double lengthSquared() {
        return xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3;
    }

    /**
     * Computes the length of the vector.
     *
     * @return The length of the vector.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Normalizes the vector.
     *
     * @return A new vector representing the normalized vector.
     */
    public Vector normalize() {
        double vectorLength = length();
        return new Vector(xyz.reduce(vectorLength));
    }

    /**
     * Check if the vector is equal
     *
     * @return the boolean value of the check
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector vector)) return false;
        return xyz.equals(vector.xyz);
    }
}