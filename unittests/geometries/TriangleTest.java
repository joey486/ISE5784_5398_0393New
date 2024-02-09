package geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * JUnit test class for the {@link Triangle} class.
 */
class TriangleTest {

    /**
     * Tests the {@link Triangle#getNormal(Point)} method.
     */
    @Test
    void getNormal() {
        // Creating a triangle with vertices at (1,0,0), (0,1,0), and (0,0,1)
        Triangle triangle = new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));

        // Getting the normal at the given point
        Vector normal = triangle.getNormal(new Point(2, -1, 0));

        // Checking if the normal is in the correct direction
        assertThrows(IllegalArgumentException.class,
                () -> (new Vector(1, 1, 1)).crossProduct(normal),
                "ERROR: triangle doesn't return normal in the correct direction.");

        // Checking if the normal is normalized
        assertEquals(1, normal.lengthSquared(), 0.001, "ERROR: normal to triangle isn't normalized.");
    }

    @Test
    /**
     * Tests the {@link Triangle#findIntersections(Ray)} method.
     */

    void testFindIntersections() {
        Triangle triangle = new Triangle(new Point(0, 0, 2), new Point(0, 2, 0), new Point (2,0,0));
        List<GeoPoint> intersections = triangle.findGeoIntersections(new Ray(new Point(0,0,0),new Vector(0.5,0.5,1)));

        // ============ Equivalence Partitions Tests ==============
        //TC01: The point of intersection with the "contained" plane is inside the polygon/triangle (1 point)
        assertEquals(1,intersections.size(),"Wrong number of points");
        assertEquals(List.of(new GeoPoint(triangle,new Point(0.5,0.5,1))),intersections," The point of intersection with the contained plane is inside the polygon/triangle");

        //TC02: against side (0 points)
        assertNull(triangle.findGeoIntersections(new Ray(new Point(0,0,0),new Vector(-2,2,2))),"TC02: against edge");
        //TC03: against vertex (0 points)
        assertNull(triangle.findGeoIntersections(new Ray(new Point(0,0,0),new Vector(-1,4,-1))),"against vertx");
        // =============== Boundary Values Tests ==================
        //TC11: on a side (0 points)
        assertNull(triangle.findGeoIntersections(new Ray(new Point(0,0,0),new Vector(1,1,0))),"on a side");
        //TC12: on a vertex (0 points)
        assertNull(triangle.findGeoIntersections(new Ray(new Point(0,0,0),new Vector(2,0,0))),"on a vertex");
        //TC13: on the continuance of a side (0 points)
        assertNull(triangle.findGeoIntersections(new Ray(new Point(0,0,0),new Vector(-2,4,0))),"on the continuance of a side");
    }

    @Test
    void testGeoFindIntersections(){
        Triangle triangle = new Triangle(new Point(0, 0, 2), new Point(0, 2, 0), new Point (2,0,0));
        List<GeoPoint> intersections = triangle.findGeoIntersections(new Ray(new Point(0,0,0),new Vector(0.5,0.5,1)));


    }
}