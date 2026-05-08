package se.iths.axel.usersapi.service;

import org.jspecify.annotations.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.axel.usersapi.dto.AppUserRequestDTO;
import se.iths.axel.usersapi.dto.AppUserResponseDTO;
import se.iths.axel.usersapi.exception.UserNotFoundException;
import se.iths.axel.usersapi.mapper.UserMapperImpl;
import se.iths.axel.usersapi.model.AppUser;
import se.iths.axel.usersapi.repository.AppUserRepository;

import java.util.List;

@Service
public class AppUserService {

    private final AppUserRepository repository;
    private final UserMapperImpl mapper;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository repository, UserMapperImpl mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<AppUserResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public AppUserResponseDTO findById(Long id) {
        return mapper.toResponseDTO(getAppUser(id));
    }

    public AppUserResponseDTO createUser(AppUserRequestDTO appUserRequestDTO) {

        AppUser appUser = mapper.toEntity(appUserRequestDTO);
        appUser.setRole("USER");
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        AppUser saved = repository.save(appUser);

        return mapper.toResponseDTO(saved);
    }

    public AppUserResponseDTO updateUser(AppUserRequestDTO appUserRequestDTO, Long id) {
        AppUser appUser = getAppUser(id);
        mapper.updateEntity(appUserRequestDTO, appUser);
        AppUser saved = repository.save(appUser);

        return mapper.toResponseDTO(saved);
    }

    public void deleteUser(Long id) {
        if (!repository.existsById(id)) {
            throw new UserNotFoundException(id);
        } else {
            repository.deleteById(id);
        }
    }

    private @NonNull AppUser getAppUser(Long id) {
        AppUser appUser = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return appUser;
    }
}
