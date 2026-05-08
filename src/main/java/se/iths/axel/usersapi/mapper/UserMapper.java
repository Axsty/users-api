package se.iths.axel.usersapi.mapper;

import se.iths.axel.usersapi.dto.AppUserRequestDTO;
import se.iths.axel.usersapi.dto.AppUserResponseDTO;
import se.iths.axel.usersapi.model.AppUser;

public interface UserMapper {
    AppUser toEntity(AppUserRequestDTO dto);

    AppUserResponseDTO toResponseDTO(AppUser appUser);

    void updateEntity(AppUserRequestDTO dto, AppUser appUser);

}
