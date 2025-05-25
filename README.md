# Streaming Platform Application

## Overview
The Streaming Platform Application is a Java-based application that simulates a video streaming platform similar to YouTube. Users can log in, view videos, comment, like videos, create playlists, and explore actors and their associated videos.

## Project Structure
The project is organized into several packages, each serving a specific purpose:

- **src**: Contains the source code for the application.
  - **Main.java**: Entry point of the application.
  - **db**: Contains database connection logic.
    - **DatabaseConnection.java**: Manages database connections and queries.
  - **model**: Contains data models representing the application's entities.
    - **User.java**: Represents a user in the system.
    - **Video.java**: Represents a video in the platform.
    - **Actor.java**: Represents an actor associated with videos.
    - **Playlist.java**: Represents a playlist of videos.
    - **Comment.java**: Represents a comment made by a user on a video.
  - **view**: Contains classes for the user interface.
    - **LoginFrame.java**: User login interface.
    - **MainFrame.java**: Main application window.
    - **VideoPanel.java**: Displays video details and interactions.
    - **ActorPanel.java**: Displays actor information and related videos.
    - **PlaylistPanel.java**: Manages user playlists.
  - **controller**: Contains classes that manage application logic.
    - **UserController.java**: Manages user operations.
    - **VideoController.java**: Manages video operations.
    - **ActorController.java**: Manages actor operations.
    - **PlaylistController.java**: Manages playlist operations.
    - **CommentController.java**: Manages comment operations.

- **lib**: Contains JDBC driver jars required for database connectivity.

## Setup Instructions
1. **Clone the Repository**: Clone this repository to your local machine.
2. **Install Java**: Ensure you have Java Development Kit (JDK) installed on your machine.
3. **Add JDBC Driver**: Place the JDBC driver jar files in the `lib` directory.
4. **Compile the Application**: Navigate to the `src` directory and compile the Java files.
5. **Run the Application**: Execute the `Main.java` file to start the application.

## Usage Guidelines
- **Login**: Users can log in using their credentials.
- **View Videos**: Users can browse and view videos available on the platform.
- **Comment and Like**: Users can comment on videos and like them.
- **Manage Playlists**: Users can create and manage their playlists.
- **Explore Actors**: Users can view information about actors and see other videos they have featured in.

## Contributing
Contributions are welcome! Please feel free to submit a pull request or open an issue for any enhancements or bug fixes.

## License
This project is licensed under the MIT License. See the LICENSE file for more details.