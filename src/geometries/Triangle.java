package geometries;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import java.util.List;

import static primitives.Util.alignZero;


/**
 * The Triangle class represents a triangle in three-dimensional space. It is a specific type of polygon defined by three points.
 */


/**
 * The Triangle class represents a triangle in three-dimensional space. It is a specific type of polygon defined by three points.
 */
public class Triangle extends Polygon {

/**
 * Constructs a new Triangle with three specified points.
 *
 * @param p1 The first vertex of the triangle.
 * @param p2 The second vertex of the triangle.
 * @param p3 The third vertex of the triangle.
 */
public Triangle(Point p1, Point p2, Point p3) {
    super(p1, p2, p3);
}

@Override
public Vector getNormal(Point point){
    return super.getNormal(point);
}

@Override
protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
    var pts = plane.findGeoIntersections(ray,maxDistance);

    if(pts == null) return null;

    List<GeoPoint> geoPoints = List.of(new GeoPoint(this, pts.getFirst().point));

    for (int i=1;i < pts.size(); i++){
       geoPoints.add(new GeoPoint(this, pts.get(i).point));
    }

    GeoPoint p1 = new GeoPoint(this,vertices.get(0));
    GeoPoint p2 = new GeoPoint(this,vertices.get(1));
    GeoPoint p3 = new GeoPoint(this,vertices.get(2));
    Vector v1 = p1.point.subtract(ray.getHead());
    Vector v2 = p2.point.subtract(ray.getHead());
    Vector v3 = p3.point.subtract(ray.getHead());
    Vector n1 = v1.crossProduct(v2).normalize();
    Vector n2 = v2.crossProduct(v3).normalize();
    Vector n3 = v3.crossProduct(v1).normalize();
    double proDot1 = alignZero(ray.getDirection().dotProduct(n1));
    double proDot2 = alignZero(ray.getDirection().dotProduct(n2));
    double proDot3 = alignZero(ray.getDirection().dotProduct(n3));
    if (proDot3 < 0 && proDot1 < 0 && proDot2 < 0)
        return geoPoints;
    if (proDot3 > 0 && proDot1 > 0 && proDot2 > 0)
        return geoPoints;
    return null;
    }
}
