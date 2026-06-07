# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project: Android SMS Sender

Android app that sends the SMS `NL2000 AAN` to `1266` when triggered by Samsung Routines (Modes and Routines) via the "Open an app" action.

The app opens, sends the SMS, shows a brief Toast, and closes automatically.

### Build

```
cd android-sms-sender
./gradlew assembleDebug
```

Install on device:
```
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Key Files

- `app/src/main/java/com/ntilau/smssender/MainActivity.kt` — Single Activity: checks permission, sends SMS, finishes
- `app/src/main/AndroidManifest.xml` — Declares `SEND_SMS` permission; Activity uses translucent theme for near-invisible launch
- `app/build.gradle.kts` — `minSdk = 26`, `compileSdk = 34`, Kotlin + AppCompat

### Samsung Routines Setup

1. Install the APK and grant SMS permission on first manual launch
2. In Samsung Routines, add: **Then** → "Open an app" → select "SMS Sender"
3. The routine will launch the app, which sends the SMS and exits
