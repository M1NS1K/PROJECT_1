package rat2race.login.domain.user.entity;

public record OAuth2UserInfo(
        String name,
        String email,
        String profile
) {

    public static OAuth2UserInfo of()
}
