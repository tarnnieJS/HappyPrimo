# HappyPrimo Android Application

This is the README.md for the HappyPrimo Android application. HappyPrimo is an Android application that displays Medium articles from the feed of @primoapp. It allows users to view articles, see detailed information about them, and store them locally for offline viewing.

## Features

- Display homepage with Medium article cards from the feed of [@primoapp](https://medium.com/feed/@primoapp).
- Clicking on a card opens the detailed article page.
- Medium feed data is stored locally in the application's database.
- When the homepage is opened:
  - If data exists in the local database:
    - Load data from the local database and display it on the home screen.
    - Load data from the Medium feed and update the screen and the local database.
  - If data does not exist in the local database:
    - Load data from the Medium feed.
    - Update the screen and the local database with the retrieved data.

## Technology Stack

- **Technology**: Kotlin native
- **Architecture**: MVVM
- **Single Activity**: Utilizes a single activity with a navigation graph.

## Dependencies

The project uses the following dependencies:

- **AndroidX Libraries**: Core, AppCompat, ConstraintLayout, Lifecycle (LiveData, ViewModel), Navigation, Test, Espresso.
- **DI**: Koin for dependency injection.
- **Image and Animation**: Lottie for animation, Glide for image loading.
- **Moshi**: For JSON parsing.
- **OkHttp**: Networking library.
- **Coroutines**: For asynchronous programming.
- **Material Components**: Material design components.
- **Retrofit**: For making HTTP requests.

## Usage

To use the application, simply clone the repository and open it in Android Studio. Run the application on an Android device or emulator.

## Installation

To install the application on a device:
1. Build the APK using Android Studio.
2. Transfer the APK file to your Android device.
3. Open the APK file on your device and follow the installation instructions.

## Contributing

Contributions to the project are welcome. If you'd like to contribute, please fork the repository and create a pull request with your changes.

## License

This project is licensed under the [MIT License](LICENSE).
