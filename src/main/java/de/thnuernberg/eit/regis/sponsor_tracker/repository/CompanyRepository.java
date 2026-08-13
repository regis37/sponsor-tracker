package de.thnuernberg.eit.regis.sponsor_tracker.repository;

import de.thnuernberg.eit.regis.sponsor_tracker.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}