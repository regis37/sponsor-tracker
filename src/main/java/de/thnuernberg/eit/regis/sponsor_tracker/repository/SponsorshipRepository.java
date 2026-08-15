package de.thnuernberg.eit.regis.sponsor_tracker.repository;

import de.thnuernberg.eit.regis.sponsor_tracker.model.Sponsorship;
import org.springframework.data.jpa.repository.JpaRepository; 

import java.util.List;

public interface SponsorshipRepository extends JpaRepository<Sponsorship, Long> {

    List<Sponsorship>findByEventId(Long eventId);
}
