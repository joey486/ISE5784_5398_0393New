package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * The AmbientLight class represents ambient lighting in a 3D scene, providing a uniform illumination
 * to all objects in the scene. It is characterized by an ambient color (IA) and a coefficient (KA).
 */
public class AmbientLight extends Light {

    /**
     * The ambient color of the light.
     */
    Color IA;

    /**
     * The coefficient for ambient light intensity.
     */
    Double3 KA;

    /**
     * A constant representing no ambient light (black color and zero coefficient).
     */
    public final static AmbientLight NONE = new AmbientLight(new Color(java.awt.Color.BLACK), new Double3(0.0));

    /**
     * Constructs an AmbientLight with the specified ambient color and coefficient.
     *
     * @param IA The ambie
     *           nt color.
     * @param KA The coefficient for ambient light intensity.
     */
    public AmbientLight(Color IA, Double3 KA) {
        super(IA.scale(KA));
//        this.IA = IA;
//        this.KA = KA;
    }

    /**
     * Constructs an AmbientLight with the specified ambient color and coefficient.
     *
     * @param IA The ambient color.
     * @param KA The coefficient for ambient light intensity.
     */
    public AmbientLight(Color IA, double KA) {
        super(IA.scale(KA));
        this.IA = IA;
        this.KA = new Double3(KA);
    }
}
