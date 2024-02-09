package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The Plane class represents an infinite plane in three-dimensional space.
 * It is defined by a point on the plane and a normal vector to the plane.
 * The class inherits from the Polygon class, which is a flat, two-dimensional polygon.
 */
public class Plane extends Geometry implements FlatGeometry{

    /**
     * The reference point on the plane.
     */
    Point point;

    /**
     * The normal vector to the plane.
     */
    Vector normalVector;

    /**
     * Constructs a plane using three points on the plane.
     *
     * @param p1 The first point on the plane.
     * @param p2 The second point on the plane.
     * @param p3 The third point on the plane.
     */
    public Plane(Point p1, Point p2, Point p3) {

        Vector p = p2.subtract(p1);
        Vector pp = p3.subtract(p1);
        Vector v = p.crossProduct(pp);

        normalVector = v.normalize();
        this.point = p1;
    }

    /**
     * Constructs a plane using a reference point and a normal vector.
     *
     * @param point  A reference point on the plane.
     * @param vector The normal vector to the plane.
     */
    public Plane(Point point, Vector vector) {
        this.point = point;
        normalVector = vector.normalize();
    }

    /**
     * Gets the normal vector to the plane at a specified point.
     *
     * @param point The point on the plane.
     * @return The normal vector to the plane at the specified point.
     */
    @Override
    public Vector getNormal(Point point) {
        return normalVector;
    }

    public Vector getNormal() {
        return normalVector;
    }



    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {

        //if ray is parallel to plane
        if(isZero(normalVector.dotProduct(ray.getDirection()))){
            return null;
        }

        //if ray head is on point (and therefore on the plane)
        if(ray.getHead().equals(point)){
            return null;
        }

        //if ray head is on plane
        if(isZero(normalVector.dotProduct(point.subtract(ray.getHead())))){
            return null;
        }

        Vector QMinusP0=point.subtract(ray.getHead());
        double nQMinusP0=alignZero(normalVector.dotProduct(QMinusP0));
        double nv=alignZero(normalVector.dotProduct(ray.getDirection()));
        double t=alignZero(nQMinusP0/nv);
        if (t > 0 && alignZero(t-maxDistance)<=0)
            return List.of(new GeoPoint(this,ray.getPoint(t)));
        else
            return null;
    }

}