# Joy Finder

**나를 웃게 하는 순간 탐색기**라는 콘셉트로 제작된 안드로이드 앱입니다. 매일 어떤 활동에서 즐거움을 느끼는지 기록하고 분석하여 사용자가 스스로 행복 포인트를 찾아볼 수 있게 도와줍니다. 모든 데이터는 로컬에 저장되며 네트워크 연결 없이도 사용 가능합니다. 추가 분석이 필요할 때는 버튼 한 번으로 Gemini 웹사이트를 열어 미리 만들어 둔 프롬프트를 붙여 넣을 수 있습니다.

## Features

- **Activity logging** – record an activity and rate how joyful it was (1‑5).
- **Local database** – all entries are saved in a Room database.
- **Offline analysis** – compute which activities give you the highest average joy directly on your device.
- **Export** – share your logs as a CSV file with other apps.
- **Online analysis shortcut** – copy an analysis prompt to the clipboard and open Gemini in your browser.

## Building

Clone this repository and open it in Android Studio Arctic Fox or later. Build and run the `app` module on a device or emulator running Android 7.0 (API 24) or higher.

Because Gradle may not automatically download the Android plugin in restricted environments, ensure you have a local Android SDK installed.

## License

This project is released under the MIT License. You are free to modify and distribute the app. If you publish it on Google Play, please respect the license and include attribution.
