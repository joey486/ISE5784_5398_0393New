package primitives;

import java.util.List;
import geometries.Intersectable.GeoPoint;


/**
 * Represents a ray in three-dimensional space, defined by a starting point and a direction vector.
 */
public class Ray {
    /**
     * The starting point of the ray.
     */
    final Point point;

    /**
     * The normalized direction vector of the ray.
     */
    final Vector vector;

    /**
     * Constructs a new Ray with the specified starting point and direction vector.
     * The direction vector is normalized during construction.
     *
     * @param point  the starting point of the ray.
     * @param vector the direction vector of the ray.
     */
    public Ray(Point point, Vector vector) {
        this.point = point;
        this.vector = vector.normalize();
    }

    public Point getHead() {
        return this.point;
    }

    public Vector getDirection() {
        return this.vector;
    }

    /**
     * Checks if this ray is equal to another object.
     *
     * @param o the object to compare with this ray.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;
        return this.point.equals(ray.point) && this.vector.equals(ray.vector);
    }

    /**
     * Returns a string representation of this ray.
     *
     * @return a string representation of this ray.
     */
    @Override
    public String toString() {
        return "Ray{" +
                "point=" + point +
                ", vector=" + vector +
                '}';
    }

    /**
     * Calculates a point along the ray based on the parameter t.
     *
     * @param t The parameter along the ray.
     * @return A point on the ray at the specified parameter value.
     */
    public Point getPoint(double t) {
        // P(t) = O + t * D, where O is the origin, D is the direction, and t is the parameter
        double x = point.xyz.d1 + t * vector.xyz.d1;
        double y = point.xyz.d2 + t * vector.xyz.d2;
        double z = point.xyz.d3 + t * vector.xyz.d3;

        return new Point(x, y, z);
    }
    public Point findClosestPoint(List<Point> pointList){
        return pointList == null ? null
                : findClosestGeoPoint(pointList.stream()
                .map(p -> new GeoPoint(null, p)).toList()
        ).point;
    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPointList){

        if(geoPointList == null) return null;

        GeoPoint closestPoint = geoPointList.getFirst();

        for(int i=0; i < geoPointList.size(); i++){
            if(Math.abs(getHead().distance(geoPointList.get(i).point)) < Math.abs(getHead().distance(closestPoint.point)))
                closestPoint = geoPointList.get(i);
        }
        return closestPoint;
    }
}