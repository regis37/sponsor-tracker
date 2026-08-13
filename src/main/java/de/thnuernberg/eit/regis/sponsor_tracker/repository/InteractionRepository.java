package de.thnuernberg.eit.regis.sponsor_tracker.repository;

import de.thnuernberg.eit.regis.sponsor_tracker.model.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {
}
