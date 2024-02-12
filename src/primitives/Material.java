package primitives;

/**
 * The Material class represents the optical properties of a surface, including
 * diffuse reflection (kD), specular reflection (kS), and shininess (nShininess).
 */
public class Material {

    /** The diffuse reflection coefficient. */
    public Double3 kD = Double3.ZERO;

    /** The specular reflection coefficient. */
    public Double3 kS = Double3.ZERO;

    /** The shininess of the material. */
    public int nShininess = 0;

    public Double3 kT = Double3.ZERO;
    public Double3 kR = Double3.ZERO;

    /**
     * Sets the diffuse reflection coefficient.
     *
     * @param kD the diffuse reflection coefficient to set
     * @return the current Material object for method chaining
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient from a single value.
     *
     * @param kD the diffuse reflection coefficient to set
     * @return the current Material object for method chaining
     */
    public Material setKd(Double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Sets the specular reflection coefficient.
     *
     * @param kS the specular reflection coefficient to set
     * @return the current Material object for method chaining
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Sets the specular reflection coefficient from a single value.
     *
     * @param kS the specular reflection coefficient to set
     * @return the current Material object for method chaining
     */
    public Material setKs(Double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Sets the shininess of the material.
     *
     * @param nShininess the shininess to set
     * @return the current Material object for method chaining
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
    public Material setKt(Double3 kT){
        this.kT = kT;
        return this;
    }

    public Material setKr(Double3 kR){
        this.kR = kR;
        return this;
    }
    public Material setKt(Double kT){
        this.kT = new Double3(kT);
        return this;
    }

    public Material setKr(Double kR){
        this.kR = new Double3(kR);
        return this;
    }
}
