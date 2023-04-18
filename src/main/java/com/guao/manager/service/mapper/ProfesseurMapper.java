package com.guao.manager.service.mapper;

import com.guao.manager.domain.Professeur;
import com.guao.manager.domain.User;
import com.guao.manager.service.dto.ProfesseurDTO;
import com.guao.manager.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Professeur} and its DTO {@link ProfesseurDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProfesseurMapper extends EntityMapper<ProfesseurDTO, Professeur> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    ProfesseurDTO toDto(Professeur s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
