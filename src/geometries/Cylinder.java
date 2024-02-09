package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * The Cylinder class represents a cylinder in three-dimensional space. A cylinder is defined by its height.
 */
public class Cylinder extends Geometry{
    /**
     * The height of the cylinder.
     */
    double height;

    @Override
    public Vector getNormal(Point point) {
        return null;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        return null;
    }
}
