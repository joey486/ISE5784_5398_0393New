import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class finaltests {
    /**
     * Second scene for some of tests
     */
    private final Scene scene = new Scene("home scene")
            .setAmbientLight(new AmbientLight(new Color(yellow), new Double3(0.15)));

    /**
     * Second camera builder for some of tests
     */
    private final Camera.Builder camera1 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene))
            .setLocation(new Point(700, 0, 100))
            .setDirection(new Vector(-1, 0, 0), new Vector(0, 0, 1))
            .setVpSize(200, 200).setVpDistance(100);

    private final Camera.Builder camera2 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene))
            .setLocation(new Point(700, 0, 100))
            .setDirection(new Vector(-1, 0, 0), new Vector(0, 0, 1))
            .setVpSize(200, 200).setVpDistance(100)
            .rotate(120);

    /**
     * Shininess value for most of the geometries in the tests
     */
    private static final int SHININESS = 301;
    /**
     * Diffusion attenuation factor for some of the geometries in the tests
     */
    private static final double KD = 0.5;

    /**
     * Specular attenuation factor for some of the geometries in the tests
     */
    private static final double KS = 0.5;
    /**
     * Specular attenuation factor for some of the geometries in the tests
     */
    private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);

    /**
     * Produce a picture
     */
    @Test
    public void HouseTest(){
        //set points
        final double DELTA = 1;
        Point sunPosition = new Point(-300,400,400);
        Point p1 = new Point(200,-100,0);
        Point p2 = new Point(200,-200,0);
        Point p3 = new Point(200,-200,100);
        Point p4 = new Point(200,-100,100);
        Point p5 = new Point(150,-90,0);
        Point p6 = new Point(150,-90,105);
        Point p7 = new Point(125,-160,130);
        Point p8 = new Point(160,0,0);
        Point p9 = new Point(160,-20,0);
        Point p10= new Point(160,-20,130);
        Point p11 = new Point(160,0,130);
        Point p12 = new Point(160,-20,140);
        Point p13 = new Point(160,5,150);
        Point p14 = new Point(160,-10,160);
        Point p15 = new Point(0,-100,400);
        Point p16 = new Point(0,-125,410);
        Point p17 = new Point(400,50,0);
        Point p18 = new Point(520,100,0.1);
        Point p19 = new Point(550,75,0.1);
        Point p20 = new Point(540,40,0.1);

        //set lights
        scene.background = new Color(cyan);
        scene.lights.add(new PointLight(new Color(white.darker()),sunPosition));

        //set background
        Geometry ground = new Plane(new Point(0,0,0),new Vector(0,0,1))
                .setEmission(new Color(green)).setMaterial(new Material().setShininess(SHININESS).setKd(KD).setKs(KS));
        Geometry sun = new Sphere(50,sunPosition)
                .setEmission(new Color(yellow.brighter())).setMaterial(new Material().setKt(0.9));
        scene.geometries.add(ground,sun);

        //set walls
        Geometry t1 = new Triangle(p1,p2,p3)
                .setEmission(new Color(magenta).reduce(4)).setMaterial(new Material().setKt(0.6d));
        Geometry t2 = new Triangle(p4,p1.subtract(new Point(0,DELTA,0)),p3)
                .setEmission(new Color(magenta).reduce(4)).setMaterial(new Material().setKt(0.6d));
        Geometry t3 = new Triangle(p1,p4,p5)
                .setEmission(new Color(magenta).reduce(3)).setMaterial(new Material().setKt(0.6d));
        Geometry t4 = new Triangle(p6,p4,p5.subtract(new Point(0,DELTA,0)))
                .setEmission(new Color(magenta).reduce(3)).setMaterial(new Material().setKt(0.6d));
        scene.geometries.add(t1,t2,t3,t4);
        //set roof
        Geometry r1 = new Triangle(p4,p6,p7)
                .setEmission(new Color(red));
        Geometry r2 = new Triangle(p4,p3,p7)
                .setEmission(new Color(red));
        scene.geometries.add(r1,r2);
        //set tree trunk
        Geometry tr1 = new Triangle(p8,p9,p10)
                .setEmission(new Color(125,75,0));
        Geometry tr2 = new Triangle(p11,p8,p10)
                .setEmission(new Color(125,75,0));
        scene.geometries.add(tr1,tr2);
        //set tree leafs
        Geometry leafs1 = new Sphere(50d,p12).setEmission(new Color(green.darker().darker().darker()));
        Geometry leafs2 = new Sphere(50d,p13).setEmission(new Color(green.darker()));
        Geometry leafs3 = new Sphere(50d,p14).setEmission(new Color(green.darker().darker()));
        scene.geometries.add(leafs1,leafs2,leafs3);

        //set clouds
        Geometry c1 = new Sphere(20,p15)
                .setEmission(new Color(gray));
        Geometry c2 = new Sphere(20,p16)
                .setEmission(new Color(gray));

        scene.geometries.add(c1,c2);

        //set water
        Geometry w1 = new Triangle(p17,p18,p19)
                .setEmission(new Color(blue)).setMaterial(new Material().setKr(0.001d));
        Geometry w2 = new Triangle(p17,p19,p20)
                .setEmission(new Color(blue)).setMaterial(new Material().setKr(0.001));

        scene.geometries.add(w1,w2);

        //finish
        camera1.setImageWriter(new ImageWriter("homeScene1", 500, 500)) //
        .build()
        .renderImage()
        .writeToImage();

        camera2.setImageWriter(new ImageWriter("homeScene2", 500, 500)) //
                .build()
                .renderImage()
                .writeToImage();
    }


    //    @Test
//    public void FinalScene0() {
//        //---------------------------
//        //Default objects & lights
//        //---------------------------
//        scene.lights.add(new PointLight(new Color(white),new Point(-10000,0,2000)));
//        scene.setAmbientLight(new AmbientLight(new Color(blue).scale(0.5), new Double3(0.2, 0.2, 0.2)));
////        scene.background = new Color(cyan.darker());
//
//        final double DELTA = 1;
//        final double delta_z = 0.1;
//        final double delta_Island = 0;
//
//        // Define points
//        Point p1= new Point(DELTA, 300, 0+delta_z);
//        Point p2 = new Point(DELTA, 600, 0+delta_z);
//        Point p3 = new Point(DELTA, 625, 100+delta_z);
//        Point p4 = new Point(DELTA, 640, 150+delta_z);
//        Point p5 = new Point(DELTA, 310, 180+delta_z);
//        Point p6 = new Point(DELTA, 200, 120+delta_z);
//        Point p7 = new Point(DELTA, 210, 125+delta_z);
//        Point p8 = new Point(DELTA, 240, 250+delta_z);
//        Point p9 = new Point(DELTA, 580, 150+delta_z);
//        Point p10 = new Point(DELTA, 570, 155+delta_z);
//        Point p11 = new Point(DELTA, 597.5, 230+delta_z);
//        Point p12 = new Point(DELTA,700,500+delta_z);
//        Point p13 = new Point(-999,-50,10+delta_Island);
//        Point p14 = new Point(-999,-600,10+delta_Island);
//        Point p15 = new Point(-999,-220,110+delta_Island);
//        Point p16 = new Point(-999,-270,90+delta_Island);
//        Point p17 = new Point(-999,-290,80+delta_Island);
//        Point p18 = new Point(-999,-285,300+delta_Island);
//
//        //---------------------------
//        //Scenery
//        //---------------------------
//        Geometry Sea = new Plane(new Point(1000,0,0), new Point(100,100,0),new Point(-100,-90,0))
//                .setMaterial(material.setShininess(10).setKt(0d).setKr(0.2)).setEmission(new Color(BLUE));
//
//        Geometry Sky = new Plane(new Point(-1000,0,0),new Vector(1,0,0))
//                .setMaterial(new Material().setKd(0.2).setKs(0.2).setKt(0.01).setKr(0d)).setEmission(new Color(10,10,4));
//
//        Geometry Sun = new Sphere(100d,new Point(-1000,0,1000))
//                .setEmission(new Color(white)).setMaterial(material.setKt(1d));
//
//        scene.geometries.add(Sea,Sky,Sun);
//
//        //---------------------------
//        //Boat - Body
//        //---------------------------
//
//        Geometry t1 = new Triangle(p1, p2, p3)
//               .setMaterial(material).setEmission(new Color(CYAN).scale(10));
//        Geometry t2 = new Triangle(p3, p4, p5)
//                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0d)).setEmission(new Color(pink).reduce(2));
//        Geometry t3 = new Triangle(p1, p3, p5)
//                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0d)).setEmission(new Color(pink).reduce(2));
//        Geometry t4 = new Triangle(p1, p5, p6)
//                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0d)).setEmission(new Color(pink).reduce(2));
//
//        scene.geometries.add(t1,t2,t3,t4);
//
//        //---------------------------
//        //Boat - Ropes
//        //---------------------------
//
//        Geometry t5 = new Triangle(p6, p7, p8)
//                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.1)).setEmission(new Color(WHITE));
//        Geometry t6 = new Triangle(p9, p10, p11)
//                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.1)).setEmission(new Color(WHITE));
//
//        scene.geometries.add(t5,t6);
//
//        //---------------------------
//        //Boat - Sail
//        //---------------------------
//
//        Geometry t7 = new Triangle(p8, p11, p12)
//                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)
//                        .setKt(0.1)).setEmission(new Color(CYAN).scale(10));
//
//        scene.geometries.add(t7);
//
//        //---------------------------
//        //Island - Sand
//        //---------------------------
//        Geometry t8 = new Triangle(p13,p14,p15)
//                .setMaterial(material).setEmission(new Color(yellow));
//        scene.geometries.add(t8);
//
//        //---------------------------
//        //Island - Tree
//        //---------------------------
//        Geometry t9 = new Triangle(p16,p17,p18)
//                .setMaterial(material).setEmission(new Color(150,75,0));
//        scene.geometries.add(t9);
//
//
//        //finish
//        camera.setImageWriter(new ImageWriter("sunSetScene", 500, 500)) //
//                .build() //
//                .renderImage() //
//                .writeToImage(); //
//    }

}