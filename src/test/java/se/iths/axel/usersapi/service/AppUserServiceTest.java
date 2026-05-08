package se.iths.axel.usersapi.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import se.iths.axel.usersapi.dto.AppUserRequestDTO;
import se.iths.axel.usersapi.dto.AppUserResponseDTO;
import se.iths.axel.usersapi.mapper.UserMapperImpl;
import se.iths.axel.usersapi.model.AppUser;
import se.iths.axel.usersapi.repository.AppUserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceTest {

    @Mock
    AppUserRepository appUserRepository;

    @Mock
    UserMapperImpl mapper;

    @InjectMocks
    AppUserService appUserService;

    @Test
    @DisplayName("Should return list of users")
    void findAll() {
        List<AppUser> userList = List.of(
                new AppUser(1L, "axel", "pass", "USER"),
                new AppUser(2L, "marie", "lösen", "ADMIN")
        );

        Mockito.when(appUserRepository.findAll())
                .thenReturn(userList);

        Mockito.when(mapper.toResponseDTO(userList.get(0)))
                .thenReturn(new AppUserResponseDTO(1L, "axel"));

        Mockito.when(mapper.toResponseDTO(userList.get(1)))
                .thenReturn(new AppUserResponseDTO(2L, "marie"));

        List<AppUserResponseDTO> result = appUserService.findAll();

        assertEquals("axel", result.getFirst().username());
        assertEquals("marie", result.getLast().username());
        Mockito.verify(appUserRepository).findAll();
    }

    @Test
    @DisplayName("Should return user with id 1")
    public void findById() {
        AppUser appUser = new AppUser(1L, "klara", "pw", "USER");

        Mockito.when(appUserRepository.findById(1L))
                .thenReturn(Optional.of(appUser));

        Mockito.when(mapper.toResponseDTO(appUser))
                .thenReturn(new AppUserResponseDTO(1L, "klara"));

        AppUserResponseDTO result = appUserService.findById(1L);

        assertEquals("klara", result.username());
        Mockito.verify(appUserRepository).findById(1L);
    }

    @Test
    @DisplayName("Should save created user to database")
    public void createUser() {
        AppUserRequestDTO requestDTO = new AppUserRequestDTO("lina", "password");
        AppUser appUser = new AppUser(1L, "lina", "password", "USER");

        Mockito.when(mapper.toEntity(requestDTO))
                .thenReturn(appUser);

        Mockito.when(appUserRepository.save(appUser))
                .thenReturn(appUser);

        Mockito.when(mapper.toResponseDTO(appUser))
                .thenReturn(new AppUserResponseDTO(1L, "lina"));

        AppUserResponseDTO savedUser = appUserService.createUser(requestDTO);

        assertEquals("lina", savedUser.username());
        Mockito.verify(appUserRepository).save(appUser);
    }

    @Test
    @DisplayName("Should update users name and password")
    public void updateUser() {
        AppUserRequestDTO requestDTO = new AppUserRequestDTO("axel", "korv");
        AppUser existingUser = new AppUser(1L, "peter", "password", "USER");

        Mockito.when(appUserRepository.findById(1L))
                .thenReturn(Optional.of(existingUser));

        Mockito.doAnswer(invocation -> {
            AppUserRequestDTO dto = invocation.getArgument(0);
            AppUser user = invocation.getArgument(1);
            user.setUsername(dto.username());
            user.setPassword(dto.password());
            return null;
        }).when(mapper).updateEntity(requestDTO, existingUser);

        Mockito.when(appUserRepository.save(existingUser))
                .thenReturn(existingUser);

        Mockito.when(mapper.toResponseDTO(existingUser))
                .thenReturn(new AppUserResponseDTO(1L, "axel"));

        AppUserResponseDTO result = appUserService.updateUser(requestDTO, 1L);

        assertEquals("axel", result.username());
        Mockito.verify(appUserRepository).save(existingUser);
    }

    @Test
    @DisplayName("Should delete user")
    public void deleteUser() {
        Mockito.when(appUserRepository.existsById(2L))
                .thenReturn(true);

        appUserService.deleteUser(2L);

        Mockito.verify(appUserRepository).deleteById(2L);
    }
}
