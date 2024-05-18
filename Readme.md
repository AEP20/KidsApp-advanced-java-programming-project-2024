# CMP2004 Advanced Java Programming Project Spring 2024

## About the Project
KidsApp is an Android application developed for children aged 7-10. This application teaches children how to read analog and digital clocks, learn about seasons, directions, and spell words. Additionally, it teaches children multiplication and provides positive feedback after each correct answer. The app supports both Turkish and English.

## Installation and Setup

### Requirements
- Android Studio must be installed.
- A MongoDB database should be set up for the application to work.

### Installation
1. Download or clone the project files:

2. Open Android Studio and import the project:
    - Select `File` > `Open` and choose the project directory.
    - Download the necessary SDK and dependencies.

### Starting the Application
1. Compile the project in Android Studio and run it on an emulator or a physical device:
    - Select `Run` > `Run 'app'`.

### Application Features
- Users can register and log in to the system. User information is stored in MongoDB.
- Teaches users to read analog (e.g., 10:05) and digital (e.g., 10:15) clocks.
- Teaches users about seasons (autumn, winter, summer, spring) with multimedia animations.
- Asks users to spell/write words correctly.
- Teaches users directions (left, right, in front of, behind, etc.).
- Teaches users multiplication (e.g., 6 * 7 = 42).
- Provides positive feedback for each correct answer.
- Users can log out and be redirected to the login screen.
- Users can choose the app language as Turkish or English.

### Application Flow
1. **Login and Registration:** When users first open the app, they can register or log in with existing information. During this process, user information is saved or verified in the MongoDB database, and stored in the `LoginViewModel` class.
2. **Home Screen:** After logging in, users are directed to the home screen, where they can access the educational modules offered by the app.
3. **Educational Modules:**
   - **Clock Learning:** Users learn to read analog and digital clocks.
   - **Seasons:** Users learn about seasons with multimedia animations.
   - **Directions:** Users learn directions (left, right, in front of, behind, etc.).
   - **Multiplication:** Users learn multiplication (e.g., 6 * 7 = 42).
4. **Feedback:** Positive feedback is given to users for each correct answer, encouraging the learning process.
5. **Logout:** Users can log out, which deletes their information and redirects them to the login screen.
6. **Language Selection:** Users can choose the app language as Turkish or English.

### Server Side
The server side of the application manages user registration and login processes, as well as data related to educational modules. The server interacts with the MongoDB database to securely store user data. It also provides the necessary data (questions, answers, educational content) required by the application.

#### Server API Usage
The operations and API requests performed on the server side are as follows:
- **Login:** A POST request is sent to the `/api/auth/login` endpoint for user login. This request includes the username and password information with the `LoginRequest` object.
- **Registration:** A POST request is sent to the `/api/auth/register` endpoint for new user registration. This request also includes the username and password information with the `LoginRequest` object.
- **Fetching Questions:** A GET request is sent to the `/api/quiz/questions` endpoint to fetch questions required for educational modules. This request specifies the type and language parameters to indicate which type and language of questions to fetch.

### Libraries Used
- **Retrofit:** An HTTP client used to make API requests. This library is used for server communication and handling JSON data.
- **Navigation Component:** A library used for navigating between different screens in the application.
- **Glide:** An image loading library used for loading and displaying images.

## Contributors
- https://github.com/AEP20 (Ahmet Emre Parmaksız)
- https://github.com/denizozm (Deniz Özmen)
- https://github.com/Midzer00 (Ömer Dursun)