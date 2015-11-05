package at.jku.cis.radar.transformer;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.collections4.Transformer;

import at.jku.cis.radar.builder.FeatureBuilder;
import at.jku.cis.radar.geojson.Feature;
import at.jku.cis.radar.model.FeatureEvolution;

@ApplicationScoped
public class FeatureEvolution2FeatureTransformer implements Transformer<FeatureEvolution, Feature> {

    @Override
    public Feature transform(FeatureEvolution featureEvolution) {
        FeatureBuilder featureBuilder = new FeatureBuilder();
        featureBuilder = featureBuilder.withGeometry(featureEvolution.getGeometry());
        featureBuilder = featureBuilder.withFeatureGroup(featureEvolution.getFeatureGroup());
        featureBuilder = featureBuilder.withProperties(featureEvolution.getProperties());
        return featureBuilder.build();
    }
}