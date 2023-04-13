package com.guao.manager.repository;

import com.guao.manager.domain.Inscrption;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Inscrption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InscrptionRepository extends JpaRepository<Inscrption, Long> {}
