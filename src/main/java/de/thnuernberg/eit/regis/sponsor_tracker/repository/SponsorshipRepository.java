package de.thnuernberg.eit.regis.sponsor_tracker.repository;

import de.thnuernberg.eit.regis.sponsor_tracker.model.Sponsorship;
import org.springframework.data.jpa.repository.JpaRepository; 

public interface SponsorshipRepository extends JpaRepository<Sponsorship, Long> {
}
