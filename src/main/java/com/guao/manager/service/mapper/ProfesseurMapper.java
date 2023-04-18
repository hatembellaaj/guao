package com.guao.manager.service.mapper;

import com.guao.manager.domain.Professeur;
import com.guao.manager.service.dto.ProfesseurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Professeur} and its DTO {@link ProfesseurDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProfesseurMapper extends EntityMapper<ProfesseurDTO, Professeur> {}
