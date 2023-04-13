package com.guao.manager.service.mapper;

import com.guao.manager.domain.Classe;
import com.guao.manager.domain.Inscrption;
import com.guao.manager.service.dto.ClasseDTO;
import com.guao.manager.service.dto.InscrptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Inscrption} and its DTO {@link InscrptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface InscrptionMapper extends EntityMapper<InscrptionDTO, Inscrption> {
    @Mapping(target = "classe", source = "classe", qualifiedByName = "classeId")
    InscrptionDTO toDto(Inscrption s);

    @Named("classeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClasseDTO toDtoClasseId(Classe classe);
}
