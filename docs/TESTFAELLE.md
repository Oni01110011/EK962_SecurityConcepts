# Testfaelle und Testergebnisse

## Autor
- Datum: 2026-01-14
- Aufgabe: EK9.6 Middleware Engineering "Security Concepts"

---

## 1. Social Login Prototyp - Testfaelle

### Test 1.1: Startseite aufrufen (oeffentlich)

| Attribut | Wert |
|----------|------|
| **Test-ID** | SL-001 |
| **Beschreibung** | Startseite ohne Authentifizierung aufrufen |
| **Vorbedingung** | Anwendung laeuft auf Port 8080, Benutzer nicht angemeldet |
| **Testschritte** | 1. Browser oeffnen<br>2. http://localhost:8080/ aufrufen |
| **Erwartetes Ergebnis** | Startseite wird angezeigt mit "Anmelden"-Button |
| **Tatsaechliches Ergebnis** | *(bei Durchfuehrung ausfuellen)* |
| **Status** | *(PASS/FAIL)* |

### Test 1.2: Login-Seite aufrufen

| Attribut | Wert |
|----------|------|
| **Test-ID** | SL-002 |
| **Beschreibung** | Login-Seite mit OAuth2-Provider-Auswahl anzeigen |
| **Vorbedingung** | Anwendung laeuft, Benutzer nicht angemeldet |
| **Testschritte** | 1. http://localhost:8080/login aufrufen |
| **Erwartetes Ergebnis** | Login-Seite mit Google und GitHub Buttons |
| **Tatsaechliches Ergebnis** | *(bei Durchfuehrung ausfuellen)* |
| **Status** | *(PASS/FAIL)* |

### Test 1.3: Google OAuth2 Login

| Attribut | Wert |
|----------|------|
| **Test-ID** | SL-003 |
| **Beschreibung** | Authentifizierung mit Google-Account |
| **Vorbedingung** | Google OAuth2 Credentials konfiguriert, gueltiger Google-Account |
| **Testschritte** | 1. Login-Seite oeffnen<br>2. "Mit Google anmelden" klicken<br>3. Google-Credentials eingeben<br>4. Zugriff erlauben |
| **Erwartetes Ergebnis** | Weiterleitung zum Dashboard, Benutzername und Profilbild angezeigt |
| **Tatsaechliches Ergebnis** | *(bei Durchfuehrung ausfuellen)* |
| **Status** | *(PASS/FAIL)* |

### Test 1.4: GitHub OAuth2 Login

| Attribut | Wert |
|----------|------|
| **Test-ID** | SL-004 |
| **Beschreibung** | Authentifizierung mit GitHub-Account |
| **Vorbedingung** | GitHub OAuth2 Credentials konfiguriert, gueltiger GitHub-Account |
| **Testschritte** | 1. Login-Seite oeffnen<br>2. "Mit GitHub anmelden" klicken<br>3. GitHub-Credentials eingeben<br>4. "Authorize" klicken |
| **Erwartetes Ergebnis** | Weiterleitung zum Dashboard, GitHub-Benutzername angezeigt |
| **Tatsaechliches Ergebnis** | *(bei Durchfuehrung ausfuellen)* |
| **Status** | *(PASS/FAIL)* |

### Test 1.5: Geschuetzten Bereich ohne Login aufrufen

| Attribut | Wert |
|----------|------|
| **Test-ID** | SL-005 |
| **Beschreibung** | Zugriff auf Dashboard ohne Authentifizierung |
| **Vorbedingung** | Benutzer nicht angemeldet |
| **Testschritte** | 1. http://localhost:8080/dashboard direkt aufrufen |
| **Erwartetes Ergebnis** | Automatische Weiterleitung zur Login-Seite |
| **Tatsaechliches Ergebnis** | *(bei Durchfuehrung ausfuellen)* |
| **Status** | *(PASS/FAIL)* |

### Test 1.6: Dashboard nach Login

| Attribut | Wert |
|----------|------|
| **Test-ID** | SL-006 |
| **Beschreibung** | Dashboard-Zugriff nach erfolgreicher Authentifizierung |
| **Vorbedingung** | Benutzer angemeldet (Google oder GitHub) |
| **Testschritte** | 1. Nach Login auf Dashboard pruefen |
| **Erwartetes Ergebnis** | Dashboard wird angezeigt mit:<br>- Benutzername<br>- Profilbild (wenn verfuegbar)<br>- OAuth2-Attribute<br>- Demo-Statistiken |
| **Tatsaechliches Ergebnis** | *(bei Durchfuehrung ausfuellen)* |
| **Status** | *(PASS/FAIL)* |

### Test 1.7: Logout-Funktionalitaet

| Attribut | Wert |
|----------|------|
| **Test-ID** | SL-007 |
| **Beschreibung** | Abmelden und Session beenden |
| **Vorbedingung** | Benutzer angemeldet |
| **Testschritte** | 1. "Abmelden"-Button klicken<br>2. Versuchen /dashboard aufzurufen |
| **Erwartetes Ergebnis** | 1. Weiterleitung zur Startseite<br>2. Dashboard nicht mehr zugaenglich, Weiterleitung zu Login |
| **Tatsaechliches Ergebnis** | *(bei Durchfuehrung ausfuellen)* |
| **Status** | *(PASS/FAIL)* |

### Test 1.8: Fehlerhafte OAuth2-Credentials

| Attribut | Wert |
|----------|------|
| **Test-ID** | SL-008 |
| **Beschreibung** | Login mit ungueltigem OAuth2-Flow |
| **Vorbedingung** | OAuth2 Provider nicht erreichbar oder Credentials falsch |
| **Testschritte** | 1. Login versuchen mit falsch konfigurierten Credentials |
| **Erwartetes Ergebnis** | Fehlermeldung auf Login-Seite, keine Weiterleitung zum Dashboard |
| **Tatsaechliches Ergebnis** | *(bei Durchfuehrung ausfuellen)* |
| **Status** | *(PASS/FAIL)* |

---

## 2. Active Directory Prototyp - Testfaelle

### Test 2.1: Startseite aufrufen (oeffentlich)

| Attribut | Wert |
|----------|------|
| **Test-ID** | AD-001 |
| **Beschreibung** | Startseite ohne Authentifizierung aufrufen |
| **Vorbedingung** | Anwendung laeuft auf Port 8081, Benutzer nicht angemeldet |
| **Testschritte** | 1. Browser oeffnen<br>2. http://localhost:8081/ aufrufen |
| **Erwartetes Ergebnis** | Startseite mit "Mit AD anmelden"-Button |
| **Tatsaechliches Ergebnis** | *(bei Durchfuehrung ausfuellen)* |
| **Status** | *(PASS/FAIL)* |

### Test 2.2: Login mit gueltigem AD-Account

| Attribut | Wert |
|----------|------|
| **Test-ID** | AD-002 |
| **Beschreibung** | Authentifizierung mit AD-Credentials (normaler Benutzer) |
| **Vorbedingung** | AD-Server erreichbar (dc-01.tgm.ac.at), gueltiger AD-Account ohne Admin-Rechte |
| **Testschritte** | 1. Login-Seite oeffnen<br>2. AD-Benutzername eingeben (z.B. "mmustermann")<br>3. AD-Passwort eingeben<br>4. "Anmelden" klicken |
| **Erwartetes Ergebnis** | Weiterleitung zum Dashboard, AD-Gruppen als Rollen angezeigt |
| **Tatsaechliches Ergebnis** | *(bei Durchfuehrung ausfuellen)* |
| **Status** | *(PASS/FAIL)* |

### Test 2.3: Login mit ungueltigem Passwort

| Attribut | Wert |
|----------|------|
| **Test-ID** | AD-003 |
| **Beschreibung** | Authentifizierung mit falschem Passwort |
| **Vorbedingung** | AD-Server erreichbar |
| **Testschritte** | 1. Login-Seite oeffnen<br>2. Gueltigen Benutzernamen eingeben<br>3. Falsches Passwort eingeben<br>4. "Anmelden" klicken |
| **Erwartetes Ergebnis** | Fehlermeldung "Anmeldung fehlgeschlagen", keine Weiterleitung |
| **Tatsaechliches Ergebnis** | *(bei Durchfuehrung ausfuellen)* |
| **Status** | *(PASS/FAIL)* |

### Test 2.4: User-Bereich als normaler Benutzer

| Attribut | Wert |
|----------|------|
| **Test-ID** | AD-004 |
| **Beschreibung** | Zugriff auf User-Bereich mit normalem AD-Account |
| **Vorbedingung** | Angemeldet als normaler AD-Benutzer (keine Admin-Gruppe) |
| **Testschritte** | 1. /user/profile aufrufen<br>2. /user/windpark aufrufen |
| **Erwartetes Ergebnis** | Beide Seiten werden angezeigt, Benutzerinformationen sichtbar |
| **Tatsaechliches Ergebnis** | *(bei Durchfuehrung ausfuellen)* |
| **Status** | *(PASS/FAIL)* |

### Test 2.5: Admin-Bereich als normaler Benutzer (Zugriff verweigert)

| Attribut | Wert |
|----------|------|
| **Test-ID** | AD-005 |
| **Beschreibung** | Zugriff auf Admin-Bereich ohne Admin-Rechte |
| **Vorbedingung** | Angemeldet als normaler AD-Benutzer (keine Admin-Gruppe) |
| **Testschritte** | 1. /admin/dashboard direkt aufrufen<br>2. /admin/users direkt aufrufen |
| **Erwartetes Ergebnis** | HTTP 403 Forbidden oder Weiterleitung zur Fehlerseite |
| **Tatsaechliches Ergebnis** | *(bei Durchfuehrung ausfuellen)* |
| **Status** | *(PASS/FAIL)* |

### Test 2.6: Admin-Bereich als Administrator

| Attribut | Wert |
|----------|------|
| **Test-ID** | AD-006 |
| **Beschreibung** | Zugriff auf Admin-Bereich mit Admin-Rechten |
| **Vorbedingung** | Angemeldet als AD-Benutzer mit Admin-Gruppe (z.B. "Administratoren") |
| **Testschritte** | 1. /admin/dashboard aufrufen<br>2. /admin/users aufrufen |
| **Erwartetes Ergebnis** | Beide Admin-Seiten werden angezeigt, Admin-Rollen sichtbar |
| **Tatsaechliches Ergebnis** | *(bei Durchfuehrung ausfuellen)* |
| **Status** | *(PASS/FAIL)* |

### Test 2.7: Rollen-Anzeige im Dashboard

| Attribut | Wert |
|----------|------|
| **Test-ID** | AD-007 |
| **Beschreibung** | AD-Gruppen werden als Rollen angezeigt |
| **Vorbedingung** | Angemeldet mit AD-Account |
| **Testschritte** | 1. Dashboard aufrufen<br>2. Abschnitt "Ihre Rollen" pruefen |
| **Erwartetes Ergebnis** | AD-Gruppenmitgliedschaften als ROLE_* angezeigt |
| **Tatsaechliches Ergebnis** | *(bei Durchfuehrung ausfuellen)* |
| **Status** | *(PASS/FAIL)* |

### Test 2.8: AD-Server nicht erreichbar

| Attribut | Wert |
|----------|------|
| **Test-ID** | AD-008 |
| **Beschreibung** | Verhalten bei nicht erreichbarem AD-Server |
| **Vorbedingung** | LDAP_URL zeigt auf nicht erreichbaren Server |
| **Testschritte** | 1. Login versuchen |
| **Erwartetes Ergebnis** | Fehlermeldung, keine Endlosschleife, saubere Fehlerbehandlung |
| **Tatsaechliches Ergebnis** | *(bei Durchfuehrung ausfuellen)* |
| **Status** | *(PASS/FAIL)* |

### Test 2.9: Logout und Session-Invalidierung

| Attribut | Wert |
|----------|------|
| **Test-ID** | AD-009 |
| **Beschreibung** | Abmelden und Session beenden |
| **Vorbedingung** | Benutzer angemeldet |
| **Testschritte** | 1. "Abmelden" klicken<br>2. /admin/dashboard oder /user/profile aufrufen |
| **Erwartetes Ergebnis** | 1. Erfolgsmeldung auf Login-Seite<br>2. Weiterleitung zu Login, kein Zugriff mehr |
| **Tatsaechliches Ergebnis** | *(bei Durchfuehrung ausfuellen)* |
| **Status** | *(PASS/FAIL)* |

### Test 2.10: Unterschiedliche AD-Benutzer mit verschiedenen Gruppen

| Attribut | Wert |
|----------|------|
| **Test-ID** | AD-010 |
| **Beschreibung** | Verschiedene Benutzer haben unterschiedliche Berechtigungen |
| **Vorbedingung** | Zwei AD-Accounts: einer mit Admin-Gruppe, einer ohne |
| **Testschritte** | 1. Mit Admin-Account anmelden -> Admin-Bereich testen<br>2. Abmelden<br>3. Mit Normal-Account anmelden -> Admin-Bereich testen |
| **Erwartetes Ergebnis** | Admin: Zugriff auf /admin/**<br>Normal: Kein Zugriff auf /admin/** |
| **Tatsaechliches Ergebnis** | *(bei Durchfuehrung ausfuellen)* |
| **Status** | *(PASS/FAIL)* |

---

## 3. Testumgebung

### 3.1 Social Login Prototyp

| Komponente | Version/Konfiguration |
|------------|----------------------|
| Java | 17+ |
| Spring Boot | 3.2.0 |
| Port | 8080 |
| OAuth2 Provider | Google, GitHub |

### 3.2 AD Prototyp

| Komponente | Version/Konfiguration |
|------------|----------------------|
| Java | 17+ |
| Spring Boot | 3.2.0 |
| Port | 8081 |
| AD Server | dc-01.tgm.ac.at:389 |
| Domain | tgm.ac.at |

---

## 4. Testprotokoll-Vorlage

| Testdurchfuehrung | |
|-------------------|---|
| Datum | _____________ |
| Tester | _____________ |
| Umgebung | _____________ |

### Zusammenfassung

| Kategorie | Gesamt | Bestanden | Fehlgeschlagen |
|-----------|--------|-----------|----------------|
| Social Login | 8 | | |
| AD Auth | 10 | | |
| **Gesamt** | **18** | | |

### Anmerkungen

*(Besonderheiten, Probleme, Verbesserungsvorschlaege)*

---

## 5. Testanleitung

### Social Login testen

1. **Vorbereitung:**
   ```bash
   # Umgebungsvariablen setzen
   export GOOGLE_CLIENT_ID=your-id
   export GOOGLE_CLIENT_SECRET=your-secret
   export GITHUB_CLIENT_ID=your-id
   export GITHUB_CLIENT_SECRET=your-secret
   ```

2. **Anwendung starten:**
   ```bash
   cd social-login-prototype
   ./gradlew bootRun
   ```

3. **Tests durchfuehren:**
   - Browser oeffnen: http://localhost:8080
   - Testfaelle SL-001 bis SL-008 durchfuehren

### AD Auth testen

1. **Vorbereitung:**
   ```bash
   # Umgebungsvariablen setzen (oder defaults verwenden)
   export LDAP_DOMAIN=tgm.ac.at
   export LDAP_URL=ldap://dc-01.tgm.ac.at:389
   ```

2. **Anwendung starten:**
   ```bash
   cd ad-prototype
   ./gradlew bootRun
   ```

3. **Tests durchfuehren:**
   - Browser oeffnen: http://localhost:8081
   - Testfaelle AD-001 bis AD-010 durchfuehren
   - Verschiedene AD-Accounts verwenden (Admin und Normal)
