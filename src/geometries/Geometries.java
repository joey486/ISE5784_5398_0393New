package geometries;

import primitives.Ray;
import java.util.List;
import java.util.LinkedList;


public class Geometries extends Intersectable{
    private final List<Intersectable> geo = new LinkedList<>();

    public Geometries() {}

    public Geometries(Intersectable... geometries){
        add(geometries);
    }

    public void add(Intersectable... geometries){
        geo.addAll(List.of(geometries));
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {

        List<GeoPoint> intersections = null;

        for (int i = 0; i< geo.size();i++){
            var GeoPoints = geo.get(i).findGeoIntersections(ray,maxDistance);
            if (GeoPoints != null){
                if (intersections == null) intersections = new LinkedList<>(GeoPoints);
                else intersections.addAll(GeoPoints);
            }
        }
        return intersections;
    }
}