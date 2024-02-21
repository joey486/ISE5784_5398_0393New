package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * The Cylinder class represents a cylinder in three-dimensional space. A cylinder is defined by its height.
 */
public class Cylinder extends Tube{
    /**
     * The height of the cylinder.
     */
    double height;

    /**
     * tube constructor based on radius and direction.
     *
     * @param radius radius of the cylinder
     * @param ray    a ray that describes the orientation of the cylinder
     */
    public Cylinder(float radius, Ray ray) {
        super(radius, ray);
    }
}
