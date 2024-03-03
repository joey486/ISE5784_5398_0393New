package renderer;

import geometries.Geometry;
import geometries.Plane;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import static java.awt.Color.gray;
import static java.awt.Color.white;

public class softShadowTest {
    /** Scene of the tests */
    private final Scene scene      = new Scene("softShadowTest scene");
    /** Camera builder of the tests */
    private final Camera.Builder camera     = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
            .setVpSize(200, 200)
            .setRayTracer(new SimpleRayTracer(scene));

    @Test
    public void  softShadowTest(){
        //points
        Point p1 = new Point(0,0,0);
        Point lightPosition = new Point(-300,-300,100);

        //vectors
        Vector v1 = new Vector(0,0,1);

        //background
        Geometry floor = new Plane(p1,v1)
                .setMaterial(new Material()).setEmission(new Color(gray));
        scene.lights.add(new PointLight(new Color(white),lightPosition));

        //cube1

    }
}
