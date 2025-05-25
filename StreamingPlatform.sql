DROP DATABASE streaming_platform;
CREATE DATABASE streaming_platform;
Show databases;
Use streaming_platform;
Show tables;

CREATE TABLE User (
UserID varchar(45),
Email varchar(45),
Username varchar(45),
Password varchar(45),

PRIMARY KEY (UserID)
);

CREATE TABLE Subscription (
SubscriptionID varchar(45) ,
UserID varchar(45),
StartDate date,
EndDate date,
SubscriptionType varchar(45),

PRIMARY KEY(SubscriptionID),
FOREIGN KEY (UserID) references User(UserID)
);

CREATE TABLE Video (
VideoID varchar(45),
Title varchar(45),
Genre varchar(45),
Description varchar(45),
Duration int,

 PRIMARY KEY (VideoID)
);

CREATE TABLE PlaybackHistory (
HistoryID VARCHAR(45),
UserID VARCHAR(45),
VideoID VARCHAR(45),
WatchedAt DATETIME,
WatchedDuration INT,
IsCompleted BOOLEAN, 
PRIMARY KEY (HistoryID),
FOREIGN KEY (UserID) REFERENCES User(UserID),
FOREIGN KEY (VideoID) REFERENCES Video(VideoID)
 );

CREATE TABLE Liked (
LikeID VARCHAR(45) ,
UserID VARCHAR(45),   -- FK to User
VideoID VARCHAR(45),  -- FK to Video
LikedAt DATETIME,
  
PRIMARY KEY (LikeID),
FOREIGN KEY (UserID) REFERENCES User(UserID),
FOREIGN KEY (VideoID) REFERENCES Video(VideoID)
);

CREATE TABLE Commented (
CommentID VARCHAR(45) ,
UserID VARCHAR(45),   -- FK to User
VideoID VARCHAR(45),  -- FK to Video
CommentedAt DATETIME,
Content TEXT,
ParentCommentID VARCHAR(45) NULL,
  
PRIMARY KEY (CommentID),
FOREIGN KEY (UserID) REFERENCES User(UserID),
FOREIGN KEY (VideoID) REFERENCES Video(VideoID)
);

CREATE TABLE Playlist (
PlaylistID VARCHAR(45),
Name VARCHAR(100) NOT NULL,
UserID VARCHAR(45),
Description TEXT,
CreatedOn DATE,
IsPublic BOOLEAN DEFAULT FALSE,

PRIMARY KEY(PlaylistID),
FOREIGN KEY (UserID) REFERENCES User(UserID)
);
                       
Create TABLE PlaylistReferenceVideo(
PlaylistID VARCHAR(45),
VideoID VARCHAR(45),
VideoOrder INT,

PRIMARY KEY (PlaylistID, VideoID),
FOREIGN KEY (PlaylistID) REFERENCES Playlist(PlaylistID),
FOREIGN KEY (VideoID) REFERENCES Video(VideoID)
);

CREATE TABLE Actor (
ActorID VARCHAR(45),
FirstName VARCHAR(100) NOT NULL,
LastName VARCHAR(100) NOT NULL,
DateOfBirth DATE,
Biography TEXT,

 PRIMARY KEY(ActorID)
);

CREATE TABLE Video_Actor (
VideoID VARCHAR(45),
ActorID VARCHAR(45),
RoleName VARCHAR(100), -- Oyuncunun filmdeki rol adı (isteğe bağlı)

PRIMARY KEY (VideoID, ActorID),
FOREIGN KEY (VideoID) REFERENCES Video(VideoID),
FOREIGN KEY (ActorID) REFERENCES Actor(ActorID)
);


                             