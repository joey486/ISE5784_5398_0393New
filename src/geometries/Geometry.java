package geometries;

import primitives.*;

import java.util.List;
import java.util.Objects;

/**
 * The Geometry class represents a geometric shape in three-dimensional space.
 * Subclasses of Geometry should provide methods to calculate properties such as
 * the normal vector at a given point on the shape's surface.
 */
public abstract class Geometry extends Intersectable {

    /** The emission color of the geometry. */
    protected Color emission = Color.BLACK;

    /** The material color of the geometry. */
    private Material material = new Material();

    /**
     * Gets the emission color of the geometry.
     *
     * @return The emission color of the geometry.
     */
    public Color getEmission() {
        return this.emission;
    }

    /**
     * Sets the emission color of the geometry.
     *
     * @param emission The emission color to set.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Gets the material color of the geometry.
     *
     * @return The material color of the geometry.
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the material color of the geometry.
     *
     * @param material The emission color to set.
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Gets the normal vector at a specified point on the surface of the geometry.
     *
     * @param point The point on the surface for which to calculate the normal vector.
     * @return The normal vector at the specified point.
     */
    public abstract Vector getNormal(Point point);
}
