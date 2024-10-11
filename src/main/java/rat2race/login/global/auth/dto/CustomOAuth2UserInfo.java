package rat2race.login.global.auth.dto;

import static rat2race.login.global.common.exception.ErrorCode.ILLEGAL_REGISTRATION_ID;

import java.util.Map;
import lombok.Builder;
import rat2race.login.domain.user.entity.Role;
import rat2race.login.domain.user.entity.User;
import rat2race.login.global.common.exception.AuthException;

@Builder
public record CustomOAuth2UserInfo(
        String name,
        String email,
        String profile
) {

    public static CustomOAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        return switch(registrationId) {
            case "google" -> ofGoogle(attributes);
            case "kakao" -> ofKakao(attributes);
            default -> throw new AuthException(ILLEGAL_REGISTRATION_ID);
        };
    }

    public static CustomOAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
        return CustomOAuth2UserInfo.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profile((String) attributes.get("picture"))
                .build();
    }

    public static CustomOAuth2UserInfo ofKakao(Map<String, Object> attributes) {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        return CustomOAuth2UserInfo.builder()
                .name((String) profile.get("nickname"))
                .email((String) account.get("email"))
                .profile((String) profile.get("profile_image_url"))
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .profile(profile)
                .userKey("")
                .role(Role.USER)
                .build();
    }
}
