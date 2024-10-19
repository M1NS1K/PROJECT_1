package TEST.backend.domain.dto;

public record JwtResponseDto(
        String accessToken,
        String refreshToken
) {
}
