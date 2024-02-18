package primitives;

import java.util.List;

import geometries.Intersectable.GeoPoint;

import static primitives.Util.isZero;

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

    private static final double DELTA = 0.1;

    /**
     * Constructs a new Ray with a specified head point, direction, and normal vector.
     * Adjusts the head point to avoid floating point precision issues.
     *
     * @param head     the head point of the ray.
     * @param direction the direction vector of the ray.
     * @param normal   the normal vector.
     */
    public Ray(Point head, Vector direction, Vector normal) {
        if (isZero(direction.dotProduct(normal))) this.point = head;
        else {
            if (direction.dotProduct(normal) > 0) this.point = head.add(normal.scale(DELTA));
            else this.point = head.add(normal.scale(-DELTA));
        }
        this.vector = direction.normalize();
    }

    /**
     * Retrieves the starting point of the ray.
     *
     * @return the starting point of the ray.
     */
    public Point getHead() {
        return this.point;
    }

    /**
     * Retrieves the direction vector of the ray.
     *
     * @return the direction vector of the ray.
     */
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

    /**
     * Finds the closest point from a list of points to the head of the ray.
     *
     * @param pointList the list of points to check.
     * @return the closest point to the head of the ray, or {@code null} if the list is null.
     */
    public Point findClosestPoint(List<Point> pointList) {
        return pointList == null ? null
                : findClosestGeoPoint(pointList.stream()
                .map(p -> new GeoPoint(null, p)).toList()
        ).point;
    }

    /**
     * Finds the closest GeoPoint from a list of GeoPoints to the head of the ray.
     *
     * @param geoPointList the list of GeoPoints to check.
     * @return the closest GeoPoint to the head of the ray, or {@code null} if the list is null.
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPointList) {

        if (geoPointList == null) return null;

        GeoPoint closestPoint = geoPointList.getFirst();

        for (int i = 0; i < geoPointList.size(); i++) {
            if (Math.abs(getHead().distance(geoPointList.get(i).point)) < Math.abs(getHead().distance(closestPoint.point)))
                closestPoint = geoPointList.get(i);
        }
        return closestPoint;
    }
}
