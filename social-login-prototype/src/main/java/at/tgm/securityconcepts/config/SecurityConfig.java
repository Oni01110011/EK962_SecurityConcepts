package at.tgm.securityconcepts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security Konfiguration für OAuth2 Social Login.
 *
 * Konfiguriert:
 * - Öffentliche Endpunkte (/, /login, /error)
 * - Geschützte Endpunkte (/user/**, /dashboard/**)
 * - OAuth2 Login mit automatischer Provider-Erkennung
 * - Logout-Handling
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Konfiguriert die Security Filter Chain für OAuth2 Login.
     *
     * @param http HttpSecurity Builder
     * @return Konfigurierte SecurityFilterChain
     * @throws Exception bei Konfigurationsfehlern
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Autorisierungsregeln
                .authorizeHttpRequests(authorize -> authorize
                        // Öffentliche Endpunkte
                        .requestMatchers("/", "/login", "/error", "/css/**", "/js/**", "/images/**").permitAll()
                        // Alle anderen Endpunkte erfordern Authentifizierung
                        .anyRequest().authenticated()
                )
                // OAuth2 Login Konfiguration
                .oauth2Login(oauth2 -> oauth2
                        // Eigene Login-Seite
                        .loginPage("/login")
                        // Weiterleitung nach erfolgreichem Login
                        .defaultSuccessUrl("/dashboard", true)
                        // Fehlerbehandlung
                        .failureUrl("/login?error=true")
                )
                // Logout Konfiguration
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
}
