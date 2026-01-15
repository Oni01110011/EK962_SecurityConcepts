# Social Login Prototyp

Spring Boot Anwendung zur Demonstration von OAuth2-basierter Authentifizierung mit sozialen Netzwerken.

## Funktionen

- OAuth2 Login mit Google
- OAuth2 Login mit GitHub
- (Optional: Facebook Login)
- Geschuetzter Dashboard-Bereich
- Anzeige von OAuth2-Benutzerinformationen

## Voraussetzungen

- Java 17+
- Gradle 8+
- Google OAuth2 Credentials
- GitHub OAuth2 Credentials

## OAuth2 Credentials einrichten

### Google

1. Gehe zu [Google Cloud Console](https://console.cloud.google.com/)
2. Erstelle ein neues Projekt oder waehle ein bestehendes
3. Navigiere zu "APIs & Services" > "Credentials"
4. Klicke auf "Create Credentials" > "OAuth client ID"
5. Waehle "Web application"
6. Konfiguriere:
   - Name: `Windpark Portal`
   - Authorized JavaScript origins: `http://localhost:1234`
   - Authorized redirect URIs: `http://localhost:1234/login/oauth2/code/google`
7. Kopiere Client ID und Client Secret

### GitHub

1. Gehe zu [GitHub Developer Settings](https://github.com/settings/developers)
2. Klicke auf "New OAuth App"
3. Konfiguriere:
   - Application name: `Windpark Portal`
   - Homepage URL: `http://localhost:1234`
   - Authorization callback URL: `http://localhost:1234/login/oauth2/code/github`
4. Kopiere Client ID und Client Secret

## Konfiguration

Setze die OAuth2 Credentials als Umgebungsvariablen:

```bash
export GOOGLE_CLIENT_ID=your-google-client-id
export GOOGLE_CLIENT_SECRET=your-google-client-secret
export GITHUB_CLIENT_ID=your-github-client-id
export GITHUB_CLIENT_SECRET=your-github-client-secret
```

Alternativ in `application.yml` direkt eintragen (nicht fuer Produktion empfohlen).

## Starten

```bash
./gradlew bootRun
```

Oder mit Gradle Wrapper:

```bash
gradle bootRun
```

Die Anwendung ist dann erreichbar unter: http://localhost:1234

## Projektstruktur

```
social-login-prototype/
├── build.gradle                 # Gradle Build-Konfiguration
├── settings.gradle              # Gradle Settings
├── README.md                    # Diese Dokumentation
└── src/main/
    ├── java/at/tgm/securityconcepts/
    │   ├── SocialLoginApplication.java    # Main-Klasse
    │   ├── config/
    │   │   └── SecurityConfig.java        # Spring Security Config
    │   └── controller/
    │       └── HomeController.java        # Web Controller
    └── resources/
        ├── application.yml                # App-Konfiguration
        └── templates/
            ├── index.html                 # Startseite
            ├── login.html                 # Login-Seite
            ├── dashboard.html             # Dashboard
            └── user.html                  # Benutzerprofil
```

## Endpunkte

| Pfad | Beschreibung | Zugriffsrecht |
|------|--------------|---------------|
| `/` | Startseite | Oeffentlich |
| `/login` | Login-Seite | Oeffentlich |
| `/dashboard` | Benutzer-Dashboard | Authentifiziert |
| `/user` | Benutzerdetails | Authentifiziert |
| `/logout` | Abmelden (POST) | Authentifiziert |

## Integration in bestehendes Projekt

1. **Dependencies hinzufuegen** (build.gradle):
   ```groovy
   implementation 'org.springframework.boot:spring-boot-starter-security'
   implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
   ```

2. **SecurityConfig kopieren** und anpassen

3. **application.yml erweitern** mit OAuth2-Konfiguration

4. **Templates anpassen** an bestehendes Design

## Sicherheitshinweise

- Client Secrets niemals im Code committen
- Umgebungsvariablen oder Secret Management verwenden
- In Produktion HTTPS verwenden
- Redirect URIs genau konfigurieren
