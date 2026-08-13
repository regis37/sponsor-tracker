package de.thnuernberg.eit.regis.sponsor_tracker.repository;

import de.thnuernberg.eit.regis.sponsor_tracker.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}