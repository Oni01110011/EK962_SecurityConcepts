package at.tgm.securityconcepts.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller für die Hauptseiten der Anwendung.
 *
 * Endpunkte:
 * - / : Startseite (öffentlich)
 * - /login : Login-Seite mit OAuth2-Providern
 * - /dashboard : Benutzer-Dashboard (geschützt)
 * - /user : Benutzerprofilinformationen (geschützt)
 */
@Controller
public class HomeController {

    /**
     * Startseite der Anwendung.
     * Öffentlich zugänglich.
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }

    /**
     * Login-Seite mit verfügbaren OAuth2-Providern.
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Benutzer-Dashboard nach erfolgreichem Login.
     * Zeigt Informationen über den angemeldeten Benutzer.
     *
     * @param principal OAuth2User mit Benutzerinformationen vom Provider
     * @param model Spring MVC Model
     */
    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal != null) {
            // Benutzerinformationen aus OAuth2 Response extrahieren
            model.addAttribute("name", getDisplayName(principal));
            model.addAttribute("email", principal.getAttribute("email"));
            model.addAttribute("picture", getPictureUrl(principal));
            model.addAttribute("attributes", principal.getAttributes());
        }
        return "dashboard";
    }

    /**
     * Detaillierte Benutzerprofilseite.
     *
     * @param principal OAuth2User mit Benutzerinformationen
     * @param model Spring MVC Model
     */
    @GetMapping("/user")
    public String userProfile(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal != null) {
            model.addAttribute("user", principal);
            model.addAttribute("attributes", principal.getAttributes());
        }
        return "user";
    }

    /**
     * Extrahiert den Anzeigenamen aus verschiedenen OAuth2-Provider-Formaten.
     */
    private String getDisplayName(OAuth2User principal) {
        // Google: "name"
        // GitHub: "login" oder "name"
        // Facebook: "name"
        String name = principal.getAttribute("name");
        if (name == null) {
            name = principal.getAttribute("login"); // GitHub fallback
        }
        return name != null ? name : "Unbekannter Benutzer";
    }

    /**
     * Extrahiert die Profilbild-URL aus verschiedenen OAuth2-Provider-Formaten.
     */
    private String getPictureUrl(OAuth2User principal) {
        // Google: "picture"
        // GitHub: "avatar_url"
        // Facebook: erfordert Graph API Aufruf
        String picture = principal.getAttribute("picture");
        if (picture == null) {
            picture = principal.getAttribute("avatar_url"); // GitHub
        }
        return picture;
    }
}
