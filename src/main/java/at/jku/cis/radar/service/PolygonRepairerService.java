package at.jku.cis.radar.service;

import static com.vividsolutions.jts.operation.valid.TopologyValidationError.DISCONNECTED_INTERIOR;
import static com.vividsolutions.jts.operation.valid.TopologyValidationError.RING_SELF_INTERSECTION;
import static com.vividsolutions.jts.operation.valid.TopologyValidationError.SELF_INTERSECTION;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.operation.polygonize.Polygonizer;
import com.vividsolutions.jts.operation.valid.IsValidOp;
import com.vividsolutions.jts.operation.valid.TopologyValidationError;

@ApplicationScoped
public class PolygonRepairerService implements Serializable {

    public List<Polygon> repair(MultiPolygon multiPolygon) {
        ArrayList<Polygon> polygons = new ArrayList<>();
        for (int i = 0; i < multiPolygon.getNumGeometries(); i++) {
            polygons.addAll(repair((Polygon) multiPolygon.getGeometryN(i)));
        }
        return polygons;
    }

    public List<Polygon> repair(Polygon polygon) {
        if (!isSelfIntersectingPolygon(polygon)) {
            return Arrays.asList(polygon);
        }
        Geometry boundary = polygon.getBoundary();
        boundary = boundary.union(boundary);
        Polygonizer polygonizer = new Polygonizer();
        polygonizer.add(boundary);
        List<Polygon> newPolygons = new ArrayList<>();
        for (Object newPolygon : polygonizer.getPolygons()) {
            if (newPolygon != null) {
                newPolygons.addAll(repair((Polygon) newPolygon));
            }
        }
        return newPolygons;
    }

    private boolean isSelfIntersectingPolygon(Polygon polygon) {
        TopologyValidationError topologyValidationError = new IsValidOp(polygon).getValidationError();
        return topologyValidationError != null && isSelfIntersectionErrorType(topologyValidationError);
    }

    private boolean isSelfIntersectionErrorType(TopologyValidationError topologyValidationError) {
        int errorType = topologyValidationError.getErrorType();
        return SELF_INTERSECTION == errorType || RING_SELF_INTERSECTION == errorType
                || DISCONNECTED_INTERIOR == errorType;
    }
}