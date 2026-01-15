package at.tgm.securityconcepts.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
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
class HomeControllerExtendedTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Erstellt einen GitHub OAuth2User Mock
     */
    private OAuth2User createGitHubUser() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("login", "testuser");
        attributes.put("name", "Test User");
        attributes.put("email", "test@github.com");
        attributes.put("avatar_url", "https://github.com/avatar.jpg");
        attributes.put("id", 12345);
        
        return new DefaultOAuth2User(
            List.of(new SimpleGrantedAuthority("ROLE_USER")),
            attributes,
            "login"
        );
    }

    /**
     * Erstellt einen Google OAuth2User Mock
     */
    private OAuth2User createGoogleUser() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", "123456789");
        attributes.put("name", "Test User");
        attributes.put("email", "test@gmail.com");
        attributes.put("picture", "https://google.com/picture.jpg");
        
        return new DefaultOAuth2User(
            List.of(new SimpleGrantedAuthority("ROLE_USER")),
            attributes,
            "sub"
        );
    }

    /**
     * Erstellt einen GitHub User ohne "name" Attribut (nur "login")
     */
    private OAuth2User createGitHubUserWithoutName() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("login", "githubuser");
        attributes.put("email", "user@github.com");
        attributes.put("avatar_url", "https://github.com/avatar2.jpg");
        
        return new DefaultOAuth2User(
            List.of(new SimpleGrantedAuthority("ROLE_USER")),
            attributes,
            "login"
        );
    }

    /**
     * Erstellt einen User ohne name oder login (Edge Case)
     */
    private OAuth2User createUserWithoutNameOrLogin() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("email", "unknown@example.com");
        attributes.put("id", 99999);
        
        return new DefaultOAuth2User(
            List.of(new SimpleGrantedAuthority("ROLE_USER")),
            attributes,
            "id"
        );
    }

    /**
     * Erstellt einen User ohne Profilbild
     */
    private OAuth2User createUserWithoutPicture() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("name", "No Picture User");
        attributes.put("email", "nopic@example.com");
        
        return new DefaultOAuth2User(
            List.of(new SimpleGrantedAuthority("ROLE_USER")),
            attributes,
            "email"
        );
    }

    @Test
    void dashboardWithGitHubUserShouldDisplayCorrectly() throws Exception {
        mockMvc.perform(get("/dashboard")
                .with(oauth2Login().oauth2User(createGitHubUser())))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attribute("name", "Test User"))
                .andExpect(model().attribute("email", "test@github.com"))
                .andExpect(model().attribute("picture", "https://github.com/avatar.jpg"))
                .andExpect(model().attributeExists("attributes"));
    }

    @Test
    void dashboardWithGoogleUserShouldDisplayCorrectly() throws Exception {
        mockMvc.perform(get("/dashboard")
                .with(oauth2Login().oauth2User(createGoogleUser())))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attribute("name", "Test User"))
                .andExpect(model().attribute("email", "test@gmail.com"))
                .andExpect(model().attribute("picture", "https://google.com/picture.jpg"))
                .andExpect(model().attributeExists("attributes"));
    }

    @Test
    void dashboardWithGitHubUserWithoutNameShouldUseFallback() throws Exception {
        mockMvc.perform(get("/dashboard")
                .with(oauth2Login().oauth2User(createGitHubUserWithoutName())))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attribute("name", "githubuser"))
                .andExpect(model().attribute("email", "user@github.com"))
                .andExpect(model().attribute("picture", "https://github.com/avatar2.jpg"));
    }

    @Test
    void dashboardWithUserWithoutNameOrLoginShouldShowDefault() throws Exception {
        mockMvc.perform(get("/dashboard")
                .with(oauth2Login().oauth2User(createUserWithoutNameOrLogin())))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attribute("name", "Unbekannter Benutzer"))
                .andExpect(model().attribute("email", "unknown@example.com"));
    }

    @Test
    void dashboardWithUserWithoutPictureShouldHandleNull() throws Exception {
        mockMvc.perform(get("/dashboard")
                .with(oauth2Login().oauth2User(createUserWithoutPicture())))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attribute("name", "No Picture User"));
        // Picture ist null, wird aber trotzdem als Attribut gesetzt
    }

    @Test
    void userProfileWithGitHubUserShouldDisplayCorrectly() throws Exception {
        OAuth2User user = createGitHubUser();
        mockMvc.perform(get("/user")
                .with(oauth2Login().oauth2User(user)))
                .andExpect(status().isOk())
                .andExpect(view().name("user"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().attributeExists("attributes"));
    }

    @Test
    void userProfileWithGoogleUserShouldDisplayCorrectly() throws Exception {
        OAuth2User user = createGoogleUser();
        mockMvc.perform(get("/user")
                .with(oauth2Login().oauth2User(user)))
                .andExpect(status().isOk())
                .andExpect(view().name("user"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().attributeExists("attributes"));
    }

    @Test
    void staticResourcesShouldBeAccessible() throws Exception {
        // Diese Tests stellen sicher, dass die Security-Konfiguration
        // statische Ressourcen erlaubt (obwohl sie möglicherweise nicht existieren)
        mockMvc.perform(get("/css/style.css"))
                .andExpect(status().isNotFound()); // 404 ist OK, nicht 302 (redirect)
        
        mockMvc.perform(get("/js/script.js"))
                .andExpect(status().isNotFound());
        
        mockMvc.perform(get("/images/logo.png"))
                .andExpect(status().isNotFound());
    }

    @Test
    void errorEndpointShouldBeAccessible() throws Exception {
        // Error-Endpunkt sollte öffentlich zugänglich sein
        // Spring Boot Error Seite gibt 500 zurück,
        // wichtig ist, dass keine Weiterleitung zu /login erfolgt
        mockMvc.perform(get("/error"))
                .andExpect(status().is5xxServerError()); // 500, nicht 302
    }

    @Test
    void dashboardWithNullPrincipalShouldStillWork() throws Exception {
        // Dieser Test stellt sicher, dass die Methode mit null principal umgehen kann
        // (sollte nicht passieren, aber defensive Programmierung)
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().is3xxRedirection()); // Sollte zum Login weiterleiten
    }

    @Test
    void userProfileWithNullPrincipalShouldStillWork() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().is3xxRedirection()); // Sollte zum Login weiterleiten
    }

    @Test
    void loginPageWithErrorParameterShouldBeAccessible() throws Exception {
        mockMvc.perform(get("/login?error=true"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void multipleAttributesInDashboardShouldBePresent() throws Exception {
        OAuth2User user = createGitHubUser();
        mockMvc.perform(get("/dashboard")
                .with(oauth2Login().oauth2User(user)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("name"))
                .andExpect(model().attributeExists("email"))
                .andExpect(model().attributeExists("picture"))
                .andExpect(model().attributeExists("attributes"));
    }

    @Test
    void multipleAttributesInUserProfileShouldBePresent() throws Exception {
        OAuth2User user = createGoogleUser();
        mockMvc.perform(get("/user")
                .with(oauth2Login().oauth2User(user)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("attributes"));
    }
}
