package TEST.backend.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("authenticationProvider")
@RequiredArgsConstructor
public class FormAuthenticationProvider implements AuthenticationProvider {
	 private final UserDetailsService userDetailsService;
	 private final PasswordEncoder passwordEncoder;

	 @Override
	 public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		 return null;
	 }

	 @Override
	 public boolean supports(Class<?> authentication) {
		 return false;
	 }
}
