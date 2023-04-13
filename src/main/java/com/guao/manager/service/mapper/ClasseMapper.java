package com.guao.manager.service.mapper;

import com.guao.manager.domain.Classe;
import com.guao.manager.service.dto.ClasseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Classe} and its DTO {@link ClasseDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClasseMapper extends EntityMapper<ClasseDTO, Classe> {}
