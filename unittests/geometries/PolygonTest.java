package geometries;
import geometries.Plane;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Testing Polygons
 * @author Dan
 */
public class PolygonTest {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;


    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        Assertions.assertDoesNotThrow(() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1)),
                "Failed constructing a correct polygon");

        // TC02: Wrong vertices order
        Assertions.assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        Assertions.assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0.5, 0.25, 0.5)), "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        Assertions.assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0, 0.5, 0.5)),
                "Constructed a polygon with vertix on a side");

        // TC11: Last point = first point
        Assertions.assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                "Constructed a polygon with vertice on a side");

        // TC12: Co-located points
        Assertions.assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                "Constructed a polygon with vertice on a side");

    }

    /** Test method for {@link geometries.Polygon#getNormal(primitives.Point)}. */
    @Test
    public void testGetNormal() {

        Point[] pts =
                { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
        Polygon pol = new Polygon(pts);
        Vector result = pol.getNormal(new Point(0, 0, 1));



        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Assertions.assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");

        // TC02: test the length of the normal vector
        assertEquals(1, result.length(), DELTA, "Polygon's normal is not a unit vector");

        // TC03: ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])), DELTA,
                    "Polygon's normal is not orthogonal to one of the edges");
    }

    private final Point p001 = new Point(0, 0, 1);
    private final Point p100 = new Point(1, 0, 0);
    private final Point p010 = new Point(0, 1, 0);
    private final Vector v011 = new Vector(0, 1, 1);
    private final Ray r100 = new Ray(new Point(1,0,0), new Vector(1,0,0));

//    @Test
//    public void findIntersections(){
//        Polygon polygon = new Polygon(p001,p100,p010);
//        Vector v310 = new Vector(3,1,0);
//        final Point p0500 = new Point(0.5,0,0);
//        final Vector v111 = new Vector(1, 1, 1);
//        final Point p200 = new Point(2,0,0);
//        final Point p01 = new Point(-1, 0, 0);
//        final Vector v100 = new Vector(1,0,0);
//        final Vector v010 = new Vector(0,1,0);
//        final Point gp1 = new Point(0.6666666666666666,0.16666666666666666,0.16666666666666666);
//        final var exp = List.of(gp1);
//
//        // ============ Equivalence Partitions Tests ==============
//        // TC01: Ray's line is outside the polygon (0 points)
//        assertNull(polygon.findIntersections(new Ray(p200, v011)), "TC01: Ray's line is outside the polygon");
//
//        // TC02: Ray starts before and crosses the polygon (2 points)
//        final var result1 = polygon.findGeoIntersections(new Ray(p0500, v111))
//                .stream().sorted(Comparator.comparingDouble(p -> p.point.distance(p001))).toList();
//        assertEquals(1, result1.size(), "TC02: Wrong number of points");
//        assertEquals(exp, result1, "TC02: Ray crosses polygon");
//
//        assertNull(polygon.findIntersections(new Ray(p001, v011)), "TC03: Ray's line is on the polygon");
//
//        // TC04: Ray starts after the polygon (0 points)
//        assertNull(polygon.findIntersections(new Ray(p200, new Vector(3,1,0))), "TC04: Ray starts after the polygon");
//
//        // =============== Boundary Values Tests ==================
//
//        // **** Group: Ray's line crosses the polygon (but not the center)
//
//        // TC11: Ray starts at tube and goes inside (1 points)
//        final var result3 = polygon.findIntersections(new Ray(p200, new Vector(-1,-1,0)))
//                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p200))).toList();
//        assertEquals(1, result3.size(), "TC11: Wrong number of points");
//
//        // **** Group: Ray's line is tangent to the polygon (all tests 0 points)
//        // TC19: Ray starts before the polygon
//        assertNull(polygon.findIntersections(new Ray(new Point(-10,-1,-1),new Vector(-1,1,0))), "TC19: Ray's line is tangent to the polygon");
//
//        // TC20: Ray starts above the polygon
//        assertNull(polygon.findIntersections(new Ray(new Point(1,1,1),new Vector(-1,1,0))), "TC20: Ray's line is tangent to the polygon");
//
//        // TC21: Ray starts after the polygon
//        assertNull(polygon.findIntersections(new Ray(new Point(-1,-1,2), new Vector(-1,1,0))), "TC21: Ray's line is tangent to the polygon");
//
//        // **** Group: Special cases (?)
//    }
}