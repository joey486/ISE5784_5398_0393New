package renderer;

import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

public class softShadowTest {
    /** Scene of the tests */
    private final Scene scene      = new Scene("softShadowTest scene")
            .setAmbientLight(new AmbientLight(new Color(gray), new Double3(0.15)));
    /** Camera builder of the tests */


    /**
     * Second camera builder for some of tests
     */
    private final Camera.Builder camera = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene))
            .setLocation(new Point(700, 0, 100))
            .setDirection(new Vector(-1, 0, 0), new Vector(0, 0, 1))
            .setVpSize(200, 200).setVpDistance(100);

    @Test
    public void  softShadowTest(){
        //points
        Point p1 = new Point(0,0,0);
        Point lightPosition = new Point(10,100,150);
        Point p2 = new Point(20,10,100);

        //vectors
        Vector v1 = new Vector(0,0,1);
        Vector v2 = new Vector(0,-0.707,-0.707);

        //background
        Geometry floor = new Plane(p1,v1)
                .setMaterial(new Material()).setEmission(new Color(gray));
        scene.geometries.add(floor);

        scene.lights.add(new SpotLight(new Color(red).scale(10),lightPosition,v2));


        //cube
        //sphere
        Geometry sphere = new Sphere(100d,p2)
                .setMaterial(new Material()).setEmission(new Color(gray.brighter()));
        scene.geometries.add(sphere);
        //pyramid



        //write
        camera
                .setImageWriter(new ImageWriter("soft Shadow Test", 1000, 1000))
                .build()
                .renderImage()
                .writeToImage();
    }
}
