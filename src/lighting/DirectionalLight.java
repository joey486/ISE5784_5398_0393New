package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a directional light source that illuminates all objects uniformly
 * from a given direction.
 */
public class DirectionalLight extends Light implements LightSource {

    /** The direction from which the light is coming. */
    private Vector direction;

    /**
     * Constructs a new directional light with the specified intensity.
     *
     * @param intensity the intensity of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * Returns the intensity of the light at the specified point.
     *
     * @param p the point at which to calculate the intensity
     * @return the intensity of the light
     */
    @Override
    public Color getIntensity(Point p) {
        return intensity;
    }

    /**
     * Returns the direction vector of the light.
     *
     * @param point the point at which to determine the direction vector
     * @return the direction vector of the light
     */
    @Override
    public Vector getL(Point point) {
        return direction.normalize();
    }

    /**
     * Calculates the distance from the specified point to the light source (infinity).
     *
     * @param point the point from which to determine the direction
     * @return the direction vector from the point to the light source
     */
    @Override
    public double getDistance(Point point){
        return Double.POSITIVE_INFINITY;
    }
}
