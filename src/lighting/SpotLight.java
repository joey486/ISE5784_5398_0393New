package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
import renderer.Blackboard;

/**
 * Represents a spot light source that illuminates objects from a specific position
 * in a specified direction within a limited cone angle.
 */
public class SpotLight extends PointLight {

    /** The direction in which the light is shining. */
    private Vector direction;

    /**
     * Constructs a new spot light with the specified intensity.
     *
     * @param intensity the intensity of the light
     */
    public SpotLight(Color intensity,Point position,Vector direction) {
        super(intensity,position);
        this.direction = direction.normalize();
        blackboard = new Blackboard(0,0,0);
        blackboard.setWidth(4);
        blackboard.setHeight(4);
        blackboard.setK(9);
    }
    public SpotLight(Color intensity,Point position,Vector direction,double width,double height,double k) {
        super(intensity,position);
        this.direction = direction.normalize();
        blackboard = new Blackboard(0,0,0);
        blackboard.setWidth(width);
        blackboard.setHeight(height);
        blackboard.setK(k);
    }

    @Override
    public SpotLight setkC(double kC) {
        return (SpotLight) super.setkC(kC);
    }
    @Override
    public SpotLight setKl(double kL) {
        return (SpotLight) super.setKl(kL);
    }
    @Override
    public SpotLight setKq(double kQ) {
        return (SpotLight) super.setKq(kQ);
    }



    /**
     * Returns the intensity of the light.
     *
     * @return the intensity of the light
     */
    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity(p).scale(Math.max(0,direction.dotProduct(p.subtract(position).normalize())));
    }

    /**
     * Calculates the direction vector from the specified point to the light source,
     * considering the spotlight cone angle and direction.
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
    public double getDistance(Point point) {
        return super.getDistance(point);
    }
}
