package TEST.backend.article.domain.dto;

import TEST.backend.article.constant.Role;
import TEST.backend.article.domain.entity.User;
import jakarta.security.auth.message.AuthException;
import lombok.Builder;

import javax.crypto.KeyGenerator;
import java.util.Map;
import java.util.UUID;

@Builder
public record UserInfo(
        String name,
        String email,
        String profile
) {
    public static UserInfo of(String registrationId, Map<String, Object> attributes) {
		return switch (registrationId) {
			case "google" -> ofGoogle(attributes);
			case "kakao" -> ofKakao(attributes);
			default -> throw new AuthException();
		};
    }

	private static UserInfo ofGoogle(Map<String, Object> attributes) {
		return UserInfo.builder()
				.name((String) attributes.get("name"))
				.email((String) attributes.get("email"))
				.profile((String) attributes.get("picture"))
				.build();
	}

	private static UserInfo ofKakao(Map<String, Object> attributes) {
		Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
		Map<String, Object> profile = (Map<String, Object>) account.get("profile");

		return UserInfo.builder()
				.name((String) profile.get("nickname"))
				.email((String) account.get("email"))
				.profile((String) profile.get("profile_image_url"))
				.build();
	}

	public User toEntity() {
		return User.builder()
				.username(name)
				.email(email)
				.profile(profile)
				.userKey(UUID.randomUUID())
				.role(Role.USER)
				.build();
	}
}

