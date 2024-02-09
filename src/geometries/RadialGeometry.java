package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * The RadialGeometry class is an abstract class representing geometric shapes in three-dimensional space
 * that have a radial property, such as radius. It implements the Geometry interface.
 */
public abstract class RadialGeometry extends Geometry {
    /**
     * The radial property, typically representing the radius of the geometric shape.
     */
    double radius;

    /**
     * Constructs a new RadialGeometry with the specified radial property.
     *
     * @param radius The radial property, often representing the radius of the geometric shape.
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

}
