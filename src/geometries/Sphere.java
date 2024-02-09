package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * The Sphere class represents a sphere in three-dimensional space. A sphere is defined by a center point and a radius.
 * Extends RadialGeometry, inheriting the radius property.
 */
public class Sphere extends RadialGeometry {
    /**
     * The center point of the sphere.
     */
    Point center;

    /**
     * The radius of the sphere.
     */
    double radius;

    /**
     * Constructs a radial geometry with a specified radius.
     *
     * @param radius The radius of the radial geometry.
     * @param center The center point of the sphere.
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.radius = radius;
        this.center = center;
    }

    /**
     * Computes and returns the normal vector to the sphere at a specified point.
     *
     * @param point The point on the sphere for which to calculate the normal vector.
     * @return The normalized vector representing the normal to the sphere at the given point.
     */
    @Override
    public Vector getNormal(Point point) {
        Vector vector = point.subtract(center);
        return vector.normalize();
    }

    /**
     * Finds the intersection points between a ray and the sphere.
     *
     * @param ray The ray to intersect with the sphere.
     * @return A list of intersection points, or null if there are no intersections.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        // Check if ray starts at the center
        if (center.equals(ray.getHead()))
            return List.of(new GeoPoint(this,ray.getPoint(radius)));

        // Calculate the vector from the ray's origin to the sphere's center
        Vector u = center.subtract(ray.getHead());

        double tm = alignZero(ray.getDirection().dotProduct(u));
        double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));
        double th = alignZero(Math.sqrt(radius * radius - d * d));

        // If d >= r, there are no intersections
        if (d >= radius || (tm < 0 && u.lengthSquared() >= radius * radius)) return null;

        // Calculate the t scalars
        double t1 = alignZero(tm + th);
        double t2 = alignZero(tm - th);

        Point p1 = ray.getPoint(t1);
        Point p2 = ray.getPoint(t2);

        boolean b1 = alignZero(t1 - maxDistance) <= 0;
        boolean b2 = alignZero(t2 - maxDistance) <= 0;

        // Two intersections
        if (t2 > 0 && t1 > 0 && b2 && b1)
            return List.of(new GeoPoint(this,p1),new GeoPoint(this, p2));

        // One intersection
        if(b2 && t2 > 0)
            return List.of(new GeoPoint(this,p2));

        // One intersection
        if(b1 && t1 > 0)
            return List.of(new GeoPoint(this,p1));

        return null;
    }
}
