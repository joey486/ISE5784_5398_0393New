package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RayTests {
    @Test
    public void findClosestPoint(){
        final String ERROR  = "ERROR! uncorrected point";
        Point p111 = new Point(1,1,1);
        Point p101 = new Point(1,0,1);
        Point p203 = new Point(2,0,3);
        Point p555 = new Point(5,5,5);
        var pointList1 = List.of(p203, p101,p555);
        List<Point> pointList2 = null;
        var pointList3 = List.of(p101,p203,p555);
        var pointList4 = List.of(p203,p555,p101);
        Ray ray = new Ray( p111, new Vector(1,2,0));

        // ============ Equivalence Partitions Tests ==============
        //TC01: middle point is the closest
        assertEquals(p101, ray.findClosestPoint(pointList1),ERROR);

        // =============== Boundary Values Tests ==================
        //TC02: empty list
        assertNull(ray.findClosestPoint(pointList2),"ERROR!");

        //TC03: first point is the closest
        assertEquals(p101,ray.findClosestPoint(pointList3),ERROR);

        //TC04: last point is the closest
        assertEquals(p101,ray.findClosestPoint(pointList4),ERROR);

    }
}
