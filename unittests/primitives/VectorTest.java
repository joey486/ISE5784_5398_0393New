package primitives;

import primitives.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * JUnit test class for the {@link Vector} class.
 */
class VectorTest {

    /**
     * Tests the {@link Vector#add(Vector)} method.
     */
    @Test
    void testConstructor(){
        //TC01:
        assertThrows(IllegalArgumentException.class,()->new Vector(0,0,0), "ERROR: Constructor does not working");
    }

    @Test
    void testConstructorDouble3(){
        //TC01: Constructor of vector that using Double3 does not work
        assertThrows(IllegalArgumentException.class,()->new Vector(new Double3(0,0,0)), "ERROR: Constructor does not work");

    }

    @Test
    void add() {
        // Creating vectors for testing
        Vector v1 = new Vector(1, 2, 3);
        Vector v1Opposite = new Vector(-1, -2, -3);
        Vector v2 = new Vector(-2, -4, -6);

        //TC01:=======<Adding two vectors should result in the opposite vector>======
        assertEquals(v1.add(v2), v1Opposite, "ERROR: Vector + Vector does not work correctly");

        // =============== Boundary Values Tests ==================
        //TC02: Adding a vector to its opposite should throw an exception
        assertThrows(IllegalArgumentException.class, () -> v1.add(v1Opposite),
                "ERROR: Vector + -itself does not throw an exception");

    }

    /**
     * Tests the {@link Vector#scale(double)} method.
     */
    //the function do scale between number and vector
    @Test
    void scale() {
        // Creating a vector for testing
        Vector v1 = new Vector(1, 2, 3);

        // =============== Boundary Values Tests ==================
        //TC01:=======<Scaling a vector by 0 should throw an exception>======
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0),
                "ERROR: Vector scaled by 0 does not throw an exception");

        // ============ Equivalence Partitions Tests ==============
        //TC02:=======<Scaling a vector by a positive scalar>======
        assertEquals(v1.scale(3), new Vector(3, 6, 9), "ERROR: Vector*scalar does not work correctly");

        //TC:03=======<Scaling a vector by a negative scalar>======
        assertEquals(v1.scale(-3), new Vector(-3, -6, -9), "ERROR: Vector*(negative scalar) does not work correctly");

        //TC:04=======<Scaling a vector by a scalar between 0-1>======
        assertEquals(v1.scale(0.5), new Vector(0.5, 1, 1.5), "ERROR: Vector*(scalar between 0-1) does not work correctly");
    }

    /**
     * Tests the {@link Vector#dotProduct(Vector)} method.
     */
    @Test
    void dotProduct() {
        // Creating vectors for testing
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        //TC01:=======<Dot product of two vectors>======
        assertEquals(v1.dotProduct(v2), -28, "ERROR: dotProduct() wrong value");

        // =============== Boundary Values Tests ==================
        //TC02:=======<Dot product of orthogonal vectors should be 0>======
        assertEquals(v1.dotProduct(v3), 0, "ERROR: dotProduct() for orthogonal vectors is not zero");

    }

    /**
     * Tests the {@link Vector#crossProduct(Vector)} method.
     */
    @Test
    void crossProduct() {
        // Creating vectors for testing
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        //TC01=======<Cross product result length>======
        Vector vr = v1.crossProduct(v3);
        assertEquals(vr.length(), v1.length() * v3.length(), 0.0001, "ERROR: crossProduct() wrong result length");

        // =============== Boundary Values Tests ==================
        //TC02=======<Cross product result is orthogonal to its operands>======
        assertEquals(vr.dotProduct(v1), 0, "ERROR: crossProduct() result is not orthogonal to its operands");
        assertEquals(vr.dotProduct(v3), 0, "ERROR: crossProduct() result is not orthogonal to its operands");
        //TC03=======<Cross product of parallel vectors should throw an exception>======
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2),
                "ERROR: crossProduct() for parallel vectors does not throw an exception");

    }

    /**
     * Tests the {@link Vector#lengthSquared()} method.
     */
    @Test
    void lengthSquared() {
        // Creating a vector for testing
        Vector v4 = new Vector(1, 2, 2);

        //TC01:=======<Squared length of a vector>======
        assertEquals(v4.lengthSquared(), 9, "ERROR: lengthSquared() wrong value");
    }

    /**
     * Tests the {@link Vector#length()} method.
     */
    @Test
    void length() {
        // Creating a vector for testing
        Vector v4 = new Vector(1, 2, 2);

        //TC01:=======<Length of a vector>======
        assertEquals(v4.length(), 3, "ERROR: length() wrong value");
    }

    /**
     * Tests the {@link Vector#normalize()} method.
     */
    @Test
    void normalize() {
        // Creating a vector for testing
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();

        //TC01:=======<Normalized vector is a unit vector>======
        assertEquals(u.length(), 1, "ERROR: the normalized vector is not a unit vector");

        //TC02=======<Normalized vector is parallel to the original one>======
        assertThrows(IllegalArgumentException.class, () -> v.crossProduct(u),
                "ERROR: the normalized vector is not parallel to the original one");
        //TC03=======<Normalized vector is opposite to the original one>======
        assertTrue(v.dotProduct(u) > 0, "ERROR: the normalized vector is opposite to the original one");
    }
}