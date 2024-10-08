package rat2race.login.global.auth.dto;

import java.util.Map;

public record CustomOAuth2UserInfo(
        String name,
        String email,
        String profile
) {

    public static CustomOAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        return switch(registrationId) {
            case "google" -> ofGoogle
        }
    }
}
