package com.guao.manager.service.mapper;

import com.guao.manager.domain.Classe;
import com.guao.manager.domain.Examen;
import com.guao.manager.domain.Matiere;
import com.guao.manager.service.dto.ClasseDTO;
import com.guao.manager.service.dto.ExamenDTO;
import com.guao.manager.service.dto.MatiereDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Examen} and its DTO {@link ExamenDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExamenMapper extends EntityMapper<ExamenDTO, Examen> {
    @Mapping(target = "matiere", source = "matiere", qualifiedByName = "matiereId")
    @Mapping(target = "classe", source = "classe", qualifiedByName = "classeId")
    ExamenDTO toDto(Examen s);

    @Named("matiereId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatiereDTO toDtoMatiereId(Matiere matiere);

    @Named("classeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClasseDTO toDtoClasseId(Classe classe);
}
