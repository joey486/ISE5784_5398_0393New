package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * The Scene class represents a 3D scene in computer graphics, containing information about the scene's name,
 * background color, ambient light, and geometries.
 */
public class Scene {

    /** The name of the scene. */
    public String name;

    /** The background color of the scene. */
    public Color background = Color.BLACK;

    /** The ambient light in the scene. Default is AmbientLight.NONE. */
    public AmbientLight ambientLight = AmbientLight.NONE;

    /** The collection of geometries in the scene. */
    public Geometries geometries = new Geometries();

    /** The collection of light sources in the scene. */
    public List<LightSource> lights = new LinkedList<>();

    /**
     * Constructs a Scene with the specified name.
     *
     * @param name The name of the scene.
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * Sets the background color of the scene.
     *
     * @param background The background color to set.
     * @return The current Scene object for method chaining.
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light in the scene.
     *
     * @param ambientLight The ambient light to set.
     * @return The current Scene object for method chaining.
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the collection of geometries in the scene.
     *
     * @param geometries The geometries to set.
     * @return The current Scene object for method chaining.
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Sets the collection of light sources in the scene.
     *
     * @param lights The light sources to set.
     * @return The current Scene object for method chaining.
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
