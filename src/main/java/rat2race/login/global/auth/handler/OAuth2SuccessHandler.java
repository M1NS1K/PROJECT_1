package rat2race.login.global.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import rat2race.login.domain.user.entity.Role;
import rat2race.login.global.auth.dto.CustomOAuth2UserInfo;
import rat2race.login.global.auth.dto.model.CustomOAuth2User;
import rat2race.login.global.auth.jwt.service.TokenProvider;
import rat2race.login.global.auth.jwt.service.TokenService;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final TokenService tokenService;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private static final String URI = "/auth/success";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) oAuth2User;

        Long userId = customOAuth2User.user().getId();
        Role role = customOAuth2User.user().getRole();

        String accessToken = tokenProvider.generateAccessToken(userId, role);
        String refreshToken = tokenProvider.generateRefreshToken(userId, role);

        ResponseCookie refreshTokenCookie = tokenService.createHttpOnlyCookie(refreshToken);
        tokenService.saveOrUpdateRefreshToken(userId, refreshToken);

        response.addHeader("Set-Cookie", refreshTokenCookie.toString());
        response.setHeader("Authorization", "Bearer" + accessToken);

        redirectStrategy.sendRedirect(request, response, URI);
    }
}
