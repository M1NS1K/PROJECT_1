package TEST.backend.security.config;

import TEST.backend.security.handler.FormAuthenticationFailureHandler;
import TEST.backend.security.handler.FormAuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetails;


@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationProvider authenticationProvider;
	private final AuthenticationProvider restAuthenticationProvider;
	private final AuthenticationProvider<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;
	private final FormAuthenticationSuccessHandler successHandler;
	private final FormAuthenticationFailureHandler failureHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
						.authorizeHttpRequests(auth -> auth
										.requestMatchers("/css/**", "images/**", "/js/**", "/favicon.*", "/*/icon-*").permitAll()
										.requestMatchers("/", "/signup", "/login*").permitAll()
										.requestMatchers("/user").hasAuthority("ROLE_USER")
										.requestMatchers("/manager").hasAuthority("ROLE_MANAGER")
										.anyRequest().authenticated())
						.formLogin(form -> form
										.loginPage("/login")
										.authenticationDetailsSource(authenticationDetailsSource)
										.successHandler(successHandler)
										.failureHandler(failureHandler)
										.permitAll())
						.authenticationProvider(authenticationProvider)
						.exceptionHandling(exception -> exception
										.accessDeniedHandler(null));

		return http.build();

	}
}
