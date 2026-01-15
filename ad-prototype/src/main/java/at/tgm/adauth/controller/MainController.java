package at.tgm.adauth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.stream.Collectors;

/**
 * Controller fuer die Hauptseiten der AD-Authentication Anwendung.
 *
 * Demonstriert rollenbasierte Zugriffskontrolle:
 * - Oeffentlicher Bereich: /, /login
 * - User-Bereich: /user/** (alle authentifizierten Benutzer)
 * - Admin-Bereich: /admin/** (nur Administratoren)
 */
@Controller
public class MainController {

    /**
     * Startseite - oeffentlich zugaenglich.
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }

    /**
     * Login-Seite mit Username/Passwort Formular.
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Dashboard nach erfolgreichem Login.
     * Zeigt Benutzerinformationen und verfuegbare Bereiche.
     */
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        if (authentication != null) {
            model.addAttribute("username", authentication.getName());
            model.addAttribute("authorities", authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));

            // Pruefe ob Benutzer Admin-Rechte hat
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().toUpperCase().contains("ADMIN"));
            model.addAttribute("isAdmin", isAdmin);
        }
        return "dashboard";
    }

    /**
     * User-Bereich - zugaenglich fuer alle authentifizierten Benutzer.
     * Zeigt benutzerspezifische Informationen.
     */
    @GetMapping("/user/profile")
    public String userProfile(Authentication authentication, Model model) {
        if (authentication != null) {
            model.addAttribute("username", authentication.getName());
            model.addAttribute("authorities", authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));

            // Zusaetzliche LDAP-Details wenn verfuegbar
            Object principal = authentication.getPrincipal();
            if (principal instanceof LdapUserDetails) {
                LdapUserDetails ldapUser = (LdapUserDetails) principal;
                model.addAttribute("dn", ldapUser.getDn());
            }
        }
        return "user/profile";
    }

    /**
     * User-Bereich - Windpark-Daten fuer normale Benutzer.
     */
    @GetMapping("/user/windpark")
    public String userWindpark(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
        return "user/windpark";
    }

    /**
     * Admin-Bereich - nur fuer Administratoren zugaenglich.
     * Zeigt Verwaltungsfunktionen.
     */
    @GetMapping("/admin/dashboard")
    public String adminDashboard(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("authorities", authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        return "admin/dashboard";
    }

    /**
     * Admin-Bereich - Benutzerverwaltung.
     */
    @GetMapping("/admin/users")
    public String adminUsers(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
        return "admin/users";
    }
}
