package at.tgm.adauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hauptklasse fuer den Active Directory Authentication Prototyp.
 * Demonstriert LDAP-basierte Authentifizierung gegen ein Active Directory
 * mit rollenbasierter Zugriffskontrolle.
 *
 * Features:
 * - LDAP/AD Authentifizierung
 * - Gruppenbasierte Rollen (Admin, User)
 * - Zwei geschuetzte Bereiche mit unterschiedlichen Zugriffsrechten
 *
 * @author Security Concepts Team
 * @version 1.0.0
 */
@SpringBootApplication
public class AdAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdAuthApplication.class, args);
    }
}
