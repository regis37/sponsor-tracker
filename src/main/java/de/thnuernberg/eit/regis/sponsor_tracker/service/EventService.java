package de.thnuernberg.eit.regis.sponsor_tracker.service;

import de.thnuernberg.eit.regis.sponsor_tracker.model.Event;
import de.thnuernberg.eit.regis.sponsor_tracker.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class EventService {
    
    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public List<Event> findAll() {
        return repository.findAll();
    }

    public Optional<Event> findById(Long id) {
        return repository.findById(id);
    }

    public Event create(Event event) {
        return repository.save(event);
    }

    public Optional<Event> update(Long id, Event updated) {
        return repository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setDate(updated.getDate());
            existing.setTargetBudget(updated.getTargetBudget());
            existing.setDescription(updated.getDescription());
            return repository.save(existing);
        });
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

}
