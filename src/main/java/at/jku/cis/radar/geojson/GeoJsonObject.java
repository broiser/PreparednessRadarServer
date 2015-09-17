package at.jku.cis.radar.geojson;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

import at.jku.cis.radar.model.Feature;

@JsonSubTypes(@Type(Feature.class))
@JsonTypeInfo(property = GeoJsonObject.TYPE, use = Id.NAME)
public interface GeoJsonObject extends Serializable {

    static final String TYPE = "type";
    static final String COORDINATES = "coordinates";
    static final String GEOMETRIES = "geometries";

}