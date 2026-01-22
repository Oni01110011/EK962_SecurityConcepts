# EK9.6 Security Concepts - Middleware Engineering

![Tests](https://github.com/Oni01110011/EK962_SecurityConcepts/actions/workflows/test.yml/badge.svg)
![Coverage](.github/badges/jacoco.svg)

Uebungsaufgabe zur Implementierung von Authentication Services mit Spring Boot.

## Inhalt

- **Recherche:** Vergleich von Authentifizierungstechnologien (SSO, OAuth, SAML, Kerberos, CAS)
- **Prototyp 1:** Social Login mit OAuth2 (Google, GitHub)
- **Prototyp 2:** Active Directory Authentifizierung mit LDAP

---

## Authentifizierungsprotokolle im Ueberblick

In diesem Projekt wurden **OAuth2** und **LDAP** implementiert. Hier ein Ueberblick ueber die gaengigsten Authentifizierungsprotokolle:

### LDAP (Lightweight Directory Access Protocol)

**Was:** Protokoll zum Auffinden von Benutzern, Gruppen und Ressourcen in einem Netzwerk-Verzeichnis.

**Wie:** Client sendet Bind-Request an LDAP-Server, der die Credentials gegen das Verzeichnis prueft.

**Wofuer:** Grundlage fuer Microsoft Active Directory, zentrale Benutzerverwaltung in Unternehmen.

### OAuth 2.0

**Was:** Autorisierungs-Framework fuer delegierten Zugriff auf Benutzerkonten (Google, GitHub, etc.).

**Wie:** Benutzer genehmigt Zugriff -> Anwendung erhaelt Authorization Code -> Code wird gegen Access Token getauscht.

**Wofuer:** Social Login, Drittanbieter-Integrationen, API-Zugriff ohne Passwort-Weitergabe.

### Kerberos

**Was:** Netzwerk-Authentifizierungsprotokoll mit Ticket-basierter Authentifizierung.

**Wie:** Benutzer erhaelt Ticket-Granting-Ticket (TGT) vom Key Distribution Center (KDC), damit werden Service-Tickets fuer einzelne Dienste angefordert.

**Wofuer:** Enterprise-Umgebungen mit hohen Sicherheitsanforderungen, Windows-Domaenen.

### SAML (Security Assertion Markup Language)

**Was:** XML-basierter Standard fuer den Austausch von Authentifizierungs- und Autorisierungsdaten.

**Wie:** Benutzer wird zu Identity Provider weitergeleitet, authentifiziert sich, erhaelt signierte XML-Assertion.

**Wofuer:** Single Sign-On (SSO) in Unternehmen, Federation zwischen Organisationen.

### RADIUS (Remote Authentication Dial-In User Service)

**Was:** Protokoll fuer zentralisierte Authentifizierung, Autorisierung und Accounting (AAA).

**Wie:** Zugangspunkt sendet verschluesselte Credentials an RADIUS-Server, der gegen Datenbank oder AD prueft.

**Wofuer:** WLAN-Authentifizierung, VPN-Zugang, Netzwerkzugriffskontrolle.

### Vergleich

| Protokoll | Typ | Einsatzgebiet | In diesem Projekt |
|-----------|-----|---------------|-------------------|
| LDAP | Verzeichnisdienst | Unternehmen, AD | **Ja (AD-Prototyp)** |
| OAuth 2.0 | Autorisierung | Web/Mobile Apps | **Ja (Social Login)** |
| Kerberos | Authentifizierung | Windows-Domaenen | Nein |
| SAML | Federation/SSO | Enterprise SSO | Nein |
| RADIUS | AAA | Netzwerkzugang | Nein |

> Quelle: [Kisi - Authentication Protocols Overview](https://www.getkisi.com/blog/authentication-protocols-overview)

---

## Projektstruktur

```
syt5-ek96-security-concepts/
├── README.md                        # Diese Datei
├── docs/
│   ├── RECHERCHE.md                 # Technologie-Vergleich
│   └── TESTFAELLE.md                # Testdokumentation
├── social-login-prototype/          # OAuth2 Social Login
│   ├── build.gradle
│   └── src/main/...
└── ad-prototype/                    # AD/LDAP Authentication
    ├── build.gradle
    └── src/main/...
```

## Quick Start

### Social Login Prototyp

```bash
cd social-login-prototype
./gradlew bootRun
# Oeffnen: http://localhost:1234
```

### AD Prototyp

```bash
cd ad-prototype
export LDAP_DOMAIN=tgm.ac.at
export LDAP_URL=ldap://dc-01.tgm.ac.at:389
./gradlew bootRun
# Oeffnen: http://localhost:8081
```

---

## Implementierung: Social Login Prototyp (OAuth2)

### Architektur

```
┌─────────────┐     ┌─────────────────┐     ┌──────────────────┐
│   Browser   │────>│  Spring Boot    │────>│  OAuth2 Provider │
│             │<────│  Application    │<────│  (Google/GitHub) │
└─────────────┘     └─────────────────┘     └──────────────────┘
```

### 1. Dependencies

```gradle
implementation 'org.springframework.boot:spring-boot-starter-security'
implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
```

### 2. OAuth2 Provider Konfiguration

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: ${GOOGLE_CLIENT_ID}
            client-secret: ${GOOGLE_CLIENT_SECRET}
            scope: [email, profile]
          github:
            client-id: ${GITHUB_CLIENT_ID}
            client-secret: ${GITHUB_CLIENT_SECRET}
            scope: [user:email, read:user]
```

### 3. Security Filter Chain

```java
http
    .authorizeHttpRequests(authorize -> authorize
        .requestMatchers("/", "/login", "/error").permitAll()
        .anyRequest().authenticated()
    )
    .oauth2Login(oauth2 -> oauth2
        .loginPage("/login")
        .defaultSuccessUrl("/dashboard", true)
    );
```

### 4. Benutzerinfos aus OAuth2 Response

```java
@GetMapping("/dashboard")
public String dashboard(@AuthenticationPrincipal OAuth2User principal, Model model) {
    model.addAttribute("name", principal.getAttribute("name"));
    model.addAttribute("email", principal.getAttribute("email"));
    return "dashboard";
}
```

### OAuth2 Flow

```
1. Benutzer klickt "Mit Google anmelden"
2. Weiterleitung zu Google OAuth2
3. Benutzer authentifiziert sich
4. Google sendet Authorization Code
5. Spring tauscht Code gegen Access Token
6. Benutzerinfos werden geladen
7. Session wird erstellt
```

---

## Implementierung: Active Directory Prototyp (LDAP)

### Architektur

```
┌─────────────┐     ┌─────────────────┐     ┌──────────────────┐
│   Browser   │────>│  Spring Boot    │────>│  Active Directory│
│             │<────│  Application    │<────│  (LDAP Server)   │
└─────────────┘     └─────────────────┘     └──────────────────┘
                           │
                    ┌──────┴───────┐
                    │ Rollenbasiert │
                    │ Admin / User  │
                    └──────────────┘
```

### 1. Dependencies

```gradle
implementation 'org.springframework.boot:spring-boot-starter-security'
implementation 'org.springframework.ldap:spring-ldap-core'
implementation 'org.springframework.security:spring-security-ldap'
```

### 2. LDAP Konfiguration

```yaml
ldap:
  domain: ${LDAP_DOMAIN:tgm.ac.at}
  url: ${LDAP_URL:ldap://dc-01.tgm.ac.at:389}
```

### 3. AD Authentication Provider

```java
@Bean
public ActiveDirectoryLdapAuthenticationProvider adProvider() {
    return new ActiveDirectoryLdapAuthenticationProvider(ldapDomain, ldapUrl);
}
```

### 4. Rollenbasierte Zugriffskontrolle

```java
http
    .authorizeHttpRequests(authorize -> authorize
        .requestMatchers("/", "/login").permitAll()
        .requestMatchers("/admin/**").hasAnyRole("ADMINISTRATOREN", "ADMINS")
        .requestMatchers("/user/**").authenticated()
        .anyRequest().authenticated()
    )
    .formLogin(form -> form
        .loginPage("/login")
        .defaultSuccessUrl("/dashboard", true)
    );
```

### 5. AD-Gruppen als Rollen auslesen

```java
@GetMapping("/dashboard")
public String dashboard(Authentication auth, Model model) {
    model.addAttribute("username", auth.getName());
    model.addAttribute("roles", auth.getAuthorities()
        .stream().map(GrantedAuthority::getAuthority).toList());
    return "dashboard";
}
```

### AD Authentifizierungs-Flow

```
1. Benutzer gibt AD-Credentials ein
2. Spring sendet LDAP Bind-Request
3. AD prueft Credentials
4. Gruppenmitgliedschaften werden geladen
5. AD-Gruppen -> Spring Roles (z.B. ROLE_ADMINISTRATOREN)
6. Zugriffskontrolle basierend auf Rollen
```

### Rollen-Mapping

| AD-Gruppe | Spring Role | Zugriff |
|-----------|-------------|---------|
| Administratoren | ROLE_ADMINISTRATOREN | /admin/**, /user/** |
| Users | ROLE_USERS | /user/** |

---

## Technologien

| Prototyp | Technologie | Framework |
|----------|-------------|-----------|
| Social Login | OAuth 2.0 / OIDC | Spring Security OAuth2 Client |
| AD Auth | LDAP / Kerberos | Spring Security LDAP |

