# Task Manager App

This project is a task manager application designed to help users efficiently manage their tasks. Users can create, edit, delete, and update tasks while tracking their status to determine whether they are completed or pending. The application provides a user-friendly interface to view tasks sorted by time and status, making it easy to prioritize and manage tasks effectively.

## App Features 🎯

- **Login and Signup**: Users can register and login with their email and password. User data is stored locally.
- **Create Task**: Add new tasks with details like name, description, and due date.
- **Edit Task**: Modify existing tasks to update their information.
- **Delete Task**: Remove tasks that are no longer needed.
- **Update Task Status**: Mark tasks as completed or pending.
- **View Tasks**: See a list of all tasks along with their status.
- **Sort Tasks**: View tasks sorted by time and status to prioritize effectively.

## Technologies Used 🔩

- **Kotlin Multiplatform (KMP)**: For building cross-platform applications.
- **Compose**: Utilise the Compose UI for iOS and Android.
- **SQLDelight**: For database handling.
- **Settings**: For managing user preferences and settings.
- **PreCompose**: For creating user interfaces.
- **Navigation**: For managing screen navigation.
- **Flow**: For reactive programming.
- **ViewModel**: For managing UI-related data.
- **Koin**: For dependency injection.

## Architecture

The app follows the MVVM (Model-View-ViewModel) architecture:

- **Model**: Handles the data layer using SQLDelight for database operations.
- **View**: Composes UI components using PreCompose and Jetpack Compose.
- **ViewModel**: Manages UI-related data and handles business logic using Flow and LiveData.

## Installation

### Android

1. **Clone the repository:**

   ```sh
     git clone https://github.com/gaddarkumar7447/PersonalTask.git
     cd PersonalTask

2. **Open the project in Android Studio:**

  - Launch Android Studio.
  - Select "Open an existing Android Studio project".
  - Navigate to the cloned repository and select the PersonalTask directory.

3. **Sync the project with Gradle:**

  - Android Studio should automatically prompt you to sync the project.
  - If not, click on "File" > "Sync Project with Gradle Files".

4. **Set up an Android device or emulator:**

  - Connect your Android device via USB, or
  - Set up an Android emulator by clicking on "AVD Manager" in Android Studio and following the prompts to create a new virtual device.
  
5. **Run the app:**

  - Click on the "Run" button (green play icon) in Android Studio.
  - Select your device or emulator from the list of connected devices.



### iOS
1. **Clone the repository:**

   ```sh
     git clone https://github.com/gaddarkumar7447/PersonalTask.git
     cd PersonalTask


2. **Install required tools:**

  - Ensure you have Xcode installed. You can download it from the Mac App Store.
  
  - Install CocoaPods if not already installed:


     ```sh
       sudo gem install cocoapods

3. **Open the iOS project in Xcode:**

  - Navigate to the iosApp directory within the cloned repository:

     ```sh
       cd iosApp
   
  - Install CocoaPods dependencies:

      ```sh
       pod install
    
  - Open the .xcworkspace file in Xcode:
  
    ```sh
      open iosApp.xcworkspace


4. **Set up an iOS device or simulator:**

  - Connect your iOS device via USB, or
  - Set up an iOS simulator by selecting your target device from the top toolbar in Xcode.
  
5. **Run the app:**

  - Click on the "Run" button (play icon) in Xcode.
  - Select your device or simulator from the list of available targets.

## Common Issues
  - Gradle sync issues in Android Studio: Ensure you have the correct version of Android Studio and all required SDK components installed.
  - CocoaPods issues in Xcode: Ensure CocoaPods is installed and updated. Run pod repo update if necessary.
  - Device connection issues: Ensure your device is properly connected and recognized by your development environment.


