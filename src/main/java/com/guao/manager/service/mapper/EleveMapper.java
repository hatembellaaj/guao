package com.guao.manager.service.mapper;

import com.guao.manager.domain.Classe;
import com.guao.manager.domain.Eleve;
import com.guao.manager.service.dto.ClasseDTO;
import com.guao.manager.service.dto.EleveDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Eleve} and its DTO {@link EleveDTO}.
 */
@Mapper(componentModel = "spring")
public interface EleveMapper extends EntityMapper<EleveDTO, Eleve> {
    @Mapping(target = "classe", source = "classe", qualifiedByName = "classeId")
    EleveDTO toDto(Eleve s);

    @Named("classeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClasseDTO toDtoClasseId(Classe classe);
}
