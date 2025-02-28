package renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class LightsTests {
    /**
     * First scene for some of tests
     */
    private final Scene scene1 = new Scene("Test scene");
    /**
     * Second scene for some of tests
     */
    private final Scene scene2 = new Scene("Test scene")
            .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

    /**
     * First camera builder for some of tests
     */
    private final Camera.Builder camera1 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene1))
            .setLocation(new Point(0, 0, 1000))
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpSize(150, 150).setVpDistance(1000);
    /**
     * Second camera builder for some of tests
     */
    private final Camera.Builder camera2 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene2))
            .setLocation(new Point(0, 0, 1000))
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpSize(200, 200).setVpDistance(1000);

    /**
     * Shininess value for most of the geometries in the tests
     */
    private static final int SHININESS = 301;
    /**
     * Diffusion attenuation factor for some of the geometries in the tests
     */
    private static final double KD = 0.5;
    /**
     * Diffusion attenuation factor for some of the geometries in the tests
     */
    private static final Double3 KD3 = new Double3(0.2, 0.6, 0.4);

    /**
     * Specular attenuation factor for some of the geometries in the tests
     */
    private static final double KS = 0.5;
    /**
     * Specular attenuation factor for some of the geometries in the tests
     */
    private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);

    /**
     * Material for some of the geometries in the tests
     */
    private final Material material = new Material().setKd(KD3).setKs(KS3).setShininess(SHININESS);
    /**
     * Light color for tests with triangles
     */
    private final Color trianglesLightColor = new Color(800, 500, 250);
    /**
     * Light color for tests with sphere
     */
    private final Color sphereLightColor = new Color(800, 500, 0);
    /**
     * Color of the sphere
     */
    private final Color sphereColor = new Color(BLUE).reduce(2);

    /**
     * Center of the sphere
     */
    private final Point sphereCenter = new Point(0, 0, -50);
    /**
     * Radius of the sphere
     */
    private static final double SPHERE_RADIUS = 50d;

    /**
     * The triangles' vertices for the tests with triangles
     */
    private final Point[] vertices =
            {
                    // the shared left-bottom:
                    new Point(-110, -110, -150),
                    // the shared right-top:
                    new Point(95, 100, -150),
                    // the right-bottom
                    new Point(110, -110, -150),
                    // the left-top
                    new Point(-75, 78, 100)
            };
    /**
     * Position of the light in tests with sphere
     */
    private final Point sphereLightPosition = new Point(-50, -50, 25);
    /**
     * Light direction (directional and spot) in tests with sphere
     */
    private final Vector sphereLightDirection = new Vector(1, 1, -0.5);
    /**
     * Position of the light in tests with triangles
     */
    private final Point trianglesLightPosition = new Point(30, 10, -100);
    /**
     * Light direction (directional and spot) in tests with triangles
     */
    private final Vector trianglesLightDirection = new Vector(-2, -2, -2);

    /**
     * The sphere in appropriate tests
     */
    private final Geometry sphere = new Sphere(SPHERE_RADIUS, sphereCenter)
            .setEmission(sphereColor).setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));
    /**
     * The first triangle in appropriate tests
     */
    private final Geometry triangle1 = new Triangle(vertices[0], vertices[1], vertices[2])
            .setMaterial(material);
    /**
     * The first triangle in appropriate tests
     */
    private final Geometry triangle2 = new Triangle(vertices[0], vertices[1], vertices[3])
            .setMaterial(material);

    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new DirectionalLight(sphereLightColor, sphereLightDirection));

        camera1.setImageWriter(new ImageWriter("lightSphereDirectional", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new PointLight(sphereLightColor, sphereLightPosition)
                .setKl(0.001).setKq(0.0002));

        camera1.setImageWriter(new ImageWriter("lightSpherePoint", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spotlight
     */
    @Test
    public void sphereSpot() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(sphereLightColor, sphereLightPosition, sphereLightDirection)
                .setKl(0.001).setKq(0.0001));

        camera1.setImageWriter(new ImageWriter("lightSphereSpot", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new DirectionalLight(trianglesLightColor, trianglesLightDirection));

        camera2.setImageWriter(new ImageWriter("lightTrianglesDirectional", 500, 500)) //
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new PointLight(trianglesLightColor, trianglesLightPosition)
                .setKl(0.001).setKq(0.0002));

        camera2.setImageWriter(new ImageWriter("lightTrianglesPoint", 500, 500)) //
                .build() //
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Produce a picture of two triangles lighted by a spotlight
     */
    @Test
    public void trianglesSpot() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
                .setKl(0.001).setKq(0.0001));

        camera2.setImageWriter(new ImageWriter("lightTrianglesSpot", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    @Test
   public void testScene1() {
//        var spotLight = new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
//                .setKl(0.001).setKq(0.0001);
//        var pointLight = new PointLight(trianglesLightColor, trianglesLightPosition)
//                .setKl(0.001).setKq(0.002);
//        var directionalLight = new DirectionalLight(trianglesLightColor, trianglesLightDirection);
//        List<Geometry> list = CubeCreator();
//        for(Geometry i: list) scene2.geometries.add(i.setMaterial(material).setEmission(new Color(red)));
//        scene2.lights.add(spotLight);
//        scene2.lights.add(pointLight);
//        scene2.lights.add(directionalLight);
//
//        camera2.setImageWriter(new ImageWriter("testScene1", 500, 500))
//                .build()
//                .renderImage()
//                .writeToImage();
    }

    @Test
    public void testScene2() {
        Geometry sphere1 = new Sphere(20,new Point(-50, -50, 25)).setMaterial(material).setEmission(new Color(5,5,0));
        Geometry sphere2 = new Sphere(10,new Point(0, -50, 25)).setMaterial(material).setEmission(new Color(70,0,12));
        scene2.geometries.add(triangle1, triangle2,sphere1,sphere2);
        scene2.lights.add(new PointLight(new Color(blue), trianglesLightPosition)
                .setKl(0.001).setKq(0.0002));
        scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition.subtract(vertices[0]), trianglesLightDirection.normalize())
                .setKl(0.001).setKq(0.0001));
        scene2.lights.add(new PointLight(new Color(255,0,160).scale(10), new Point(-50, 40, 25))
                .setKl(0.001).setKq(0.002));

        camera2.setImageWriter(new ImageWriter("testScene2", 500, 500)) //
                .build() //
                .renderImage() //
                .writeToImage(); //
    }

    public final Point[] pointsOfCube = {
            new Point(-55, -55, -75),
            new Point(47.5, 50, -75),
            new Point(50, -55, -75),
            new Point(-37.5, 39, 25),
            new Point(-45, -55, -75),
            new Point(57.5, 50, -75),
            new Point(65, -55, -75),
            new Point(-27.5, 39, 25)
    };



    public List<Geometry> CubeCreator (){
            List<Geometry> cubeTriangles = new LinkedList<>();

            // Define the vertices of the cube
            Point A = pointsOfCube[0];
            Point B = pointsOfCube[1];
            Point C = pointsOfCube[2];
            Point D = pointsOfCube[3];
            Point E = pointsOfCube[4];
            Point F = pointsOfCube[5];
            Point G = pointsOfCube[6];
            Point H = pointsOfCube[7];

            // Front face
            cubeTriangles.add(new Triangle(A, B, C));
            cubeTriangles.add(new Triangle(A, C, G));

            // Back face
            cubeTriangles.add(new Triangle(E, F, H));
            cubeTriangles.add(new Triangle(E, H, D));

            // Top face
            cubeTriangles.add(new Triangle(A, G, H));
            cubeTriangles.add(new Triangle(A, H, E));

            // Bottom face
            cubeTriangles.add(new Triangle(B, D, F));
            cubeTriangles.add(new Triangle(B, F, C));

            // Right face
            cubeTriangles.add(new Triangle(C, F, G));
            cubeTriangles.add(new Triangle(C, B, F));

            // Left face
            cubeTriangles.add(new Triangle(A, E, D));
            cubeTriangles.add(new Triangle(A, D, B));

            return cubeTriangles;
    }
}