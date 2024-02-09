package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * Polygon class represents a two-dimensional polygon in 3D Cartesian coordinate system.
 * The polygon is defined by a list of ordered vertices forming its edges.
 *
 * @author Dan
 */
public class Polygon extends Geometry implements FlatGeometry{

   /** List of polygon's vertices */
   protected final List<Point> vertices;

   /** Associated plane in which the polygon lays */
   protected final Plane plane;

   /** The size of the polygon - the number of vertices in the polygon */
   private final int size;

   /**
    * Constructs a polygon based on a list of vertices. The list must be ordered by edge path,
    * and the polygon must be convex.
    *
    * @param vertices list of vertices in order of edge path
    * @throws IllegalArgumentException in case of illegal combinations of vertices:
    *         <ul>
    *             <li>Less than 3 vertices</li>
    *             <li>Consequent vertices are in the same point</li>
    *             <li>The vertices are not in the same plane</li>
    *             <li>The order of vertices is not according to edge path</li>
    *             <li>Three consequent vertices lay in the same line (180° angle between two consequent edges)</li>
    *             <li>The polygon is concave (not convex)</li>
    *         </ul>
    */
   public Polygon(Point... vertices) {
      if (vertices.length < 3)
         throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
      this.vertices = List.of(vertices);
      size = vertices.length;

      // Generate the plane according to the first three vertices and associate the
      // polygon with this plane.
      // The plane holds the invariant normal (orthogonal unit) vector to the polygon
      plane = new Plane(vertices[0], vertices[1], vertices[2]);
      if (size == 3) return; // no need for more tests for a Triangle

      Vector n = plane.getNormal();
      // Subtracting any subsequent points will throw an IllegalArgumentException
      // because of Zero Vector if they are in the same point
      Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
      Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

      // Cross Product of any subsequent edges will throw an IllegalArgumentException
      // because of Zero Vector if they connect three vertices that lay in the same
      // line.
      // Generate the direction of the polygon according to the angle between last and
      // first edge being less than 180 deg. It is held by the sign of its dot product
      // with the normal. If all the rest consequent edges will generate the same sign
      // - the polygon is convex ("kamur" in Hebrew).
      boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
      for (var i = 1; i < vertices.length; ++i) {
         // Test that the point is in the same plane as calculated originally
         if (!primitives.Util.isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
            throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
         // Test the consequent edges have
         edge1 = edge2;
         edge2 = vertices[i].subtract(vertices[i - 1]);
         if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
            throw new IllegalArgumentException("All vertices must be ordered, and the polygon must be convex");
      }
   }

   /**
    * Gets the normal vector of the polygon at a specified point.
    *
    * @param point the point on the polygon
    * @return the normal vector of the polygon
    */
   @Override
   public Vector getNormal(Point point) {
      return plane.getNormal();
   }

   @Override
   protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
      return null;
   }

   /**
    * Finds intersections between a ray and the polygon.
    *
    * @param ray the ray to intersect with the polygon
    * @return a list of intersection points, or null if there are no intersections
    */
}
//      List<Point> pts = plane.findIntersections(ray);
//      if(pts == null) return null;
//
//      List<Point> points = null;
//      List<Vector> vec = null;
//      List<Vector> normals = null;
//
//      for(int i = 0; i < vertices.size(); i++){
//         points.add(vertices.get(i));
//         vec.add(vertices.get(i).subtract(ray.getHead()));
//      }
//
//      for (int i = 0; i < vertices.size(); i++){
//         normals.add(vec.get(i).crossProduct(vec.get(i+1)).normalize());
//      }
//
//      double[] dotProducts = new double[vertices.size()];
//
//      for (int i = 0; i < vertices.size(); i++){
//         dotProducts[i] = alignZero(ray.getDirection().dotProduct(normals.get(i)));
//      }
//
//      int NumOfPositives = 0;
//      int NumOfNegatives = 0;
//
//      for (int i = 0; i < vertices.size(); i++){
//         if(dotProducts[i] > 0) NumOfPositives++;
//         if(dotProducts[i] < 0) NumOfNegatives++;
//      }
//      if (NumOfPositives == vertices.size()) return pts;
//      if (NumOfNegatives == vertices.size()) return pts;
//
//      return null;
//   }


