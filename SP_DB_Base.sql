DROP DATABASE IF EXISTS streaming_platform;
CREATE DATABASE streaming_platform;
USE streaming_platform;

CREATE TABLE User (
    UserID VARCHAR(45),
    Email VARCHAR(100) UNIQUE,
    Username VARCHAR(50) UNIQUE,
    Password VARCHAR(100) NOT NULL,

    PRIMARY KEY (UserID)
);

CREATE TABLE Subscription (
    SubscriptionID VARCHAR(45),
    UserID VARCHAR(45),
    StartDate DATE NOT NULL,
    EndDate DATE,
    SubscriptionType ENUM('Basic', 'Standard', 'Premium') NOT NULL,

    PRIMARY KEY (SubscriptionID),
    FOREIGN KEY (UserID) REFERENCES User(UserID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Video (
    VideoID VARCHAR(45),
    Title VARCHAR(100) NOT NULL,
    Genre VARCHAR(50),
    Description TEXT,
    Duration INT CHECK (Duration > 0),

    PRIMARY KEY (VideoID)
);

CREATE TABLE PlaybackHistory (
    HistoryID VARCHAR(45),
    UserID VARCHAR(45),
    VideoID VARCHAR(45),
    WatchedAt DATETIME NOT NULL,
    WatchedDuration INT CHECK (WatchedDuration >= 0),
    IsCompleted BOOLEAN DEFAULT FALSE,

    PRIMARY KEY (HistoryID),
    FOREIGN KEY (UserID) REFERENCES User(UserID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (VideoID) REFERENCES Video(VideoID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Liked (
    LikeID VARCHAR(45),
    UserID VARCHAR(45),
    VideoID VARCHAR(45),
    LikedAt DATETIME DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (LikeID),
    FOREIGN KEY (UserID) REFERENCES User(UserID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (VideoID) REFERENCES Video(VideoID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE(UserID, VideoID)  -- Bir kullanıcı bir videoyu yalnızca bir kez beğenebilir
);

CREATE TABLE Commented (
    CommentID VARCHAR(45),
    UserID VARCHAR(45),
    VideoID VARCHAR(45),
    CommentedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    Content TEXT CHECK (CHAR_LENGTH(Content) <= 1000),
    ParentCommentID VARCHAR(45) NULL,

    PRIMARY KEY (CommentID),
    FOREIGN KEY (UserID) REFERENCES User(UserID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (VideoID) REFERENCES Video(VideoID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (ParentCommentID) REFERENCES Commented(CommentID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Playlist (
    PlaylistID VARCHAR(45),
    Name VARCHAR(100) NOT NULL,
    UserID VARCHAR(45),
    Description TEXT,
    CreatedOn DATE,
    IsPublic BOOLEAN DEFAULT FALSE,

    PRIMARY KEY (PlaylistID),
    FOREIGN KEY (UserID) REFERENCES User(UserID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE PlaylistReferenceVideo (
    PlaylistID VARCHAR(45),
    VideoID VARCHAR(45),
    VideoOrder INT CHECK (VideoOrder >= 0),

    PRIMARY KEY (PlaylistID, VideoID),
    FOREIGN KEY (PlaylistID) REFERENCES Playlist(PlaylistID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (VideoID) REFERENCES Video(VideoID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Actor (
    ActorID VARCHAR(45),
    FirstName VARCHAR(100) NOT NULL,
    LastName VARCHAR(100) NOT NULL,
    DateOfBirth DATE,
    Biography TEXT,

    PRIMARY KEY (ActorID)
);

CREATE TABLE Video_Actor (
    VideoID VARCHAR(45),
    ActorID VARCHAR(45),
    RoleName VARCHAR(100),

    PRIMARY KEY (VideoID, ActorID),
    FOREIGN KEY (VideoID) REFERENCES Video(VideoID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (ActorID) REFERENCES Actor(ActorID)
        ON DELETE CASCADE ON UPDATE CASCADE
);
