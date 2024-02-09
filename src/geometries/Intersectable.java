package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * The {@code Intersectable} interface represents a geometric object that can be intersected by a ray.
 * Implementing classes should provide a method to find the intersections between the object and a given ray.
 */
public abstract class Intersectable {


    /**
     * Finds the intersections between the current geometric object and the specified ray.
     *
     * @param ray The ray to be intersected with the object.
     * @return A list of intersection points between the object and the ray.
     *         An empty list is returned if there are no intersections.
     */

    public final List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * A helper class representing a geometric point on an intersectable object.
     */
    public static class GeoPoint {
        /** The intersected geometry. */
        public Geometry geometry;
        /** The point of intersection. */
        public Point point;

        public GeoPoint(Geometry geometry, Point point){
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint geoPoint)) return false;
            return this.geometry.equals(geoPoint.geometry) && this.point.equals(geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }
    protected abstract List<GeoPoint>
    findGeoIntersectionsHelper(Ray ray, double maxDistance);
}
