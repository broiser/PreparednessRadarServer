package at.jku.cis.radar.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import at.jku.cis.radar.model.FeatureEvolution;

@ApplicationScoped
public class FeatureGroupDao extends AbstractDao<FeatureEvolution> {

    public FeatureGroupDao() {
        super(FeatureEvolution.class);
    }

    public Long findNextFeatureGroup() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<FeatureEvolution> featureEvolutionRoot = criteriaQuery.from(FeatureEvolution.class);
        Expression<Long> featureGroupExpression = featureEvolutionRoot.get("featureGroup").as(Long.class);
        criteriaQuery.select(featureGroupExpression).orderBy(criteriaBuilder.desc(featureGroupExpression));
        return getSingleResult(getEntityManager().createQuery(criteriaQuery).setMaxResults(1));
    }

}
