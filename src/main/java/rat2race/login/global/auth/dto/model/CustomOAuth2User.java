package rat2race.login.global.auth.dto.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import rat2race.login.domain.user.entity.User;

public record CustomOAuth2User(
        User user,
        Map<String, Object> attributes,
        String attributeKey) implements OAuth2User {

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole().getKey())
        );
    }

    @Override
    public String getName() {
        return attributes.get(attributeKey).toString();
    }
}
