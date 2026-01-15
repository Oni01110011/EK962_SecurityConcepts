package at.tgm.adauth.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "ldap.domain=test.local",
    "ldap.url=ldap://localhost:389"
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

        mockMvc.perform(get("/user/profile"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void roleBasedAccessControlForUserRole() throws Exception {
        // User can access user area
        mockMvc.perform(get("/user/profile"))
                .andExpect(status().isOk());

        // User cannot access admin area
        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void roleBasedAccessControlForAdminRole() throws Exception {
        // Admin can access user area
        mockMvc.perform(get("/user/profile"))
                .andExpect(status().isOk());

        // Admin can access admin area
        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser")
    void logoutShouldWork() throws Exception {
        mockMvc.perform(post("/logout").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout=true"));
    }

    @Test
    void loginFormShouldAcceptCredentials() throws Exception {
        // Test that login form accepts POST requests
        mockMvc.perform(post("/login")
                .param("username", "testuser")
                .param("password", "testpass")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
