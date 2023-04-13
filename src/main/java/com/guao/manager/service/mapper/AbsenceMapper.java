package com.guao.manager.service.mapper;

import com.guao.manager.domain.Absence;
import com.guao.manager.domain.Eleve;
import com.guao.manager.service.dto.AbsenceDTO;
import com.guao.manager.service.dto.EleveDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Absence} and its DTO {@link AbsenceDTO}.
 */
@Mapper(componentModel = "spring")
public interface AbsenceMapper extends EntityMapper<AbsenceDTO, Absence> {
    @Mapping(target = "eleve", source = "eleve", qualifiedByName = "eleveId")
    AbsenceDTO toDto(Absence s);

    @Named("eleveId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EleveDTO toDtoEleveId(Eleve eleve);
}
