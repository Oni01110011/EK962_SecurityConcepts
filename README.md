# EK9.6 Security Concepts - Middleware Engineering

![Tests](https://github.com/Oni01110011/EK962_SecurityConcepts/actions/workflows/test.yml/badge.svg)
![Coverage](.github/badges/jacoco.svg)

Uebungsaufgabe zur Implementierung von Authentication Services mit Spring Boot.

## Inhalt

- **Recherche:** Vergleich von Authentifizierungstechnologien (SSO, OAuth, SAML, Kerberos, CAS)
- **Prototyp 1:** Social Login mit OAuth2 (Google, GitHub)
- **Prototyp 2:** Active Directory Authentifizierung mit LDAP

## Projektstruktur

```
syt5-ek96-security-concepts/
├── README.md                        # Diese Datei
├── docs/
│   ├── RECHERCHE.md                 # Technologie-Vergleich
│   └── TESTFAELLE.md                # Testdokumentation
├── social-login-prototype/          # OAuth2 Social Login
│   ├── build.gradle
│   ├── README.md
│   └── src/main/...
└── ad-prototype/                    # AD/LDAP Authentication
    ├── build.gradle
    ├── README.md
    └── src/main/...
```

## Quick Start

### Social Login Prototyp

```bash
cd social-login-prototype

# Starten (Credentials sind bereits in application.yml konfiguriert)
./gradlew bootRun

# Oeffnen: http://localhost:1234
```

### AD Prototyp

```bash
cd ad-prototype

# LDAP Konfiguration (optional, defaults auf TGM AD)
export LDAP_DOMAIN=tgm.ac.at
export LDAP_URL=ldap://dc-01.tgm.ac.at:389

# Starten
./gradlew bootRun

# Oeffnen: http://localhost:8081
```

## Bewertungskriterien (erfuellt)

### Anforderungen ueberwiegend erfuellt

- [x] Aufzaehlung und Quellenrecherche der Technologien (siehe `docs/RECHERCHE.md`)
- [x] Vergleich der Technologien auf moegliche Einsatzgebiete
- [x] Prototyp zur Authentifikation mittels sozialer Dienste (Google, GitHub)
- [x] Dokumentation der notwendigen Schritte
- [x] Testfaelle und deren Dokumentation (siehe `docs/TESTFAELLE.md`)

### Anforderungen zur Gaenze erfuellt

- [x] Prototyp zur Authentifikation mittels Active Directory
- [x] Anbindung an externes AD (dc-01.tgm.ac.at)
- [x] Rollenbasierte Zugriffskontrolle (Admin/User-Bereiche)
- [x] Dokumentation der Schritte
- [x] Testfaelle dokumentiert

## Technologien

| Prototyp | Technologie | Framework |
|----------|-------------|-----------|
| Social Login | OAuth 2.0 / OIDC | Spring Security OAuth2 Client |
| AD Auth | LDAP / Kerberos | Spring Security LDAP |

## Autor

- Aufgabe: EK9.6 Middleware Engineering "Security Concepts"
- Datum: 2026-01-14
