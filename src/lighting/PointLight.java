package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
import renderer.Blackboard;

/**
 * Represents a point light source that illuminates objects from a specific position.
 */
public class PointLight extends Light implements LightSource {

    public Blackboard blackboard;

    /** The position of the point light source. */
    protected Point position;

    /** The constant attenuation factor. */
    private double kC = 1;

    /** The linear attenuation factor. */
    private double kL = 0;

    /** The quadratic attenuation factor. */
    private double kQ = 0;

    public Point getPosition() {
        return position;
    }

    /**
     * Constructs a new point light with the specified intensity.
     *
     * @param intensity the intensity of the light
     */
    public PointLight(Color intensity,Point position) {
        super(intensity);
        this.position = position;
        blackboard = new Blackboard(0,0,0);
        blackboard.setWidth(4);
        blackboard.setHeight(4);
        blackboard.setK(9);
    }


    /**
     * Sets the quadratic attenuation factor.
     *
     * @param kQ the quadratic attenuation factor to set
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Sets the constant attenuation factor.
     *
     * @param kC the constant attenuation factor to set
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the linear attenuation factor.
     *
     * @param kL the linear attenuation factor to set
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Calculates the intensity of the light at the specified point.
     *
     * @param p the point at which to calculate the intensity
     * @return the intensity of the light
     */
    @Override
    public Color getIntensity(Point p) {
        return this.intensity.scale(1 / (kC + kL * p.distance(this.position) + kQ * p.distanceSquared(this.position)));
    }

    /**
     * Calculates the direction vector from the specified point to the light source.
     *
     * @param p the point from which to determine the direction
     * @return the direction vector from the point to the light source
     */
    @Override
    public Vector getL(Point p) {
        return p.subtract(this.position).normalize();
    }

    /**
     * Calculates the distance from the specified point to the light source.
     *
     * @param point the point from which to determine the direction
     * @return the direction vector from the point to the light source
     */
    @Override
    public double getDistance(Point point){
        return Math.abs(point.distance(position));
    }
}
