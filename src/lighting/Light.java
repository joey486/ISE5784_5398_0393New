package lighting;

import primitives.Color;

/**
 * The abstract class Light represents a generic light source.
 * This class serves as a base for specific types of lights.
 */
abstract class Light {
    protected Color intensity;

    /**
     * Constructs a new Light object with the specified intensity.
     *
     * @param intensity the intensity of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Returns the intensity of the light.
     *
     * @return the intensity of the light
     */
    public Color getIntensity() {
        return this.intensity;
    }
}
