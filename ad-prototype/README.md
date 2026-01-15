# Active Directory Authentication Prototyp

Spring Boot Anwendung zur Demonstration von LDAP/Active Directory Authentifizierung mit rollenbasierter Zugriffskontrolle.

## Funktionen

- LDAP/Active Directory Authentifizierung
- Rollenbasierte Zugriffskontrolle basierend auf AD-Gruppen
- Zwei geschuetzte Bereiche:
  - **User-Bereich** (`/user/**`): Fuer alle authentifizierten Benutzer
  - **Admin-Bereich** (`/admin/**`): Nur fuer Administratoren (AD-Gruppe)

## Voraussetzungen

- Java 17+
- Gradle 8+
- Zugang zu einem Active Directory Server (z.B. `dc-01.tgm.ac.at`)

## Konfiguration

### Umgebungsvariablen

```bash
export LDAP_DOMAIN=tgm.ac.at
export LDAP_URL=ldap://dc-01.tgm.ac.at:389
```

### application.yml

Die Konfiguration kann auch direkt in `src/main/resources/application.yml` angepasst werden:

```yaml
ldap:
  domain: tgm.ac.at
  url: ldap://dc-01.tgm.ac.at:389
```

### Unterstuetzte AD-Server

| Umgebung | Domain | URL |
|----------|--------|-----|
| TGM | tgm.ac.at | ldap://dc-01.tgm.ac.at:389 |
| Lokal (Test) | example.com | ldap://localhost:389 |
| Azure AD | yourdomain.onmicrosoft.com | ldaps://...:636 |

## Rollenkonzept

Die Anwendung mappt AD-Gruppen automatisch auf Spring Security Rollen:

| AD-Gruppe | Spring Role | Zugriff |
|-----------|-------------|---------|
| Administratoren | ROLE_ADMINISTRATOREN | Admin + User Bereich |
| Admins | ROLE_ADMINS | Admin + User Bereich |
| Domaenen-Admins | ROLE_DOMÄNEN-ADMINS | Admin + User Bereich |
| (alle anderen) | ROLE_* | User Bereich |

### Anpassung der Rollenpr uefung

In `SecurityConfig.java` koennen die Admin-Gruppen angepasst werden:

```java
.requestMatchers("/admin/**")
    .hasAnyRole("ADMINISTRATOREN", "ADMINS", "ADMIN", "DOMÄNEN-ADMINS")
```

## Starten

```bash
./gradlew bootRun
```

Die Anwendung ist erreichbar unter: http://localhost:8081

## Projektstruktur

```
ad-prototype/
├── build.gradle                 # Gradle Build-Konfiguration
├── settings.gradle              # Gradle Settings
├── README.md                    # Diese Dokumentation
└── src/main/
    ├── java/at/tgm/adauth/
    │   ├── AdAuthApplication.java       # Main-Klasse
    │   ├── config/
    │   │   └── SecurityConfig.java      # Security + LDAP Config
    │   └── controller/
    │       └── MainController.java      # Web Controller
    └── resources/
        ├── application.yml              # App-Konfiguration
        └── templates/
            ├── index.html               # Startseite
            ├── login.html               # Login-Formular
            ├── dashboard.html           # Dashboard
            ├── user/
            │   ├── profile.html         # Benutzerprofil
            │   └── windpark.html        # Windpark-Daten
            └── admin/
                ├── dashboard.html       # Admin-Dashboard
                └── users.html           # Benutzerverwaltung
```

## Endpunkte

| Pfad | Beschreibung | Zugriffsrecht |
|------|--------------|---------------|
| `/` | Startseite | Oeffentlich |
| `/login` | Login-Seite | Oeffentlich |
| `/dashboard` | Benutzer-Dashboard | Authentifiziert |
| `/user/profile` | Benutzerprofil | Authentifiziert |
| `/user/windpark` | Windpark-Daten | Authentifiziert |
| `/admin/dashboard` | Admin-Dashboard | ROLE_ADMIN* |
| `/admin/users` | Benutzerverwaltung | ROLE_ADMIN* |
| `/logout` | Abmelden (POST) | Authentifiziert |

## Integration in bestehendes Projekt

1. **Dependencies hinzufuegen** (build.gradle):
   ```groovy
   implementation 'org.springframework.boot:spring-boot-starter-security'
   implementation 'org.springframework.ldap:spring-ldap-core'
   implementation 'org.springframework.security:spring-security-ldap'
   ```

2. **SecurityConfig kopieren** und AD-Konfiguration anpassen

3. **application.yml erweitern** mit LDAP-Konfiguration

4. **Controller anpassen** mit Rollen-Annotationen:
   ```java
   @PreAuthorize("hasRole('ADMIN')")
   @GetMapping("/admin/secret")
   public String adminSecret() { ... }
   ```

## Troubleshooting

### Verbindungsfehler zum AD

```
Caused by: javax.naming.CommunicationException
```
- AD-Server erreichbar? Firewall-Regeln pruefen
- Korrekte URL und Port?

### Authentifizierung fehlgeschlagen

```
Bad credentials
```
- Benutzername im richtigen Format? (sAMAccountName)
- Passwort korrekt?
- Benutzer im AD aktiv?

### Keine Admin-Rechte

- Ist der Benutzer Mitglied einer Admin-Gruppe im AD?
- Gruppennamen in `SecurityConfig.java` pruefen
- Debug-Logging aktivieren um zugewiesene Rollen zu sehen

## Sicherheitshinweise

- In Produktion LDAPS (Port 636) mit SSL/TLS verwenden
- Service-Account fuer LDAP-Bindung in Erwaegung ziehen
- Passwort-Policy des AD beachten
- Session-Timeout konfigurieren
