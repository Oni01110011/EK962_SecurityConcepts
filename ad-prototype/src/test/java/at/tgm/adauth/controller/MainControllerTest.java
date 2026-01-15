package at.tgm.adauth.controller;

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
    "ldap.domain=test.local",
    "ldap.url=ldap://localhost:389"
})
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void homePageShouldBeAccessible() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void loginPageShouldBeAccessible() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void dashboardShouldRedirectToLoginWhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void authenticatedUserCanAccessDashboard() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void authenticatedUserCanAccessUserProfile() throws Exception {
        mockMvc.perform(get("/user/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/profile"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void authenticatedUserCanAccessWindpark() throws Exception {
        mockMvc.perform(get("/user/windpark"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/windpark"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void normalUserCannotAccessAdminDashboard() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void normalUserCannotAccessAdminUsers() throws Exception {
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void adminUserCanAccessAdminDashboard() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/dashboard"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void adminUserCanAccessAdminUsers() throws Exception {
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/users"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMINISTRATOREN"})
    void administratorenGroupCanAccessAdminArea() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().isOk());
    }
}
