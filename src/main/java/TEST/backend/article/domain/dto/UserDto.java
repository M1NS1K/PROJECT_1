package TEST.backend.article.domain.dto;

import TEST.backend.article.constant.RoleType;
import TEST.backend.article.constant.UserStatus;
import java.time.LocalDateTime;

public record UserDto(
        Long userId,
        String loginId,
        String username,
        String password,
        UserStatus status,
        String email,
        RoleType roleType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    // factory method of 선언
    public static UserDto of(Long userId, String loginId, String username, String password, UserStatus status, String email, RoleType roleType, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new UserDto(userId, loginId, username, password, status, email, roleType, createdAt, updatedAt);
    }

    // security에서 사용할 팩토리 메서드
    public static UserDto of(String loginId) {
        return new UserDto(
                null, loginId, null, null, null, null, null, null, null
        );
    }
}
