package se.iths.axel.usersapi.mapper;

import org.springframework.stereotype.Component;
import se.iths.axel.usersapi.dto.AppUserRequestDTO;
import se.iths.axel.usersapi.dto.AppUserResponseDTO;
import se.iths.axel.usersapi.model.AppUser;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public AppUser toEntity(AppUserRequestDTO dto) {
        AppUser appUser = new AppUser();
        appUser.setUsername(dto.username());
        appUser.setPassword(dto.password());
        return appUser;
    }

    @Override
    public AppUserResponseDTO toResponseDTO(AppUser appUser) {
        return new AppUserResponseDTO(
                appUser.getId(),
                appUser.getUsername());
    }

    @Override
    public void updateEntity(AppUserRequestDTO dto, AppUser appUser) {
        appUser.setUsername(dto.username());
        appUser.setPassword(dto.password());
    }
}
