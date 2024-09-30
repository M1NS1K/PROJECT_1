package rat2race.security.jwt;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;

@AutoConfigureMockMvc
@SpringBootTest
class TokenAuthenticationFilterTest {
    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Controller 없이 필터만 추가
        mockMvc = MockMvcBuilders
                .standaloneSetup(tokenAuthenticationFilter)  // 필터를 추가
                .build();
    }

    @Test
    public void whenValidToken_thenAuthenticated() throws Exception {
        // Given: 유효한 토큰 설정
        String validToken = "validAccessToken";

        when(tokenProvider.validateToken(validToken)).thenReturn(true);  // 토큰이 유효하다고 설정

        // When & Then: 필터 통과 후 200 OK 반환 (필터만 테스트하는 경우)
        mockMvc.perform(get("/any-endpoint")  // 실제 엔드포인트는 필요 없음, 필터가 작동하는지 확인
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + validToken))
                .andExpect(status().isOk());
    }

    @Test
    public void whenNoToken_thenUnauthorized() throws Exception {
        // When & Then: 토큰 없이 요청한 경우 401 반환
        mockMvc.perform(get("/any-endpoint"))  // 실제 엔드포인트는 필요 없음
                .andExpect(status().isUnauthorized());
    }
}