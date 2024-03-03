package renderer;

import geometries.Intersectable.GeoPoint;
import primitives.*;
import primitives.Color;
import primitives.Point;
import scene.Scene;

import java.awt.*;
import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The Camera class represents a virtual camera in a 3D graphics rendering system.
 * It defines the camera's position, orientation, and parameters for generating rays.
 */
public class Camera implements Cloneable {

    private Point p0;      // Camera position
    private Vector vRight;  // Right vector of the camera orientation
    private Vector vUp;     // Up vector of the camera orientation
    private Vector vTo;     // To vector of the camera orientation
    private double width = 0.0;    // Width of the view plane
    private double height = 0.0;   // Height of the view plane
    private double distance = 0.0; // Distance from the camera to the view plane
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    boolean useSoftShadows = true;
    /**
     * Private constructor for Camera.
     * Initializes the camera with default values.
     */
    private Camera() {
    }

    /**
     * Get the width of the view plane.
     *
     * @return The width of the view plane.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Set the width of the view plane.
     *
     * @param width The width of the view plane.
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Get the height of the view plane.
     *
     * @return The height of the view plane.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Set the height of the view plane.
     *
     * @param height The height of the view plane.
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Get the distance from the camera to the view plane.
     *
     * @return The distance from the camera to the view plane.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Set the distance from the camera to the view plane.
     *
     * @param distance The distance from the camera to the view plane.
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * The Builder class is a static inner class within the Camera class,
     * designed to facilitate the construction of Camera objects using a fluent API.
     * It allows for setting various parameters of the camera in a chainable manner.
     * <p>
     * This class follows the Builder pattern to create Camera instances with a clear and
     * expressive syntax, making it easy to configure the camera parameters during instantiation.
     * Note that the actual implementation of the Builder class is missing and should be provided
     * as part of the Camera class.
     */
    public static class Builder {
        final Camera camera = new Camera();

        /**
         * Set the location of the camera.
         *
         * @param point The position of the camera.
         * @return The Builder instance for method chaining.
         * @throws IllegalArgumentException If the provided point is (0, 0, 0).
         */
        public Builder setLocation(Point point) {
            camera.p0 = point;
            return this;
        }

        /**
         * Set the direction vectors of the camera.
         *
         * @param vTo The direction vector towards which the camera is pointing.
         * @param vUp The up vector defining the camera's up direction.
         * @return The Builder instance for method chaining.
         * @throws IllegalArgumentException If vTo and vUp are not perpendicular.
         */
        public Builder setDirection(Vector vTo, Vector vUp) throws IllegalArgumentException {
            if (vTo.dotProduct(vUp) != 0)
                throw new IllegalArgumentException("Vectors TO and UP must be perpendicular");
            this.camera.vTo = vTo.normalize();
            this.camera.vUp = vUp.normalize();
            this.camera.vRight = vTo.crossProduct(vUp).normalize();
            return this;
        }

        /**
         * Set the size of the view plane.
         *
         * @param width  The width of the view plane.
         * @param height The height of the view plane.
         * @return The Builder instance for method chaining.
         * @throws IllegalArgumentException If width or height is 0.
         */
        public Builder setVpSize(double width, double height) throws IllegalArgumentException {
            if (width == 0) throw new IllegalArgumentException("Illegal width, cannot be 0");
            if (height == 0) throw new IllegalArgumentException("Illegal height, cannot be 0");
            this.camera.width = width;
            this.camera.height = height;
            return this;
        }

        /**
         * Set the distance from the camera to the view plane.
         *
         * @param distance The distance from the camera to the view plane.
         * @return The Builder instance for method chaining.
         * @throws IllegalArgumentException If the distance is 0.
         */
        public Builder setVpDistance(double distance) throws IllegalArgumentException {
            if (distance == 0) throw new IllegalArgumentException("Illegal distance, cannot be 0");
            this.camera.setDistance(distance);
            return this;
        }

        public Builder setImageWriter(ImageWriter imageWriter){
            this.camera.imageWriter = imageWriter;
            return this;
        }

        public Builder setRayTracer(SimpleRayTracer simpleRayTracer){
            this.camera.rayTracer = simpleRayTracer;
            return this;
        }

        public Builder setUseSoftShadows(boolean useSoftShadows){
            this.camera.useSoftShadows = useSoftShadows;
            return this;
        }

        /**
         * Rotates the camera around its viewing axis by the specified angle in degrees.
         * Positive angles rotate counter-clockwise, and negative angles rotate clockwise.
         *
         * @param degrees the angle of rotation in degrees
         * @return the Builder instance for method chaining
         */
        public Builder rotate(double degrees) {
            // Convert degrees to radians for trigonometric functions
            double radians = alignZero(Math.toRadians(360 - degrees));

            // Calculate the cosine and sine of the angle
            double cosin = alignZero(Math.cos(radians));
            double sinus = alignZero(Math.sin(radians));

            // Initialize a temporary vector
            Vector v = null;

            // If cosine is not approximately zero, scale the camera's upward vector by cosine
            if (!isZero(cosin))
                v = camera.vUp.scale(cosin);

            // If sine is not approximately zero
            if (!isZero(sinus)) {
                // If cosine is not approximately zero, perform a vector addition and scaling
                if (!isZero(cosin))
                    v = v.add(camera.vTo.crossProduct(camera.vUp).scale(sinus));
                else
                    // If cosine is approximately zero, perform a cross product and scaling
                    v = camera.vTo.crossProduct(camera.vUp).scale(sinus);
            }

            // Update the camera's upward vector
            camera.vUp = v;

            // Update the camera's rightward vector (cross product of view direction and upward vector)
            camera.vRight = camera.vTo.crossProduct(camera.vUp);

            // Return the Builder instance for method chaining
            return this;
        }


        /**
         * Build the Camera instance.
         *
         * @return The constructed Camera instance.
         * @throws MissingResourceException If any required parameter is missing.
         * @throws RuntimeException        If cloning is not supported.
         */
        public Camera build() {
            final String MissingValue  = "Bad ray";
            if (camera.vTo == null) throw new MissingResourceException(MissingValue, "in vTo vector", "undefined");
            if (camera.vRight == null) throw new MissingResourceException(MissingValue, "in vRight vector", "undefined");
            if (camera.vUp == null) throw new MissingResourceException(MissingValue, "in vUp vector", "undefined");
            if (isZero(camera.height))
                throw new MissingResourceException(MissingValue, "in camera-height", "value is 0");
            if (isZero(camera.width))
                throw new MissingResourceException(MissingValue, "in camera-width", "value is 0");
            if (isZero(camera.distance))
                throw new MissingResourceException(MissingValue, "in camera-distance", "value is 0");
            if (camera.distance < 0 || camera.width < 0 || camera.height < 0)
                throw new RuntimeException("ERROR, no parameter can be below 0");
            if (camera.rayTracer == null) throw new MissingResourceException(MissingValue, "in ray Tracer", "undefined");
            if (camera.imageWriter == null) throw new MissingResourceException(MissingValue, "in image writer", "undefined");

            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Static method to get a builder for the Camera class.
     *
     * @return A new instance of the Camera.Builder class.
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Construct a ray from the camera through a specified pixel on the view plane.
     *
     * @param nX The number of pixels in the x-direction.
     * @param nY The number of pixels in the y-direction.
     * @param j  The x-coordinate of the pixel.
     * @param i  The y-coordinate of the pixel.
     * @return A Ray representing the direction from the camera through the specified pixel.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pc = this.p0.add(vTo.scale(distance));
        double Ry = height / nX;
        double Rx = width / nY;
        double yI = -(i - (nY - 1) / 2d) * Ry;
        double xJ = (j - (nX - 1) / 2d) * Rx;
        Point pIJ = pc;
        if (xJ != 0) pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0) pIJ = pIJ.add(vUp.scale(yI));
        return new Ray(p0, pIJ.subtract(p0));
    }

    /**
     * Renders the image by casting rays through each pixel of the view plane.
     *
     * @return The Camera instance after rendering the image.
     */
    public Camera renderImage() {
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++)
                castRay(nX, nY, i, j);
        }
        return this;
    }

    /**
     * Prints a grid on the image at specified intervals with the given color.
     *
     * @param interval The interval between grid lines.
     * @param color    The color of the grid lines.
     * @return The Camera instance after printing the grid.
     */
    public Camera printGrid(int interval, Color color) {
        // Iterate through each pixel in the grid of the image and set it to the color
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(i, j, color); // Set pixel color to the specified color
                }
            }
        }
        return this;
    }

    /**
     * Writes the image to the output file.
     *
     * @return The Camera instance after writing the image.
     */
    public Camera writeToImage() {
        imageWriter.writeToImage();
        return this;
    }

    /**
     * Casts a ray through a specified pixel on the view plane and traces it to determine the pixel color.
     *
     * @param nX The number of pixels in the x-direction.
     * @param nY The number of pixels in the y-direction.
     * @param j  The x-coordinate of the pixel.
     * @param i  The y-coordinate of the pixel.
     */
    private void castRay(int nX, int nY, int j, int i) {
        Ray ray = constructRay(nX, nY, j, i);
        imageWriter.writePixel(j, i,
                rayTracer.traceRay(ray));
    }

}