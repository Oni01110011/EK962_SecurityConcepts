package at.tgm.securityconcepts.config;

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
    "spring.security.oauth2.client.registration.github.client-id=test-id",
    "spring.security.oauth2.client.registration.github.client-secret=test-secret",
    "spring.security.oauth2.client.registration.google.client-id=test-id",
    "spring.security.oauth2.client.registration.google.client-secret=test-secret"
})
class SecurityConfigExtendedTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "testuser")
    void logoutShouldInvalidateSessionAndRedirectToHome() throws Exception {
        mockMvc.perform(post("/logout").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void logoutWithoutAuthenticationShouldStillWork() throws Exception {
        mockMvc.perform(post("/logout").with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void rootPathShouldBePubliclyAccessible() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    void loginPathShouldBePubliclyAccessible() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    void errorPathShouldBePubliclyAccessible() throws Exception {
        // Error-Endpunkt ist öffentlich, gibt 500 zurück (keine Weiterleitung)
        mockMvc.perform(get("/error"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void cssResourcesShouldBePubliclyAccessible() throws Exception {
        // CSS-Pfade sollten nicht zur Login-Seite weiterleiten
        mockMvc.perform(get("/css/bootstrap.min.css"))
                .andExpect(status().isNotFound()); // 404, nicht 302
    }

    @Test
    void jsResourcesShouldBePubliclyAccessible() throws Exception {
        mockMvc.perform(get("/js/main.js"))
                .andExpect(status().isNotFound()); // 404, nicht 302
    }

    @Test
    void imagesResourcesShouldBePubliclyAccessible() throws Exception {
        mockMvc.perform(get("/images/icon.png"))
                .andExpect(status().isNotFound()); // 404, nicht 302
    }

    @Test
    void dashboardShouldRequireAuthentication() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    void userProfileShouldRequireAuthentication() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void authenticatedUserShouldAccessDashboard() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void authenticatedUserShouldAccessUserProfile() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk());
    }

    @Test
    void oauth2AuthorizationEndpointForGitHubShouldExist() throws Exception {
        mockMvc.perform(get("/oauth2/authorization/github"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void oauth2AuthorizationEndpointForGoogleShouldExist() throws Exception {
        mockMvc.perform(get("/oauth2/authorization/google"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void loginWithErrorParameterShouldBeAccessible() throws Exception {
        mockMvc.perform(get("/login?error=true"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void loginWithLogoutParameterShouldBeAccessible() throws Exception {
        mockMvc.perform(get("/login?logout"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void nonExistentProtectedPathShouldRequireAuthentication() throws Exception {
        mockMvc.perform(get("/protected/nonexistent"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void authenticatedUserCanAccessNonExistentProtectedPath() throws Exception {
        mockMvc.perform(get("/protected/nonexistent"))
                .andExpect(status().isNotFound()); // 404, nicht 302
    }

    @Test
    void multiplePublicResourcesCanBeAccessedSimultaneously() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
        // Error-Endpunkt ist öffentlich, gibt aber nicht immer 200 zurück
    }

    @Test
    @WithMockUser(username = "user1")
    void differentAuthenticatedUsersShouldAccessSameResource() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user2")
    void anotherAuthenticatedUserShouldAccessDashboard() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk());
    }

    @Test
    void nestedCssPathsShouldBeAccessible() throws Exception {
        mockMvc.perform(get("/css/vendor/bootstrap.min.css"))
                .andExpect(status().isNotFound()); // Keine Weiterleitung zur Login-Seite
    }

    @Test
    void nestedJsPathsShouldBeAccessible() throws Exception {
        mockMvc.perform(get("/js/lib/jquery.min.js"))
                .andExpect(status().isNotFound());
    }

    @Test
    void nestedImagesPathsShouldBeAccessible() throws Exception {
        mockMvc.perform(get("/images/icons/favicon.ico"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testuser", authorities = {"SCOPE_read", "ROLE_USER"})
    void authenticatedUserWithScopesShouldAccessProtectedResources() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk());
    }
}
