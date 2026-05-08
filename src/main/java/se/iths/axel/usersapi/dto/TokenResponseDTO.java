package se.iths.axel.usersapi.dto;

import java.util.List;

public record TokenResponseDTO(
        String accessToken,
        long expiresIn,
        String subject,
        List<String> roles
) {
}
