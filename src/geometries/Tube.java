package geometries;

import primitives.Point;
import primitives.Util;
import primitives.Vector;
import primitives.Ray;

import java.util.List;

import static primitives.Util.isZero;

/**
 * The Tube class represents a tube in three-dimensional space. A tube is defined by a radius and an axis ray.
 */
public class Tube extends RadialGeometry {
    private final Ray ray;
    /**
     * tube constructor based on radius and direction.
     * @param  radius                   radius of the cylinder
     * @param  ray                      a ray that describes the orientation of the cylinder
     */
    public Tube(float radius, Ray ray) {
        super(radius);
        this.ray = ray;
    }

    @Override
    public Vector getNormal(Point point) {
        //the "shade" of the action point-head on the main axis
        double t = ray.getDirection().dotProduct(point.subtract(ray.getHead()));
        //border case
        if(isZero(t)){
            return point.subtract(ray.getHead());
        }
        //multiply the direction vector of the axis by t, and you have the point on the axis infront of the point
        return point.subtract(ray.getHead().add(ray.getDirection().scale(t))).normalize() ;
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        //check if ray starts at center
        if(ray == this.ray)
            return List.of(new GeoPoint(this,ray.getPoint(radius)));

        // Calculate the vector from the ray's origin to the tube's center
        Vector u = this.ray.getHead().subtract(ray.getHead());

        double tm = Util.alignZero(ray.getDirection().dotProduct(u));
        double d = Util.alignZero(Math.sqrt(u.lengthSquared() - tm*tm));
        double th = Util.alignZero(Math.sqrt(radius * radius - d * d));


        //if d>=r there are no intersections
        if (d >= radius||( tm < 0 && u.lengthSquared() >= radius*radius)) {
            return null;
        }

        //calculate the t scalars
        double t1 = tm + th;
        double t2 = tm - th;

        if (tm - th > 0)//two intersections
            return List.of(new GeoPoint(this,ray.getPoint(tm-th)),new GeoPoint(this,ray.getPoint(tm+th)));
        //one intersection
        return List.of(new GeoPoint(this,ray.getPoint(tm+th)));
        //scale the vector
    }
}