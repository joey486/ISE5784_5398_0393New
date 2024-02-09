
package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for the {@link geometries.Sphere} class.
 * Verifies that the correct normal vector is obtained at a specified point on the surface of the sphere.
 * @author Shachar Levy and Yoel Kable
 */
public class SphereTest {

    /**
     * Test method for {@link geometries.Geometry#getNormal(primitives.Point)}.
     * Verifies that the correct normal vector is obtained at a specified point on the surface of the sphere.
     */
    @Test
    void testGetNormal() {
        Point p = new Point(1, 0, 0);
        Sphere s = new Sphere(1,new Point(0, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Verify that the calculated normal vector matches the expected result (test if the get normal returns the equal vector)
        assertEquals(new Vector(1, 0, 0), s.getNormal(p), "TC01: getNormal does not work correctly");

        // TC02: checking the normal vector itself
        assertEquals(1, s.getNormal(p).lengthSquared(), 0.001, "ERROR: normal to triangle isn't normalized.");
    }

    private final Point p001 = new Point(0, 0, 1);
    private final Point p100 = new Point(1, 0, 0);
    private final Vector v001 = new Vector(0, 0, 1);
    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1,p100);
        final GeoPoint gp1 = new GeoPoint(sphere, new Point(0.0651530771650466, 0.355051025721682, 0));
        final GeoPoint gp2 =new GeoPoint(sphere, new Point(1.53484692283495, 0.844948974278318, 0));
        final var exp = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Point p01 = new Point(-1, 0, 0);
        final Point p0500 = new Point(0.5,0,0);
        final Point p200 = new Point(2,0,0);
        final Vector v100 = new Vector(1,0,0);
        final Vector v100op = new Vector(-1,0,0);
        final Vector v010 = new Vector(0,1,0);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findGeoIntersections(new Ray(p01, v110)), "TC01: Ray's line is outside the sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result1 = sphere.findGeoIntersections(new Ray(p01, v310))
                .stream().sorted(Comparator.comparingDouble(p -> p.point.distance(p01))).toList();
        assertEquals(2, result1.size(), "TC02: Wrong number of points");
        assertEquals(exp, result1, "TC02: Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        final var result2 = sphere.findGeoIntersections(new Ray(p0500, v110))
                .stream().sorted(Comparator.comparingDouble(p -> p.point.distance(p0500))).toList();
        assertEquals(1, result2.size(), "TC03: Wrong number of points");
        assertEquals(new GeoPoint(sphere,new Point(1.411437827766,0.911437827766,0)), result2.get(0), "TC03: Ray starts inside the sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findGeoIntersections(new Ray(p01, new Vector(-3,-1,0))), "TC04: Ray starts after the sphere");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 point)
        final var result3 = sphere.findGeoIntersections(new Ray(p200, new Vector(-1,-1,0)))
                .stream().sorted(Comparator.comparingDouble(p -> p.point.distance(p200))).toList();
        assertEquals(1, result3.size(), "TC11: Wrong number of points");
        assertEquals(new GeoPoint(sphere,new Point(1,-1,0)), result3.get(0), "TC11: Ray starts at sphere and goes inside");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findGeoIntersections(new Ray(new Point(2,0,0), v110)), "TC12: Ray starts at sphere and goes outside");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        final var result4 = sphere.findGeoIntersections(new Ray(p01, v100))
                .stream().sorted(Comparator.comparingDouble(p -> p.point.distance(p01))).toList();
        assertEquals(2, result4.size(), "TC13: Wrong number of points");
        assertEquals(List.of(new GeoPoint(sphere,new Point(0,0,0)),new GeoPoint(sphere,p200)), result4, "TC13: Ray starts before the sphere");

        // TC14: Ray starts at sphere and goes inside (1 point)
        final var result5 = sphere.findGeoIntersections(new Ray(p200,v100op))
                .stream().sorted(Comparator.comparingDouble(p -> p.point.distance(p200))).toList();
        assertEquals(1, result5.size(), "TC14: Wrong number of points");
        assertEquals(new GeoPoint(sphere,new Point(0,0,0)), result5.get(0), "TC14: Ray starts at sphere and goes inside");

        // TC15: Ray starts inside (1 point)
        final var result6 = sphere.findGeoIntersections(new Ray(p0500, v100))
                .stream().sorted(Comparator.comparingDouble(p -> p.point.distance(p0500))).toList();
        assertEquals(1, result6.size(), "TC15: Wrong number of points");
        assertEquals(new GeoPoint(sphere,p200), result6.get(0), "TC15: Ray starts inside");

        // TC16: Ray starts at the center (1 point)
        final var result7 = sphere.findGeoIntersections(new Ray(p100, v110))
                .stream().sorted(Comparator.comparingDouble(p -> p.point.distance(p100))).toList();
        assertEquals(1, result7.size(), "TC16: Wrong number of points");
        assertEquals(new GeoPoint(sphere,new Point(1.707106781187,0.707106781187,0)), result7.get(0), "TC16: Ray starts at the center");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findGeoIntersections(new Ray(p200, v100)), "TC17: Ray starts at sphere and goes outside");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findGeoIntersections(new Ray(p01, v100op)), "TC18: Ray starts after sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findGeoIntersections(new Ray(new Point(0,-1,0),v010)), "TC19: Ray's line is tangent to the sphere");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findGeoIntersections(new Ray(p200,v010)), "TC20: Ray's line is tangent to the sphere");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findGeoIntersections(new Ray(new Point(0,1,0), v010)), "TC21: Ray's line is tangent to the sphere");

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findGeoIntersections(new Ray(p01, v010)), "TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line");
    }

    @Test
    void testFindGeoIntersections(){
        Sphere sphere = new Sphere(1,p100);
        final GeoPoint gp1 = new GeoPoint(sphere, new Point(0.0651530771650466, 0.355051025721682, 0));
        final GeoPoint gp2 =new GeoPoint(sphere, new Point(1.53484692283495, 0.844948974278318, 0));
        final var exp = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Point p01 = new Point(-1, 0, 0);

        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result = sphere.findGeoIntersections(new Ray(p01, v310),0.5);
        assertNull(result);

    }
}