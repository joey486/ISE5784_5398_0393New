package renderer;

import geometries.Plane;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;
import static primitives.Util.random;

public class Blackboard {
    private double k = 1;
    public double width;
    public double height;
    public List<Point> grid = null;
    boolean useSoftShadows = true;

    public Blackboard(double k,double width, double height) {
        this.width = width;
        this.height = height;
        this.k = k;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public void setGrid(Point pCenter, Vector vUp, Vector vRight) {
        if (grid != null) return;
        grid = new LinkedList<>();
        if (k == 1 || !useSoftShadows)                                                    //
            grid.add(pCenter);
        else {
            //Ratio
            double Ry = (height-1) / k;
            double Rx = (width-1) / k;

            for (int i = 0; i < k; i++) {
                for (int j = 0; j < k; j++) {
                    double yI = -(i - (double) (k - 1) / 2) * Ry ;  //- Ry / 2
                    double xJ = (j - (double) (k - 1) / 2) * Rx ;   //- Rx / 2
                    Point pIJ = pCenter;
                    double yMove = random(-Ry/2, Ry/2);
                    double xMove = random(-Rx/2, Rx/2);
                    if (!isZero(xJ) || !isZero(xMove))
                        pIJ = pIJ.add(vRight.scale(xJ + xMove));
                    if (!isZero(yI) || !isZero(yMove))
                        pIJ = pIJ.add(vUp.scale(yI + yMove));
                    grid.addLast(pIJ);
                }
            }
        }
    }
}
