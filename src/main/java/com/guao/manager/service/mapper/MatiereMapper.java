package com.guao.manager.service.mapper;

import com.guao.manager.domain.Matiere;
import com.guao.manager.service.dto.MatiereDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Matiere} and its DTO {@link MatiereDTO}.
 */
@Mapper(componentModel = "spring")
public interface MatiereMapper extends EntityMapper<MatiereDTO, Matiere> {}
