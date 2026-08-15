package de.thnuernberg.eit.regis.sponsor_tracker.repository;

import de.thnuernberg.eit.regis.sponsor_tracker.model.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface InteractionRepository extends JpaRepository<Interaction, Long> {

        List<Interaction> findByCompanyIdOrderByDateDesc(Long companyId);
        List<Interaction> findByNextActionDateLessThanEqualOrderByNextActionDateAsc(LocalDate date);

}
