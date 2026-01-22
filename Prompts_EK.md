# You Asked:

das ist die aufgabe dazu werde ich dich sahen fragen: Voraussetzungen
Es wird eine Java Laufzeitumgebung mit Git vorausgesetzt. Von Vorteil ist eine Gradle- bzw. Maven-Unterstützung, um vorgefertigte Repositories leicht ausführen zu können.
Detailierte Ausgabenbeschreibung
Recherche
Als Junior-Developer erhalten Sie von Ihrem technischen Architekten die Aufgabe, eine Gegenüberstellung von gängigen Authentifikationssystemen zu erstellen. Beachten Sie bei der Auflistung von Vor- und Nachteilen die notwendigen funktionalen Anforderungen:
Anbindung an ein bestehende Authentifikations-Services
Token-Authentifizierung, um ein andauerndes Login bei mehreren Services gering zu halten bzw. auf ein einmaliges Authentifizieren zu beschränken
Verwendung auf verschiedenen Implementierungen und Devices
Diese Gegenüberstellung soll kurz und bündig auf die gängigen Systeme und Technologien verweisen. Es sollen keine Informationen kopiert sondern nur kurz zusammengefasst werden.
Implementierung
Um das in Entwicklung befindliche Online-Portal zur Bereitstellung von Informationen des Windparks entsprechend einer breiten Community schmackhaft zu machen, und trotzdem eine Authentifizierung anzubieten, werden Sie beauftragt einen Prototypen für Spring-Boot zu entwickeln, der sich über Sozialen-Netzwerken authentifizieren kann (Facebook, Google+, etc.).
Es soll ebenfalls eine Implementierung bereitgestellt werden, die sich an einen aktiven AD-Service verbinden und entsprechend die Rollen von bestimmten Gruppen unterscheiden kann. Dabei sollen zwei Bereiche festgelegt werden, die nur von einer bestimmten Gruppe betreten werden kann.
Die Prototypen sollen klar und einfach gehalten werden und mit entsprechender Dokumentation versehen sein, um eine einfache Integrierung in das bestehende Spring-Boot Projekt zu ermöglichen.
Bewertung
Gruppengrösse: 1-2 Person(en)
Anforderungen überwiegend erfüllt
Aufzählung und Quellenrecherche der in der Einführung erwähnter Technologien (30/200)
Vergleich der Technologien auf mögliche Einsatzgebiete (30/200)
Erstellung eines Prototypen zur Authentifikation mittels sozialer Dienste (30/200)
Verwendung von Deployment-Tool und entsprechende Dokumentation der notwendigen Schritte (20/200)
Testen der Webapplikation und Dokumentation der einzelnen Testfälle und deren Ergebnisse (20/200)
Anforderungen zur Gänze erfüllt
Erstellung eines Prototypen zur Authentifikation mittels Active-Directory (30/200)
Authentifikation mittels externen Active-Directory mit bestehender User-Datenbank (z.B. dc-01.tgm.ac.at) (20/200)
Verwendung von Deployment-Tool und entsprechende Dokumentation der notwendigen Schritte (10/200)
Testen der Webapplikation und Dokumentation der einzelnen Testfälle und deren Ergebnisse (10/200)

# Gemini Responded:

Das ist ein spannendes und praxisnahes Projekt! Du übernimmst hier die Rolle eines Junior-Developers, der sich mit **Identity and Access Management (IAM)** in der Spring-Boot-Welt auseinandersetzen muss.

Hier ist eine strukturierte Übersicht, wie wir diese Aufgabe angehen können. Welchen Teil möchtest du als Erstes vertiefen?

---

## 1. Recherche: Vergleich der Authentifikationssysteme
Für die Gegenüberstellung bieten sich vor allem drei Ansätze an, die deine Anforderungen (Token-basiert, Multi-Device, Service-Anbindung) erfüllen:

| System | Technologie | Vorteile | Nachteile |
| :--- | :--- | :--- | :--- |
| **OAuth2 / OpenID Connect (OIDC)** | JWT (JSON Web Tokens) | Standard für Social Login; Single Sign-On (SSO) fähig; sicher. | Komplexere Konfiguration der Authorization Server. |
| **LDAP / Active Directory** | Binäres Protokoll | Perfekt für interne Firmennetze; zentrale Benutzerverwaltung. | Nicht nativ für Web/Mobile optimiert (braucht oft Gateway). |
| **SAML 2.0** | XML-basiert | Stark verbreitet im Enterprise-Sektor; sehr sicher. | Sehr "schwerfälliges" XML-Format; kompliziert für Mobile Apps. |



[Image of OAuth2 flow diagram]


---

## 2. Implementierung Prototyp 1: Social Login
In Spring Boot nutzt man hierfür das Modul **Spring Security OAuth2 Client**.
* **Technik:** OAuth2 / OIDC.
* **Vorgehen:** In der `application.yml` werden die Client-IDs und Secrets von Google oder Facebook hinterlegt. Spring Security übernimmt den Rest des Redirect-Flows fast automatisch.

---

## 3. Implementierung Prototyp 2: Active Directory (AD)
Hier wird es technischer, da du eine Verbindung zu einem Domain Controller aufbauen musst.
* **Technik:** Spring Security LDAP.
* **Besonderheit:** Du musst die Rollen-Mappings beachten. Wenn ein User in der AD-Gruppe "Windpark_Admins" ist, muss Spring Boot dies als `ROLE_ADMIN` erkennen, um den Zugriff auf geschützte Bereiche zu erlauben.

---

## Dein Fahrplan zum Erfolg
Um die volle Punktzahl (200/200) zu erreichen, empfehle ich dieses Vorgehen:

1.  **Technologien wählen:** Wir nutzen **Gradle** oder **Maven** (wie gefordert).
2.  **Dokumentation:** Erstelle eine `README.md`, die Schritt für Schritt erklärt, wie man die Prototypen startet (z.B. `./gradlew bootRun`).
3.  **Testfälle:** Wir schreiben einfache Integrationstests mit `@SpringBootTest`, um zu beweisen, dass die Login-Umleitung funktioniert.

**Womit möchtest du beginnen?**
* Soll ich dir ein **Code-Gerüst** für den Social-Login (Google/Facebook) erstellen?
* Oder brauchst du Hilfe bei der **LDAP/AD-Konfiguration** für Spring Security?

---

# You Asked:

ch muss für mein Middleware-Engineering Modul (EK9.6) Security-Konzepte umsetzen. Ich plane zwei Prototypen: einen für Social Login (OAuth2) und einen für Active Directory (LDAP). Kannst du mir helfen, eine Vergleichsmatrix für SSO-Technologien (OAuth2, SAML, Kerberos, CAS) zu erstellen, die ich in meine RECHERCHE.md aufnehmen kann?

# Gemini Responded:

Das ist eine hervorragende Ergänzung für dein Modul. Da du Middleware-Engineering (EK9.6) belegst, ist es wichtig, nicht nur die "Was"-Frage zu beantworten, sondern auch das "Wie" (Protokolle, Datenformate) und das "Wo" (Einsatzszenarien) technisch präzise gegenüberzustellen.

Hier ist eine Vergleichsmatrix, die speziell auf die Anforderungen deines Moduls zugeschnitten ist:

### Vergleichsmatrix: SSO-Technologien

| Merkmal | OAuth 2.0 / OIDC | SAML 2.0 | Kerberos | CAS (Central Auth Service) |
| :--- | :--- | :--- | :--- | :--- |
| **Primärer Fokus** | Autorisierung & Modern Auth | Enterprise Identity Federation | Lokale Intranet-Authentifizierung | Web-basiertes SSO (Akademisch) |
| **Datenformat** | JSON / JWT (Lightweight) | XML (Heavyweight) | Binär (Tickets) | XML / JSON |
| **Transport** | HTTP / REST | HTTP / SOAP | TCP/UDP (Port 88) | HTTP / REST |
| **Sicherheitsmodell** | Bearer Token / TLS | Digitale Signaturen / XML-Enc | Symmetrische Verschlüsselung | Ticket-basierte Validierung |
| **Mobile-Tauglichkeit** | **Hervorragend** | Eingeschränkt (Browser-Fokus) | Sehr schwierig | Gut |
| **Typisches Szenario** | Social Login (Google, FB), APIs | Azure AD, Salesforce, SAP | Windows Domain / Active Directory | Universitäts-Netzwerke |

---

### Analyse für deine Prototypen

Um die Anforderungen deines Architekten ("kurz und bündig", "keine Kopien") zu erfüllen, kannst du diese Punkte in deine `RECHERCHE.md` einfließen lassen:

#### 1. OAuth2 / OpenID Connect (OIDC) – Der moderne Standard
OIDC setzt auf OAuth2 auf und fügt eine Identitätsebene hinzu. Durch die Verwendung von **JSON Web Tokens (JWT)** ist es ideal für Microservices und mobile Apps, da die Tokens staatenlos (*stateless*) validiert werden können.
* **Vorteil:** Hohe Interoperabilität mit Cloud-Diensten.
* **Middleware-Aspekt:** Einfache Integration in Spring Security via `oauth2-client`.

#### 2. Kerberos / LDAP (Active Directory) – Das Enterprise-Rückgrat
Im Gegensatz zu webbasierten Protokollen ist Kerberos tief im Betriebssystem (Windows/Linux) verwurzelt. Für Webanwendungen wird oft **SPNEGO** als Brücke genutzt, um das Kerberos-Ticket im HTTP-Header zu übertragen.
* **Vorteil:** Echtes "Zero-Login" (User ist bereits am PC angemeldet).
* **Middleware-Aspekt:** Erfordert oft eine direkte Verbindung zum Domain Controller (LDAP/S389).



---

### Nächste Schritte für dein Projekt
Da du bereits zwei Prototypen planst, könnten wir jetzt konkret werden:

1.  **Für den Social Login:** Soll ich dir zeigen, wie du die `application.yml` für Google/Facebook in Spring Boot so konfigurierst, dass die Rollen automatisch gemappt werden?
2.  **Für das AD-Szenario:** Benötigst du Unterstützung beim Setup des `LdapContextSource` in Spring Security, um die Gruppen-Rollen-Unterscheidung (z.B. Admin vs. User) umzusetzen?

**Welchen Teil der Implementierung möchtest du als Nächstes angehen?**

---

# You Asked:

Basierend auf der Recherche möchte ich nun das Projekt-Setup planen. Ich nutze Spring Boot und Gradle. Welche Dependencies brauche ich jeweils für den OAuth2-Client (Google/GitHub) und welche für die Anbindung an ein lokales Active Directory?

# Gemini Responded:

Für dein Setup mit Spring Boot (Version 3.x) und Gradle benötigst du spezifische Starter-Dependencies. Da beide Prototypen auf Spring Security basieren, ist die Basis jeweils identisch, wird aber durch die protokollspezifischen Module ergänzt.

Hier ist die Konfiguration für deine `build.gradle`:

### 1\. Prototyp: Social Login (OAuth2 / OIDC)

Dieser Starter enthält alles, was du für den Authorization Code Flow mit Anbietern wie Google oder GitHub benötigst.

```gradle
dependencies {
    // Web-Basis für die Controller
    implementation 'org.springframework.boot:spring-boot-starter-web'
    
    // Core Security & OAuth2 Client Features
    implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
    
    // Optional: Für die Darstellung (Thymeleaf passt gut zu Spring Boot)
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
}
```

### 2\. Prototyp: Active Directory (LDAP)

Die Anbindung an ein AD erfolgt über das LDAP-Protokoll. Hier sind zwei zusätzliche Bibliotheken wichtig: die LDAP-Unterstützung von Spring Data und das Security-LDAP-Modul.

```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    
    // LDAP Core & Security Support
    implementation 'org.springframework.boot:spring-boot-starter-data-ldap'
    implementation 'org.springframework.security:spring-security-ldap'
    
    // Optional: Falls du keinen echten AD-Server hast, hilft ein Embedded-Server zum Testen
    testImplementation 'com.unboundid:unboundid-ldapsdk'
}
```

-----

### Wichtige Konfigurations-Hinweise für deine Recherche

| Feature | OAuth2 Client | Active Directory (LDAP) |
| :--- | :--- | :--- |
| **User-Identität** | Wird via OIDC `UserInfo` Endpoint geladen. | Wird via `Base DN` und `Search Filter` im AD gesucht. |
| **Rollen-Mapping** | Erfolgt meist über `GrantedAuthoritiesMapper`. | Erfolgt über das Attribut `memberOf` im AD-User-Objekt. |
| **Protokoll** | REST/JSON über HTTPS. | Binäres LDAP-Protokoll (meist Port 389 oder 636). |

### Nächster Schritt für die Implementierung

Damit die Prototypen laufen, musst du die Zugangsdaten in der `application.yml` hinterlegen.

**Soll ich dir ein Beispiel für die `application.yml` erstellen, das zeigt, wie man Google-OAuth und ein lokales AD (z.B. für das TGM-Netzwerk) parallel konfiguriert?**

[Spring Boot 3 + LDAP Spring Security Tutorial](https://www.youtube.com/watch?v=0eMjjumfGmU)
Dieses Video ist hilfreich, da es zeigt, wie man die LDAP-Authentifizierung in der neuesten Spring Boot 3 Version inklusive Rollenverwaltung korrekt aufsetzt.

http://googleusercontent.com/youtube_content/0


---

# You Asked:

Lass uns mit dem Social Login starten. Wie konfiguriere ich in der application.yml mehrere Provider gleichzeitig (Google und GitHub)? Und wie sieht die SecurityFilterChain in Spring Security 6 aus, damit /login öffentlich ist, aber /dashboard geschützt wird?

# Gemini Responded:

In Spring Security 6 hat sich die Konfiguration durch den **Lambda-basierten DSL-Stil** im Vergleich zu älteren Versionen deutlich verändert. Um mehrere Provider zu unterstützen, nutzt Spring Boot die `CommonOAuth2Provider`-Klasse, die für Google und GitHub bereits Standardwerte mitbringt.

Hier ist die Umsetzung für deinen Prototypen:

### 1\. Die `application.yml`

Hier definierst du die Client-IDs und Secrets. Diese erhältst du in der [Google Cloud Console](https://console.cloud.google.com/) und in den [GitHub Developer Settings](https://github.com/settings/developers).

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: ${GOOGLE_CLIENT_ID}
            client-secret: ${GOOGLE_CLIENT_SECRET}
            scope:
              - email
              - profile
          github:
            client-id: ${GITHUB_CLIENT_ID}
            client-secret: ${GITHUB_CLIENT_SECRET}
            scope:
              - read:user
              - user:email
```

> **Tipp für das Middleware-Engineering:** Nutze Umgebungsvariablen (wie `${...}`), um Credentials niemals im Klartext in das Git-Repository zu pushen\!

-----

### 2\. Die `SecurityFilterChain` (Java Config)

In Spring Security 6 wird die Konfiguration über eine `@Bean` vom Typ `SecurityFilterChain` gelöst. Der Fokus liegt hier auf der Trennung von öffentlichen und geschützten Endpunkten.

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login**", "/error").permitAll() // Öffentlich zugänglich
                .requestMatchers("/dashboard/**").authenticated()       // Geschützter Bereich
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("/dashboard", true) // Redirect nach erfolgreichem Login
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }
}
```

-----

### 3\. Der Controller für den Test

Damit du prüfen kannst, ob die Authentifizierung funktioniert hat, kannst du die User-Daten im Controller auslesen:

```java
@RestController
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal OAuth2User principal) {
        // Hier ziehen wir den Namen aus dem Token (je nach Provider "name" oder "login")
        String name = principal.getAttribute("name");
        return "Willkommen im Windpark-Portal, " + name + "!";
    }
}
```

### Zusammenfassung für deine Dokumentation:

  * **Redirect-URIs:** Standardmäßig erwartet Spring Boot für den Prototypen `http://localhost:8080/login/oauth2/code/google` (bzw. `/github`). Das musst du bei den Providern so hinterlegen.
  * **Auto-Discovery:** Dank OpenID Connect (OIDC) muss bei Google keine `authorization-uri` angegeben werden; Spring findet die Endpunkte automatisch. Bei GitHub (OAuth2) erledigt das der Spring-Starter intern für dich.

**Soll ich dir zeigen, wie du nun die Rollentrennung (z.B. für "Admin"-Bereiche) implementierst, bevor wir zum AD/LDAP Prototypen übergehen?**

---

# You Asked:

Ich habe das Problem, dass Google das Attribut 'name' liefert, GitHub aber manchmal nur 'login'. Wie schreibe ich im Controller einen robusten Helper, der aus dem OAuth2User den richtigen Anzeigenamen extrahiert, egal welcher Provider genutzt wurde?

# Gemini Responded:

Das ist ein klassisches Problem im Middleware-Engineering: Die **Normalisierung von Daten** unterschiedlicher Quellen. Während Google dem OIDC-Standard folgt, nutzt GitHub ein proprietäres OAuth2-Schema.

Um das sauber zu lösen, solltest du die Extraktion nicht direkt in den Controller "hardcoden", sondern eine kleine Utility-Logik oder einen Service nutzen.

### 1\. Der robuste Name-Extractor

Du kannst die `getAttributes()` Map des `OAuth2User` nutzen. Wir suchen priorisiert nach dem echten Namen und fallen im Notfall auf den Usernamen/Login zurück.

```java
public class OAuth2UserHelper {

    public static String getDisplayName(OAuth2User principal) {
        if (principal == null) return "Gast";

        // 1. Suche nach Standard OIDC Attributen (Google)
        String name = principal.getAttribute("name");
        
        // 2. Suche nach GitHub-spezifischem Login, falls 'name' leer ist
        if (name == null || name.isEmpty()) {
            name = principal.getAttribute("login");
        }
        
        // 3. Fallback auf E-Mail oder 'sub' (Subject ID)
        if (name == null || name.isEmpty()) {
            name = principal.getAttribute("email");
        }

        return (name != null) ? name : "Unbekannter User";
    }
}
```

### 2\. Integration im Controller

Dein Controller bleibt dadurch schlank und testbar:

```java
@GetMapping("/dashboard")
public String dashboard(@AuthenticationPrincipal OAuth2User principal, Model model) {
    String displayName = OAuth2UserHelper.getDisplayName(principal);
    model.addAttribute("username", displayName);
    return "dashboard"; // Verweist auf dashboard.html
}
```

-----

### 3\. Professioneller Ansatz: Custom Oauth2UserService (Optional)

Wenn du für deine Dokumentation (EK9.6) glänzen willst, kannst du die Daten bereits beim Login-Prozess normalisieren. Das macht man über einen eigenen `DefaultOAuth2UserService`.

**Warum ist das besser?**

  * Du kannst die Rollen (Authorities) direkt hier anpassen.
  * Dein Controller muss sich nicht mehr um die Provider-Logik kümmern.

<!-- end list -->

```java
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        
        // Hier könntest du eine Logik einbauen, die den User in eine 
        // eigene User-Klasse mit einheitlichen Gettern mappt.
        return user; 
    }
}
```

-----

### Nächster Schritt: Active Directory (LDAP)

Da der Social Login Prototyp nun theoretisch steht, kommen wir zum komplexeren Teil der Aufgabe: **Das Active Directory**.

Im AD-Umfeld nutzen wir keine `OAuth2User`, sondern meist ein `UserDetails` Objekt, das gegen den LDAP-Server validiert wurde.

**Soll ich dir zeigen, wie du die LDAP-Authentifizierung so konfigurierst, dass sie die Rollen aus dem `memberOf`-Attribut des AD extrahiert?** (Das ist oft der schwierigste Teil bei der AD-Anbindung).

---

# You Asked:

Jetzt zum AD-Prototyp. Ich möchte den ActiveDirectoryLdapAuthenticationProvider nutzen. Wie binde ich die TGM-Domain (tgm.ac.at) ein, ohne dass ich einen festen Service-Account für den Bind brauche? Der User soll seine eigenen Credentials nutzen.

# Gemini Responded:

Das ist ein sehr eleganter Ansatz für einen Prototypen. Wenn du den `ActiveDirectoryLdapAuthenticationProvider` nutzt, verwendet Spring Security das Prinzip des **"User Bind"**. Das bedeutet, die Anwendung versucht nicht, sich mit einem Master-Account anzumelden, sondern nutzt direkt die vom User eingegebenen Credentials (Username/Passwort), um sich gegenüber dem AD zu authentifizieren.

### 1\. Die Konfiguration in der `SecurityConfig`

In Spring Security 6 musst du den AuthenticationManager explizit konfigurieren, um den AD-Provider zu registrieren. Hier ist der Code, der genau auf die `tgm.ac.at` Domain zugeschnitten ist:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        // Erstellung des AD-Providers
        ActiveDirectoryLdapAuthenticationProvider adProvider = 
            new ActiveDirectoryLdapAuthenticationProvider("tgm.ac.at", "ldap://dc-01.tgm.ac.at:389");

        // Wichtig: AD liefert oft "Active Directory Groups". 
        // Dieser Mapper konvertiert sie in Spring-Rollen (ROLE_...).
        adProvider.setConvertSubErrorCodesToExceptions(true);
        adProvider.setUseAuthenticationRequestCredentials(true);

        return new ProviderManager(adProvider);
    }

    @Bean
    public SecurityFilterChain ldapFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Beispiel: Nur User in der AD-Gruppe "Schueler" dürfen in diesen Bereich
                .requestMatchers("/internal/**").hasRole("Schueler")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .defaultSuccessUrl("/dashboard", true)
            );
        
        return http.build();
    }
}
```

-----

### 2\. Rollen-Mapping (Die "Schmackhaft"-Anforderung)

In der Aufgabenstellung steht, dass du **Rollen unterscheiden** musst. Das AD liefert Gruppen im Format `CN=Schueler,OU=Groups,DC=tgm,DC=ac,DC=at`.

Standardmäßig mappt der `ActiveDirectoryLdapAuthenticationProvider` diese Gruppen automatisch auf Authorities. Wenn ein User im AD in der Gruppe "IT-Abteilung" ist, wird daraus in Spring `ROLE_IT-Abteilung`.

> **Hinweis für die Recherche:** Im Middleware-Engineering nennt man das **Attribute Mapping**. Du solltest in deiner Dokumentation erwähnen, dass die `grantedAuthorities` direkt aus dem LDAP-Attribut `memberOf` extrahiert werden.

-----

### 3\. Testen ohne echtes AD (Wichtig für die Abgabe)

Da du für die Bewertung Testfälle dokumentieren musst, aber vielleicht nicht immer im TGM-Netz bist, solltest du in deiner `README.md` erklären, wie man den Prototyp testet:

1.  **VPN-Verbindung:** Erwähne, dass für diesen Prototyp eine Verbindung zum TGM-Netz (GlobalProtect/OpenVPN) bestehen muss, damit `dc-01.tgm.ac.at` auflösbar ist.
2.  **Fehlermeldungen:** Das AD sendet spezifische Fehler-Codes (z.B. `775` für User Locked, `52e` für Invalid Credentials). Der Provider fängt diese ab, wenn `setConvertSubErrorCodesToExceptions(true)` gesetzt ist.

-----

### Zusammenfassung der Architektur

  * **Social Login:** Nutzt OAuth2 (External Trust), Identity wird via Token (JWT) geliefert.
  * **AD Login:** Nutzt LDAP (Direct Trust), Identity wird via Bind-Operation am Domain Controller verifiziert.

### Nächster Schritt für deine Dokumentation

Soll ich dir ein Beispiel für eine **JUnit-Testklasse** schreiben, mit der du die Authentifizierung für deine Dokumentation automatisiert testen kannst? Das würde den Punkt "Testen der Webapplikation" (20/200 Punkte) optimal abdecken.

---

# You Asked:

Wie funktioniert das Mapping von AD-Gruppen auf Spring Security Rollen? Ich möchte, dass Mitglieder der Gruppe 'CN=Administratoren,OU=Groups...' automatisch Zugriff auf Endpunkte mit .hasAnyRole('ADMINISTRATOREN') haben. Muss ich das Präfix 'ROLE_' manuell hinzufügen?

# Gemini Responded:

Das Mapping von Active Directory (AD) Gruppen auf Spring Security Rollen ist ein entscheidender Schritt für dein Middleware-Projekt, da AD-Gruppenpfade (Distinguished Names) sehr lang und sperrig sind, Spring Security aber standardmäßig kurze Rollen-Strings erwartet.

### 1\. Das Standard-Verhalten des `ActiveDirectoryLdapAuthenticationProvider`

Standardmäßig extrahiert dieser Provider alle Werte aus dem AD-Attribut `memberOf`.

  * **Extraktion:** Er nimmt den `CN` (Common Name) der Gruppe. Aus `CN=Administratoren,OU=Groups,DC=tgm,DC=ac,DC=at` wird der String **"Administratoren"**.
  * **Präfix:** Spring Security fügt intern bei der Prüfung mit `hasRole()` oder `hasAnyRole()` standardmäßig das Präfix `ROLE_` hinzu.

### 2\. Die Lösung für dein Problem

Wenn du `.hasAnyRole('ADMINISTRATOREN')` verwendest, sucht Spring Security nach einer Authority namens **`ROLE_ADMINISTRATOREN`**.

Damit das funktioniert, hast du zwei Möglichkeiten:

#### Option A: Den Namen im AD-Provider anpassen (Empfohlen)

Du kannst dem Provider sagen, dass er kein Präfix hinzufügen soll oder ein benutzerdefiniertes Mapping verwenden. Da der `ActiveDirectoryLdapAuthenticationProvider` jedoch recht starr ist, nutzt man oft einen `GrantedAuthoritiesMapper`.

In deiner `SecurityConfig`:

```java
@Bean
public AuthenticationManager authManager(HttpSecurity http) throws Exception {
    ActiveDirectoryLdapAuthenticationProvider adProvider = 
        new ActiveDirectoryLdapAuthenticationProvider("tgm.ac.at", "ldap://dc-01.tgm.ac.at:389");
    
    // Verhindert, dass Fehlermeldungen verschluckt werden
    adProvider.setConvertSubErrorCodesToExceptions(true);

    // Hier definieren wir, wie die AD-Gruppen in Spring-Rollen umgewandelt werden
    adProvider.setAuthoritiesMapper(authorities -> {
        Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
        authorities.forEach(authority -> {
            String mappedName = authority.getAuthority().toUpperCase();
            // Wir fügen ROLE_ manuell hinzu, damit .hasRole("ADMINISTRATOREN") greift
            mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + mappedName));
        });
        return mappedAuthorities;
    });

    return new ProviderManager(adProvider);
}
```

#### Option B: Prüfung ohne "ROLE\_"-Präfix

Falls du das Präfix nicht manuell hinzufügen möchtest, kannst du in deiner `SecurityFilterChain` statt `hasRole()` die Methode `hasAuthority()` verwenden. Diese prüft den String exakt so, wie er vom AD kommt:

```java
.requestMatchers("/admin/**").hasAuthority("Administratoren") // Sucht exakt nach 'Administratoren'
```

-----

### 3\. Wichtiger Hinweis für die Dokumentation (RECHERCHE.md)

In deinem Bericht solltest du erwähnen, dass AD-Gruppen oft Leerzeichen oder Sonderzeichen enthalten können. Spring Security ist hier streng. Der `ActiveDirectoryLdapAuthenticationProvider` bereinigt den CN normalerweise, aber es ist Best Practice, die Rollen im Code immer in **Großbuchstaben** zu vergleichen (`.toUpperCase()`), um Case-Sensitivity-Probleme zu vermeiden.

### Zusammenfassung für den Prototyp:

1.  **AD-Gruppe:** `CN=Administratoren,...`
2.  **Provider extrahiert:** `Administratoren`
3.  **Mapper macht daraus:** `ROLE_ADMINISTRATOREN`
4.  **Security Check:** `.hasRole("ADMINISTRATOREN")` -\> **TRUE**

**Soll ich dir zeigen, wie du einen "Mock"-Test schreibst, der einen AD-User simuliert, damit du die Rollenprüfung testen kannst, ohne physisch mit dem TGM-Netz verbunden zu sein?**

---

