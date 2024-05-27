# DrunkTester

DrunkTester is an Android application designed to determine the level of inebriation by analyzing accelerometer and voice data. This project involves recording accelerometer data while the user stays still and recording voice data for analysis.

## Features

- Record accelerometer data for 10 seconds.
- Record voice data for 5 seconds.
- Analyze the collected data to determine the probability of being drunk.
- Display the analysis result to the user.

## Setup

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/DrunkTester.git
    ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the application on an Android device or emulator.

## Permissions

Ensure the following permissions are granted:
- `RECORD_AUDIO`
- `BODY_SENSORS`
- `INTERNET`

## Usage

1. Launch the app.
2. Click on "Begin Test" to start the accelerometer data recording.
3. After recording the accelerometer data, proceed to the voice recording.
4. Analyze the recorded data to get the result.

## Contributing

Feel free to fork this project, submit issues, or contribute pull requests.

## License

This project is licensed under the GIT License. See the [LICENSE](LICENSE) file for more details.
