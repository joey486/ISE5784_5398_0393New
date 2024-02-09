package geometries;
import geometries.Plane;
import primitives.Point;
import primitives.Vector;
import org.junit.jupiter.api.Test;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    @Test
    void getNormal() {
        // Define points on the plane
        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(1, 0, 0);
        Point p3 = new Point(0, 1, 0);

        // Create a plane
        Plane plane = new Plane(p1, p2, p3);

        // Expected normal vector (in this case, it should be the z-axis)
        Vector expectedNormal = new Vector(0, 0, 1);

        // Get the actual normal vector
        Vector actualNormal = plane.getNormal(p1);

        // Assert equality
        assertEquals(expectedNormal, actualNormal);
    }

    @Test
    void testFindIntersections() {
        Plane plane = new Plane(new Point(0, 0, 0), new Vector(0, 0, 1));

        // ============ Equivalence Partitions Tests ==============

        //TC30: intersect with the plane and neither parallel nor orthogonal to it (1 point)
        Ray ray30 = new Ray(new Point(0,0,-2),new Vector(0,1,1));
        List<Point> intersections30 = plane.findIntersections(ray30);
        assertEquals(1,intersections30.size(),"Wrong number of points");
        assertEquals(intersections30,List.of(new Point(0,2,0)),"intersect with the plane and neither parallel nor orthogonal to it");

        //TC31: does not intersect with the plane and neither parallel nor orthogonal to it (0 points)
        Ray ray31 = new Ray(new Point(0,0,2),new Vector(0,1,1));
        assertNull(plane.findIntersections(ray31)," does not intersect with the plane and neither parallel nor orthogonal to it");

        // =============== Boundary Values Tests ==================

        // Group: orthogonal to the plane
        //TC01: above the plane (0 points)
        Ray ray = new Ray(new Point(1, 1, 1), new Vector(0, 0, 1)); // ray above plane

        // Act
        List<Point> intersections = plane.findIntersections(ray);

        // Assert
        assertNull(intersections,"ray is orthogonal to the plane and starts above it");
        // Arrange
        //TC02: starts in the plane (0 points)
        Ray ray2 = new Ray(new Point(1, 1, 0), new Vector(0, 0, 1)); // ray inside plane

        // Act
        List<Point> intersections2 = plane.findIntersections(ray2);

        // Assert
        assertNull(intersections2,"ray is orthogonal to the plane and starts in it");
        // Arrange
        //TC03: starts beneath the plane (1 point)
        Ray ray3 = new Ray(new Point(1, 1, -1), new Vector(0, 0, 1)); // plane starts before plane

        // Act
        List<Point> intersections3 = plane.findIntersections(ray3);

        // Assert
        assertEquals(1, intersections3.size(),"Wrong number of points");
        List<Point> actualIntersections = List.of(new Point(1,1,0));
        assertEquals(actualIntersections,intersections3,"orthogonal to the plane and starts beneath it");

        // Group: parallel to the plane
        //TC11: outside the plane
        Ray ray11 = new Ray(new Point(1,1,1),new Vector(0,1,0));
        assertNull(plane.findIntersections(ray11),"outside the plane and parallel");

        //TC12: inside the plane
        Ray ray12 = new Ray(new Point(0,0,0),new Vector(0,1,0));
        assertNull(plane.findIntersections(ray12),"inside the plane and parallel");

        // Special cases
        //TC20: starts in the plane and neither parallel nor orthogonal to it
        Ray ray20 = new Ray(new Point(1,1,0),new Vector(0,1,1));
        assertNull(plane.findIntersections(ray20),"starts in the plane and neither parallel nor orthogonal to it");

        //TC21: starts at the point of the plane and neither parallel nor orthogonal to it
        Ray ray21 = new Ray(new Point(0,0,0),new Vector(0,1,1));
        assertNull(plane.findIntersections(ray21),"starts at the point of the plane and neither parallel nor orthogonal to it");
    }

    @Test
    void testFindGeoIntersection(){
        Plane plane = new Plane(new Point(0, 0, 0), new Vector(0, 0, 1));
        Ray ray30 = new Ray(new Point(0,0,-2),new Vector(0,1,1));

        assertNull(plane.findGeoIntersectionsHelper(ray30,1));
    }
}