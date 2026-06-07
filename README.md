# NL2000 SMS Sender

Android app that sends the SMS `NL2000 AAN` to `1266` when triggered by [Samsung Routines](https://www.samsung.com/us/support/answer/ANS00087283/) (Modes and Routines) via the **"Open an app"** action.

The app opens, sends the SMS, shows a brief confirmation toast, and closes automatically — no user interaction required.

## Setup

### Install

```bash
cd android-sms-sender
./gradlew assembleDebug
adb install app/build/outputs/apk/debug/app-debug.apk
```

### First launch

Open the app manually once and grant **SMS permission** when prompted. Without this, the app will show a rationale dialog asking for permission.

### Samsung Routines

1. Open **Modes and Routines** on your Samsung device
2. Create a new routine or edit an existing one
3. Add an action: **Then** → **"Open an app"** → select **NL2000**

## Release build

Release signing is configured via environment variables:

```bash
export KEYSTORE_FILE=/path/to/release.keystore
export KEYSTORE_PASSWORD=...
export KEY_ALIAS=...
export KEY_PASSWORD=...

./gradlew assembleRelease
```

## Requirements

- Android 8.0+ (API 26)
- `SEND_SMS` permission (granted on first manual launch)
