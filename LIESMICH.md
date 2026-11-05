# Auge – Android-Bildschirmschoner

Enthält zwei Betriebsarten:
1. **Bildschirmschoner** (Android „Daydream“ / `DreamService`)
2. **Vollbild-App „Auge“** – läuft dauerhaft, Bildschirm bleibt an (für die Installation)

Die Animation ist `app/src/main/assets/auge.html`. Zum Aktualisieren einfach diese Datei ersetzen und neu bauen.
Parameter (fester Ausdruck, feste Auflösung) in `AugeWeb.java` → `PARAMS`.

## APK bauen – Weg A: GitHub (ohne eigene Installation)
1. Neues (privates) Repository auf github.com anlegen, den Inhalt dieses Ordners hochladen.
2. Reiter **Actions** → Lauf „APK bauen“ abwarten (ca. 3–5 Min.).
3. Im Lauf unter **Artifacts** „auge-apk“ herunterladen → enthält `app-debug.apk`.

## APK bauen – Weg B: Android Studio
1. Ordner in Android Studio öffnen (*File → Open*), Synchronisation abwarten.
2. *Build → Build App Bundle(s) / APK(s) → Build APK(s)*.
3. APK liegt in `app/build/outputs/apk/debug/`.

## Installieren
APK aufs Gerät kopieren und öffnen. Android fragt einmalig nach der Erlaubnis
„Installation aus unbekannten Quellen“ für die App, mit der du die APK öffnest.

## Als Bildschirmschoner aktivieren
- **Smartphone/Tablet:** *Einstellungen → Display → Bildschirmschoner* → „Auge“.
  Startet je nach Einstellung beim Laden und/oder im Dock. (Menüname je nach Hersteller verschieden.)
- **Android TV:** *Einstellungen → Geräteeinstellungen → Bildschirmschoner* → „Auge“.
- **Google TV** (blendet fremde Bildschirmschoner im Menü teils aus) – per ADB vom Rechner:
  `adb shell settings put secure screensaver_components de.auge.bildschirmschoner/.AugeDream`

## Für die Installation (Dauerbetrieb)
Die App „Auge“ direkt starten. Für Ausstellungen empfiehlt sich zusätzlich
*Bildschirmfixierung / App anheften* (Einstellungen → Sicherheit), damit Besucher die App nicht verlassen.
