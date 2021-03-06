package at.jku.cis.radar.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@NamedQueries({
        @NamedQuery(name = GeometryEvolutionEntry.FIND_EVOLUTIONS_BY_FEATURE_GROUP, query = "SELECT ge FROM GeometryEvolutionEntry ge where ge.featureEntry.featureGroup = :featureGroup AND ge.date BETWEEN :fromDate AND :toDate ORDER By ge.date"),
        @NamedQuery(name = GeometryEvolutionEntry.FIND_EVOLUTIONS_BY_EVENT, query = "SELECT ge FROM GeometryEvolutionEntry ge WHERE ge.featureEntry.event.id = :eventId AND ge.date BETWEEN :fromDate AND :toDate order by ge.date") })
public class GeometryEvolutionEntry extends BaseEntity {
    public static final String FIND_EVOLUTIONS_BY_FEATURE_GROUP = "GeometryEvolutionEntry.findEvolutionsByFeatureGroup";
    public static final String FIND_EVOLUTIONS_BY_EVENT = "GeometryEvolutionEntry.findEvolutionsByEvent";

    @ManyToOne(fetch = FetchType.EAGER)
    private FeatureEntry featureEntry;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @OneToOne
    private Account account;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "geometryEvolutionEntry", cascade = CascadeType.ALL)
    private List<GeometryEntry> geometryEntries = new ArrayList<>();

    public FeatureEntry getFeatureEntry() {
        return featureEntry;
    }

    public void setFeatureEntry(FeatureEntry featureEntry) {
        this.featureEntry = featureEntry;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<GeometryEntry> getGeometryEntries() {
        return geometryEntries;
    }

    public void setGeometryEntries(List<GeometryEntry> geometryEntries) {
        this.geometryEntries = geometryEntries;
    }
}
