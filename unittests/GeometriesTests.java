import geometries.*;
import primitives.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GeometriesTests {
    private final Point p001 = new Point(0, 0, 1);
    private final Point p100 = new Point(1, 0, 0);
    private final Point p010 = new Point(0, 1, 0);
    private final Vector v001 = new Vector(0, 0, 1);
    private final Vector v010 = new Vector(0, 1, 0);

    @Test
    void testFindGeoIntersections(){
        final Geometries geometries=new Geometries();
        final Point p110=new Point(1,1,0);
        final Triangle triangle1=new Triangle(p100, p010, Point.ZERO);
        final Plane plane =new Plane(new Point(0, 0, 2), v001);
        //right above triangle1:
        final Triangle triangle2=new Triangle(new Point(1, 0, 1), new Point(0, 1, 1), p001);
        final Point p0250250 = new Point(0.25, 0.25, 0);
        final Point p025025_1 = new Point(0.25, 0.25, -1);

        // =============== Boundary Values Tests ==================
        //TC01-BVA: geometries is empty
        assertNull(geometries.findGeoIntersections(new Ray(p110, v001)), "TC01: geometries is empty");

        //TC02-BVA: no object is intersected
        geometries.add(triangle1);
        assertNull(geometries.findGeoIntersections(new Ray(p110, v001)), "TC02: no object is intersected");

        //TC03-BVA: only one object is intersected
        geometries.add(plane);
        assertEquals(1, geometries.findGeoIntersections(new Ray(p110, v001)).size(), "TC03: only one object is intersected");


        // ============ Equivalence Partitions Tests ==============
        //TC04-EP: a few objects are intersected (2 objects out of 3: triangle2, plane)
        geometries.add(triangle2);
        assertEquals(2, geometries.findGeoIntersections(new Ray(p0250250, v001)).size(), "TC04: a few objects are intersected");

        // =============== Boundary Values Tests ==================
        //TC05-BVA: all objects are intersected
        assertEquals(3, geometries.findGeoIntersections(new Ray(p025025_1, v001)).size(), "TC05: all objects are intersected");
    }
}