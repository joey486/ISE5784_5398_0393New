package renderer;

import geometries.FlatGeometry;
import geometries.Intersectable.GeoPoint;
import geometries.Triangle;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static java.lang.Math.abs;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The SimpleRayTracer class extends RayTracerBase and represents a basic ray tracer in a 3D graphics rendering system.
 * It provides a simple implementation for tracing rays through a scene and determining the color of the corresponding pixels.
 */
public class SimpleRayTracer extends RayTracerBase {
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    /**
     * Constructs a SimpleRayTracer with the specified scene.
     *
     * @param scene The scene to be rendered by the simple ray tracer.
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Traces a ray through the scene and calculates the color of the corresponding pixel.
     *
     * @param ray The ray to be traced through the scene.
     * @return The Color representing the calculated color of the pixel.
     */
    @Override
    public Color traceRay(Ray ray) {
        // Find intersections of the ray with scene geometries
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);

        // If there are no intersections, return the background color of the scene
        if (intersections == null || intersections.isEmpty()) {
            return scene.background;
        }

        // Find the closest intersection point
        // Calculate and return the color for the pixel at the closest intersection point
        return calculateColor(ray.findClosestGeoPoint(intersections), ray);
    }

    /**
     * Calculates the color of a pixel at the specified point using ambient light.
     *
     * @param geoPoint The point in the scene for which to calculate the color.
     * @param ray      The ray corresponding to the pixel.
     * @return The Color representing the calculated color of the pixel.
     */
    private Color calculateColor(GeoPoint geoPoint, Ray ray) {
        return scene.ambientLight.getIntensity().add(calcLocalEffects(geoPoint, ray));
    }

    /**
     * Calculates the color of a pixel at the specified point considering local lighting effects.
     *
     * @param gp  The point in the scene for which to calculate the color.
     * @param ray The ray corresponding to the pixel.
     * @return The Color representing the calculated color of the pixel.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        Color color = gp.geometry.getEmission();
        Material material = gp.geometry.getMaterial();

        double nv = alignZero(n.dotProduct(v));
        if (isZero(nv)) return color;

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (alignZero(nl * nv) > 0 && unshaded(gp, l, n,lightSource,nl)) { // sign(nl) == sign(nv) {}
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(
                        iL.scale(calcDiffusive(material, nl).add(calcSpecular(material, n, l, nl, v))));
            }
        }
        return color;
    }

    /**
     * Calculates the diffuse reflection component of the material.
     *
     * @param material The material of the geometry.
     * @param nl       The dot product of the normal and the light vector.
     * @return The diffuse reflection component.
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(abs(nl));
    }

    /**
     * Calculates the specular reflection component of the material.
     *
     * @param material The material of the geometry.
     * @param n        The normal vector at the point.
     * @param l        The light vector.
     * @param nl       The dot product of the normal and the light vector.
     * @param v        The view vector.
     * @return The specular reflection component.
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(2 * nl));
        return material.kS.scale(Math.pow(Math.max(0, -v.dotProduct(r)), material.nShininess));
    }

    /**
     * Checks if the point is unshaded by the given light source.
     *
     * @param gp The point in the scene.
     * @param l  The light vector.
     * @param n  The normal vector.
     * @return True if the point is unshaded, false otherwise.
     */
    private boolean unshaded(GeoPoint gp , Vector l, Vector n, LightSource lightSource, double nl) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale((nl) < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(epsVector);
        Ray ray = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray,lightSource.getDistance(point));

        if (intersections == null) return true;

        return false;
    }

    private Color calcColor(GeoPoint gp, Ray ray){
        return calcColor(gp,ray,MAX_CALC_COLOR_LEVEL,MIN_CALC_COLOR_K).add(scene.ambientLight.getIntensity());
    }
    private Color calcColor(GeoPoint gp, Ray ray,int level,double k){
        return null;
    }
}
