package renderer;

import geometries.FlatGeometry;
import geometries.Intersectable.GeoPoint;
import geometries.Triangle;
import lighting.DirectionalLight;
import lighting.LightSource;
import lighting.PointLight;
import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The SimpleRayTracer class extends RayTracerBase and represents a basic ray tracer in a 3D graphics rendering system.
 * It provides a simple implementation for tracing rays through a scene and determining the color of the corresponding pixels.
 */
public class SimpleRayTracer extends RayTracerBase {
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;
    boolean useTheSoftShadows = true;

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
    public Color traceRay(Ray ray, boolean useSoftShadows) {
        useTheSoftShadows = useSoftShadows;
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background
                : calcColor(closestPoint, ray);
    }

    /**
     * Calculates the color of a pixel at the specified point using ambient light.
     *
     * @param geoPoint The point in the scene for which to calculate the color.
     * @param ray      The ray corresponding to the pixel.
     * @return The Color representing the calculated color of the pixel.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    private Color calcColor(GeoPoint intersection, Ray ray,
                            int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray, k);
        return 1 == level ? color
                : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * Calculates the color of a pixel at the specified point considering local lighting effects.
     *
     * @param gp  The point in the scene for which to calculate the color.
     * @param ray The ray corresponding to the pixel.
     * @return The Color representing the calculated color of the pixel.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        Color color = gp.geometry.getEmission();
        Material material = gp.geometry.getMaterial();

        double nv = alignZero(n.dotProduct(v));
        if (isZero(nv)) return color;

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (alignZero(nl * nv) > 0) {
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K))){
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(
                            iL.scale(calcDiffusive(material, nl)
                                    .add(calcSpecular(material, n, l, nl, v))));
                }
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
    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source

        if(light instanceof DirectionalLight || !useTheSoftShadows) {
            Ray ray = new Ray(gp.point, lightDirection, n);
            List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray, light.getDistance(gp.point));

            if (intersections == null) return Double3.ONE;

            Double3 ktr = Double3.ONE;

            for (GeoPoint point : intersections) {
                ktr = ktr.product(point.geometry.getMaterial().kT);
                if (ktr.equals(Double3.ZERO)) break;
            }

            return ktr;
        }

        else{
            //create the vectors
            Vector vUp;
            Vector vTo;
            if(lightDirection.equals(new Vector(1,0,0)) || lightDirection.equals(new Vector(-1,0,0)))
                vUp = lightDirection.crossProduct(new Vector(0,0,1));
            else vUp = lightDirection.crossProduct(new Vector(1,0,0));
            vTo = lightDirection.crossProduct(vUp);

            Double3 ktr = Double3.ZERO;

            PointLight PosLight = (PointLight) light;

            PosLight.blackboard.setGrid(PosLight.getPosition(),vUp,vTo);

            for(Point i : PosLight.blackboard.grid){

                Ray ray = new Ray(gp.point,i.subtract(gp.point),n);

                List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray, light.getDistance(gp.point));

                if (intersections == null) ktr = ktr.add(Double3.ONE);

                else {

                    Double3 ktr_temp = Double3.ONE;

                    for (GeoPoint point : intersections) {
                        ktr_temp = ktr_temp.product(point.geometry.getMaterial().kT);
                        if (ktr_temp.equals(Double3.ZERO)) break;
                    }
                    ktr = ktr.add(ktr_temp);
                }
            }
            return ktr.scale((double) 1/(PosLight.blackboard.grid.size()));
        }
    }

    /**
     * Calculates the global effects (reflection and refraction) for a pixel at the specified point.
     *
     * @param gp    The point in the scene for which to calculate the global effects.
     * @param ray   The ray corresponding to the pixel.
     * @param level The recursion level.
     * @param k     The coefficient for the effect.
     * @return The Color representing the calculated global effects.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        Vector normal = gp.geometry.getNormal(gp.point);
        return calcGlobalEffect(constructRefractedRay(gp, ray.getDirection(), normal), level,
                material.kT, k)
                .add(calcGlobalEffect(constructReflectedRay(gp, ray.getDirection(), normal), level,
                        material.kR, k));
    }
    /**
     * Calculates either reflection or refraction effect for a pixel at the specified point.
     *
     * @param ray   The ray corresponding to the pixel.
     * @param level The recursion level.
     * @param k     The coefficient for the effect.
     * @param kx    The coefficient for the previous effect.
     * @return The Color representing the calculated effect.
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx))                  //
                .scale(kx);
    }

    /**
     * Finds the closest intersection point of a ray with the scene's geometries.
     *
     * @param ray The ray to trace.
     * @return The closest GeoPoint of intersection, or null if no intersection is found.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> list = scene.geometries.findGeoIntersections(ray);
        if (list == null) return null;
        return ray.findClosestGeoPoint(list);
    }

    /**
     * Constructs a reflected ray from the given parameters.
     *
     * @param gp       The intersection point.
     * @param direction The direction of the incident ray.
     * @param normal    The normal vector at the intersection point.
     * @return The reflected ray.
     */
    private Ray constructReflectedRay(GeoPoint gp, Vector direction, Vector normal) {
        double nv = alignZero(normal.dotProduct(direction));
        if (isZero(nv)) return null;
        return new Ray(gp.point, direction.subtract(normal.scale(2 * nv)), normal);
    }

    /**
     * Constructs a refracted ray from the given parameters.
     *
     * @param gp       The intersection point.
     * @param direction The direction of the incident ray.
     * @param normal    The normal vector at the intersection point.
     * @return The refracted ray.
     */
    private Ray constructRefractedRay(GeoPoint gp, Vector direction, Vector normal) {
        return new Ray(gp.point, direction, normal);
    }
}