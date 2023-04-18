package com.guao.manager.repository;

import com.guao.manager.domain.Professeur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Professeur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfesseurRepository extends JpaRepository<Professeur, Long> {}
