package at.tgm.securityconcepts.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.security.oauth2.client.registration.github.client-id=test-id",
    "spring.security.oauth2.client.registration.github.client-secret=test-secret",
    "spring.security.oauth2.client.registration.google.client-id=test-id",
    "spring.security.oauth2.client.registration.google.client-secret=test-secret"
})
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void publicEndpointsShouldBeAccessible() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    void protectedEndpointsShouldRequireAuthentication() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "testuser")
    void authenticatedUserCanAccessDashboard() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void authenticatedUserCanAccessUserPage() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(view().name("user"));
    }

    @Test
    void oauth2LoginEndpointExists() throws Exception {
        mockMvc.perform(get("/oauth2/authorization/github"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/oauth2/authorization/google"))
                .andExpect(status().is3xxRedirection());
    }
}
