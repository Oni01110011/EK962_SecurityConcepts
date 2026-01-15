package at.tgm.securityconcepts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hauptklasse für den Social Login Prototyp.
 * Demonstriert OAuth2-basierte Authentifizierung mit sozialen Netzwerken.
 *
 * Unterstützte Provider:
 * - Google
 * - GitHub
 * - Facebook (optional)
 *
 * @author Security Concepts Team
 * @version 1.0.0
 */
@SpringBootApplication
public class SocialLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialLoginApplication.class, args);
    }
}
