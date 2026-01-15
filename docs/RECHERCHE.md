# Recherche: Authentifizierungstechnologien

## Autor
- Name: [Dein Name]
- Datum: 2026-01-14
- Aufgabe: EK9.6 Middleware Engineering "Security Concepts"

---

## 1. Übersicht der Technologien

### 1.1 Single Sign-On (SSO)

**Definition:** SSO ist ein Authentifizierungskonzept, das Benutzern ermöglicht, sich einmalig anzumelden und dann auf mehrere verbundene Systeme zuzugreifen, ohne sich erneut authentifizieren zu müssen.

**Funktionsweise:**
- Benutzer authentifiziert sich einmal bei einem Identity Provider (IdP)
- IdP erstellt ein Token/Session
- Verbundene Anwendungen (Service Provider) vertrauen dem IdP und gewähren Zugriff

**Vorteile:**
- Verbesserte User Experience durch einmaliges Login
- Reduzierte Passwort-Fatigue
- Zentralisierte Zugriffskontrolle
- Einfachere Verwaltung von Benutzerrechten

**Nachteile:**
- Single Point of Failure (IdP-Ausfall = kein Zugriff)
- Erhöhtes Sicherheitsrisiko bei kompromittierten Credentials
- Komplexere Implementierung

---

### 1.2 Ticket Granting Ticket (TGT)

**Definition:** TGT ist ein zentrales Konzept im Kerberos-Protokoll. Es handelt sich um ein verschlüsseltes Ticket, das nach erfolgreicher Authentifizierung vom Key Distribution Center (KDC) ausgestellt wird.

**Funktionsweise:**
1. Benutzer authentifiziert sich beim Authentication Server (AS)
2. AS stellt TGT aus (verschlüsselt mit KDC-Schlüssel)
3. TGT wird für Anfragen an den Ticket Granting Server (TGS) verwendet
4. TGS stellt Service Tickets für spezifische Dienste aus

**Vorteile:**
- Passwort wird nur einmal übertragen
- Zeitlich begrenzte Gültigkeit erhöht Sicherheit
- Effizientes Caching möglich

**Nachteile:**
- Komplexe Infrastruktur erforderlich
- Zeitsynchronisation zwischen Systemen notwendig
- Schwierig in heterogenen Umgebungen

---

### 1.3 Central Authentication Service (CAS)

**Definition:** CAS ist ein Open-Source-Protokoll und -Server für Web-basiertes SSO, ursprünglich entwickelt an der Yale University.

**Funktionsweise:**
1. Benutzer greift auf geschützte Ressource zu
2. Umleitung zum CAS-Server
3. Authentifizierung beim CAS-Server
4. CAS erstellt Service Ticket (ST)
5. Umleitung zurück zur Anwendung mit ST
6. Anwendung validiert ST beim CAS-Server

**Vorteile:**
- Open Source und gut dokumentiert
- Einfache Integration in Java-Anwendungen
- Unterstützt verschiedene Backends (LDAP, JDBC, etc.)
- Proxy-Authentifizierung möglich

**Nachteile:**
- Primär für Web-Anwendungen konzipiert
- Zusätzliche Infrastruktur notwendig
- Session-Management kann komplex werden

---

### 1.4 OAuth 2.0 (Open Authorization)

**Definition:** OAuth 2.0 ist ein Autorisierungs-Framework, das Drittanwendungen begrenzten Zugriff auf Benutzerressourcen ermöglicht, ohne Credentials weiterzugeben.

**Wichtig:** OAuth ist primär ein **Autorisierungs**-Protokoll, nicht ein Authentifizierungsprotokoll. Für Authentifizierung wird oft OpenID Connect (OIDC) darauf aufgesetzt.

**Rollen:**
- **Resource Owner:** Der Benutzer
- **Client:** Die Anwendung, die Zugriff benötigt
- **Authorization Server:** Stellt Tokens aus
- **Resource Server:** Hält die geschützten Ressourcen

**Grant Types:**
- Authorization Code (für Web-Apps)
- Implicit (veraltet, für SPAs)
- Client Credentials (für Server-to-Server)
- Resource Owner Password (veraltet)
- PKCE (für mobile/native Apps)

**Vorteile:**
- Weit verbreitet (Google, Facebook, GitHub, etc.)
- Keine Weitergabe von Credentials an Dritte
- Feingranulare Berechtigungen (Scopes)
- Standardisiert und gut unterstützt

**Nachteile:**
- Komplexe Spezifikation
- Ist kein Authentifizierungsprotokoll per se
- Token-Management erforderlich
- Verschiedene Implementierungen können inkompatibel sein

---

### 1.5 Security Assertion Markup Language (SAML 2.0)

**Definition:** SAML ist ein XML-basierter Standard für den Austausch von Authentifizierungs- und Autorisierungsdaten zwischen Parteien, insbesondere zwischen Identity Provider (IdP) und Service Provider (SP).

**Komponenten:**
- **Assertions:** XML-Dokumente mit Sicherheitsinformationen
- **Protocols:** Request/Response-Formate
- **Bindings:** Wie SAML über Transport-Protokolle übertragen wird
- **Profiles:** Kombinationen von Assertions, Protocols und Bindings

**Funktionsweise (SP-initiated):**
1. Benutzer greift auf SP zu
2. SP leitet zu IdP weiter (AuthnRequest)
3. Benutzer authentifiziert sich beim IdP
4. IdP sendet SAML-Response (Assertion) an SP
5. SP validiert Assertion und gewährt Zugriff

**Vorteile:**
- Etablierter Enterprise-Standard
- Starke Sicherheit durch XML-Signaturen
- Gut für B2B-Integrationen
- Unterstützt komplexe Attribute

**Nachteile:**
- XML-basiert (verbose, komplex)
- Schwierig für mobile/native Apps
- Höherer Implementierungsaufwand als OAuth/OIDC
- Weniger geeignet für moderne Web-APIs

---

### 1.6 Kerberos

**Definition:** Kerberos ist ein Netzwerk-Authentifizierungsprotokoll, das auf symmetrischer Kryptographie basiert und eine vertrauenswürdige dritte Partei (KDC) nutzt.

**Komponenten:**
- **Key Distribution Center (KDC):** Zentrale Autorität
  - Authentication Server (AS)
  - Ticket Granting Server (TGS)
- **Principals:** Benutzer und Dienste
- **Tickets:** TGT und Service Tickets
- **Realm:** Administrativer Bereich

**Funktionsweise:**
1. Benutzer authentifiziert sich beim AS
2. AS stellt TGT aus
3. Benutzer präsentiert TGT beim TGS
4. TGS stellt Service Ticket aus
5. Benutzer präsentiert Service Ticket beim Dienst

**Vorteile:**
- Passwort wird nie über das Netzwerk gesendet
- Gegenseitige Authentifizierung
- Zeitlich begrenzte Tickets
- Standard in Windows-Domänen (Active Directory)

**Nachteile:**
- Komplexe Einrichtung
- Zeitsynchronisation kritisch
- Single Point of Failure (KDC)
- Primär für interne Netzwerke

---

## 2. Vergleichsmatrix

| Kriterium | SSO (allg.) | CAS | OAuth 2.0 | SAML 2.0 | Kerberos |
|-----------|-------------|-----|-----------|----------|----------|
| **Hauptzweck** | Einmaliges Login | Web-SSO | Autorisierung | SSO/Federation | Netzwerk-Auth |
| **Protokoll-Typ** | Konzept | Web-Protokoll | HTTP-basiert | XML/HTTP | Binär |
| **Token-Format** | variiert | Service Ticket | JWT/Opaque | XML Assertion | Binär-Ticket |
| **Web-Apps** | ++ | ++ | ++ | ++ | + |
| **Mobile Apps** | + | - | ++ | - | -- |
| **Microservices** | + | + | ++ | + | - |
| **Enterprise** | ++ | ++ | + | ++ | ++ |
| **Komplexität** | mittel | mittel | mittel | hoch | hoch |
| **Social Login** | - | - | ++ | - | - |

**Legende:** ++ sehr gut | + gut | - weniger geeignet | -- nicht geeignet

---

## 3. Empfehlungen nach Einsatzgebiet

### 3.1 Social Login / Consumer Apps
**Empfehlung: OAuth 2.0 + OpenID Connect**
- Weit verbreitet bei Google, Facebook, GitHub
- Gute SDK-Unterstützung
- Ideal für Web und Mobile

### 3.2 Enterprise / Intranet
**Empfehlung: SAML 2.0 oder Kerberos (via AD)**
- Integration mit Active Directory
- Etablierte Standards
- Hohe Sicherheitsanforderungen erfüllt

### 3.3 Microservices-Architektur
**Empfehlung: OAuth 2.0 + JWT**
- Stateless Token-Validierung
- Gute Skalierbarkeit
- Service-to-Service-Auth möglich

### 3.4 Legacy-Integration
**Empfehlung: CAS**
- Flexible Backend-Anbindung
- Gute Java/Spring-Integration
- Einfache Migration bestehender Systeme

---

## 4. Anbindung an die Anforderungen

### Anforderung 1: Anbindung an bestehende Auth-Services
| Technologie | Eignung | Begründung |
|-------------|---------|------------|
| OAuth 2.0 | Hoch | Standardisierte Provider-Anbindung |
| SAML 2.0 | Hoch | Enterprise-IdP-Integration |
| CAS | Mittel | LDAP/AD-Backend möglich |
| Kerberos | Hoch | Native AD-Integration |

### Anforderung 2: Token-Authentifizierung (SSO)
| Technologie | Eignung | Begründung |
|-------------|---------|------------|
| OAuth 2.0 | Hoch | Access/Refresh Tokens |
| SAML 2.0 | Mittel | Session-basiert |
| CAS | Mittel | Service Tickets |
| Kerberos | Hoch | TGT für Session |

### Anforderung 3: Multi-Device/Multi-Platform
| Technologie | Eignung | Begründung |
|-------------|---------|------------|
| OAuth 2.0 | Hoch | REST-basiert, SDKs verfügbar |
| SAML 2.0 | Niedrig | Browser-abhängig |
| CAS | Mittel | Primär Web |
| Kerberos | Niedrig | Netzwerk-gebunden |

---

## 5. Quellenverzeichnis

1. **RFC 6749 - The OAuth 2.0 Authorization Framework**
   https://datatracker.ietf.org/doc/html/rfc6749

2. **OASIS SAML 2.0 Specification**
   https://docs.oasis-open.org/security/saml/v2.0/

3. **Apereo CAS Documentation**
   https://apereo.github.io/cas/

4. **MIT Kerberos Documentation**
   https://web.mit.edu/kerberos/

5. **OpenID Connect Core 1.0**
   https://openid.net/specs/openid-connect-core-1_0.html

6. **Microsoft - Kerberos Authentication Overview**
   https://docs.microsoft.com/en-us/windows-server/security/kerberos/kerberos-authentication-overview

7. **Spring Security Reference**
   https://docs.spring.io/spring-security/reference/

8. **NIST SP 800-63B - Digital Identity Guidelines**
   https://pages.nist.gov/800-63-3/sp800-63b.html
