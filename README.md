# Challenge Mobile Dev
### This is a mobile application developed using Kotlin and Jetpack Compose. The app demonstrates user authentication using SharedPreferences to store and retrieve user credentials, and navigation between login and home screens.  

## Features
- User authentication with token storage
- Navigation between login and home screens
- Secure storage of authentication token and username
- Dependency injection with Dagger Hilt
- Network requests with OkHttp and Retrofit
  
## Project Structure
- MainActivity.kt: The main entry point of the application, handling navigation based on authentication state.
- SecurePreferences.kt: A utility class for securely storing and retrieving user credentials.
- AuthInterceptor.kt: An OkHttp interceptor for adding the authentication token to network requests.
- LoginViewModel.kt: A ViewModel for managing the login state and handling user input.
