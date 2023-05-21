package com.guao.manager.repository;

import com.guao.manager.domain.Absence;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Absence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    @Query("select count(a) FROM com.guao.manager.domain.Absence a where a.justifie = true ")
    Long countAbscenceJustifiee();
}
