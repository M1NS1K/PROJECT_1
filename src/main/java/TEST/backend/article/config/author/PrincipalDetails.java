package TEST.backend.article.config.author;

import TEST.backend.article.domain.entity.User;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public record PrincipalDetails(
        User user,
        Map<String, Object> attributes,
        String attributeKey
) implements OAuth2User, UserDetails {

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getUserKey();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * getRole 부분 확인
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()));
    }

    @Override
    public String getName() {
        return attributes.get(attributeKey).toString();
    }
}
