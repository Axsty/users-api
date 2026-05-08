package se.iths.axel.usersapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AppUserRequestDTO(
        @NotBlank(message = "Username is required")
        @Email
        String username,
        @NotBlank(message = "Password is required")
        String password
) {
}
