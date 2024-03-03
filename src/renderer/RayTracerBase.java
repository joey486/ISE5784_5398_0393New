package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * The RayTracerBase class is an abstract base class for ray tracing in a 3D graphics rendering system.
 * It provides a framework for tracing rays through a scene and determining the color of the corresponding pixels.
 */
public abstract class RayTracerBase {

    /**
     * The scene to be rendered by the ray tracer.
     */
    protected Scene scene;

    /**
     * Constructs a RayTracerBase with the specified scene.
     *
     * @param scene The scene to be rendered by the ray tracer.
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Abstract method to be implemented by concrete subclasses for tracing a ray through the scene
     * and determining the color of the corresponding pixel.
     *
     * @param ray The ray to be traced through the scene.
     * @return The Color representing the calculated color of the pixel.
     */
    abstract public Color traceRay(Ray ray,boolean useSoftShadows);
}
