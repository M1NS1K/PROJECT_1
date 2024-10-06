package rat2race.login.domain.user.entity;

import java.io.Serializable;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

@Getter
public class UserPrincipal extends User implements Serializable {

    private final UserDetailsImpl user;

    public UserPrincipal(UserDetailsImpl user) {
        super(user.getUsername(), user.getPassword(), user.getAuthorities());
        this.user = user;
    }
}
