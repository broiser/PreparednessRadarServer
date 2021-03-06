package at.jku.cis.radar.service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import at.jku.cis.radar.dao.EventDao;
import at.jku.cis.radar.model.Event;

@ApplicationScoped
public class EventService implements Serializable {

    @Inject
    private EventDao eventDao;

    public Event findById(long id) {
        return eventDao.findById(id);
    }

    public List<Event> findAll() {
        return eventDao.findAll();
    }
}
