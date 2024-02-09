package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The LightSource interface represents a source of light in a scene.
 * Classes implementing this interface provide functionality for interacting with light sources.
 */
public interface LightSource {

    /**
     * Retrieves the intensity of the light at the specified point in the scene.
     *
     * @param p the point in the scene
     * @return the intensity of the light at the specified point
     */
    public Color getIntensity(Point p);

    /**
     * Retrieves the direction vector from the specified point towards the light source.
     *
     * @param p the point in the scene
     * @return the direction vector from the specified point towards the light source
     */
    public Vector getL(Point p);
    public double getDistance(Point point);
}
