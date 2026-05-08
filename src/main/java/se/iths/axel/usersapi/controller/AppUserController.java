package se.iths.axel.usersapi.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.axel.usersapi.dto.AppUserRequestDTO;
import se.iths.axel.usersapi.dto.AppUserResponseDTO;
import se.iths.axel.usersapi.service.AppUserService;

import java.util.List;

@RestController
@RequestMapping("/appusers")
public class AppUserController {

    private final AppUserService service;

    public AppUserController(AppUserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AppUserResponseDTO>> findAll() {
        List<AppUserResponseDTO> userList = service.findAll();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        AppUserResponseDTO appUserResponseDTO = service.findById(id);
        return ResponseEntity.ok(appUserResponseDTO);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody AppUserRequestDTO appUserRequestDTO) {
        AppUserResponseDTO appUserResponseDTO = service.createUser(appUserRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(appUserResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUserResponseDTO> updateUser(@Valid @RequestBody AppUserRequestDTO appUserRequestDTO, @PathVariable Long id) {
        AppUserResponseDTO appUserResponseDTO = service.updateUser(appUserRequestDTO, id);
        return ResponseEntity.ok(appUserResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
