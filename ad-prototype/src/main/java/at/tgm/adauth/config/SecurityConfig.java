package at.tgm.adauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security Konfiguration fuer Active Directory Authentifizierung.
 *
 * Diese Konfiguration ermoeglicht:
 * - Authentifizierung gegen ein externes Active Directory
 * - Rollenbasierte Zugriffskontrolle basierend auf AD-Gruppen
 * - Zwei geschuetzte Bereiche: Admin und User
 *
 * AD-Gruppen werden automatisch auf Spring Security Rollen gemappt:
 * - AD-Gruppe "admins" -> ROLE_ADMIN
 * - AD-Gruppe "users" -> ROLE_USER
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Value("${ldap.domain}")
    private String ldapDomain;

    @Value("${ldap.url}")
    private String ldapUrl;

    /**
     * Konfiguriert den Active Directory Authentication Provider.
     *
     * Der Provider:
     * - Verbindet sich mit dem konfigurierten AD-Server
     * - Authentifiziert Benutzer mit ihren AD-Credentials
     * - Laedt Gruppenmitgliedschaften als Authorities
     */
    @Bean
    public ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
        ActiveDirectoryLdapAuthenticationProvider provider =
                new ActiveDirectoryLdapAuthenticationProvider(ldapDomain, ldapUrl);

        // Konvertiert AD-Gruppen zu Spring Security Authorities
        // z.B. "CN=admins,OU=Groups,DC=tgm,DC=ac,DC=at" -> "ROLE_ADMINS"
        provider.setConvertSubErrorCodesToExceptions(true);
        provider.setUseAuthenticationRequestCredentials(true);

        // Optionaler Search Filter fuer Benutzersuche
        // provider.setSearchFilter("(&(objectClass=user)(sAMAccountName={0}))");

        return provider;
    }

    /**
     * Konfiguriert den AuthenticationManager mit dem AD Provider.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.authenticationProvider(activeDirectoryLdapAuthenticationProvider());
        return authBuilder.build();
    }

    /**
     * Konfiguriert die Security Filter Chain mit rollenbasierter Zugriffskontrolle.
     *
     * Zugriffsregeln:
     * - /admin/** : Nur ROLE_ADMIN (z.B. AD-Gruppe "Administratoren" oder "admins")
     * - /user/**  : ROLE_ADMIN oder ROLE_USER
     * - /         : Oeffentlich
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Oeffentliche Ressourcen
                        .requestMatchers("/", "/login", "/error", "/css/**", "/js/**").permitAll()
                        // Admin-Bereich: Nur fuer Administratoren
                        .requestMatchers("/admin/**").hasAnyRole("ADMINISTRATOREN", "ADMINS", "ADMIN", "DOMÄNEN-ADMINS")
                        // User-Bereich: Fuer alle authentifizierten Benutzer
                        .requestMatchers("/user/**").authenticated()
                        // Alle anderen Anfragen erfordern Authentifizierung
                        .anyRequest().authenticated()
                )
                // Formular-basiertes Login
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                // Logout Konfiguration
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
}
