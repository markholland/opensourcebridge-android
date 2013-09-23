ocw-android
==========================

ocw-android is an open source app for Android that displs schedules for a conference.

All data is pulled from a server as JSON and follows a strict format that can be found in the README.

Once pulled from the server all info is parsed and then stored in an sqlite database.  From then on until a timeout hits the app will use this locally stored data.

When a timeout hits, a new JSON is only pulled from the server if it has been modified since the last time it was pulled, this last-modified date is stored locally.

The app works on android version from 2.1 and up, there is an unmerged branch, feature-14685, that supports all the way down to android 1.6

You can clone the code and add as an existing project in eclipse, you must have the android-sdk installed on your computer and the path to it configured in the adt-plugin.

[Installing the android sdk](http://developer.android.com/sdk/index.html)

You must have the android support library in your workspace which can be found in the extras folder of the Android SDK Manager.  
 Then in eclipse you must right-click on the project and under Android Tools click on "Add Support library...".   
 This will open the android sdk-manager and will prompt you to install the latest support library.








##Data Format
This is a description of data that the app expects.
###General information about data
1. All data is JSON
2. Dates must be formatted: YYYY-MM-DDTHH:MM:SS-TZ:00 (Where TZ is 
the time zone difference from UTC)
3. Optional fields must still be present but they can be empty.     


###Schedule
url = hostname/schedule.json   

This contains an array with all the events taking place at the conference, an event is an individual talk given by one or more speakers at one location and at one point during the conference.  

1. timeout: Time in milliseconds that have to pass before the app pulls the schedule.json from the server instead of the database when opening.  
 
An event element contains the following.  

1. event_id: A unique integer that identifies the event.
2. event_title: The title of the event.
3. start_time: The date and time that the event starts.
4. end_time: The date and time that the event ends.
5. description: Information about the event.(Shown when event is selected). *
6. room_id: Unique integer that identifies the location where the event takes place. *
7. room_title: Name or description of location for the event. *
8. track_id: Unique integer to identify the type of event. *
9. user_ids: Array of integers with the ids of the speakers at the event. *
10. presenter: Host for the event, i.e. a Q&A mediator.  
\* Are optional, the rest must be present.  

###Tracks
url = hostname/tracks.json  

Holds the information of the different tracks that an event can pertain to.  

1. timeout: Time in milliseconds that have to pass before the app pulls the tracks.json from the server instead of the database when opening.  

Each track is an array with the following.  

1. name: Title of the track.
2. id: Unique integer that identifies the track.
3. color - Main color for the track. This will be used in the event detail view.*
4. color_text - Color to use in the events list for the track title  
\* are optional although we recommend it for conferences with many events from different tracks that overlap on the timetable.  

###Speakers
url = hostname/speakers.json  

Holds the information of the different speakers at the conference.
1. timeout: Time in milliseconds that have to pass before the app pulls the speakers.json from the server instead of the database when opening.  

Each speaker is an array with the following.  

1. fullname: Full name of the speaker.
2. id: Unique integer that identifies the speaker.
3. biography: A short blurb about the speaker.
4. affiliation: Company/organisation they represent at the conference.
5. twitter : The speakers twitter handle without the ampersand @.
6. email: The public email for the speaker.
website: The speakers homepage.
7. blog: If the speaker has a separate blog address then it can go here.
8. linkedin: Full url to Linkedin profile.


##Customization
Instructions for rebranding the app for other conferences.  

###Making a separate package  

To make this a completely separate app from the OSBridge app you must move all files into a new java package. This is best done with git to maintain versioning so that you can pull in updates if needed.  

*git mv org/osb org/foo*

You must then:  

Edit all of the source files to update the package line to point to your new package.
Edit AndroidManifest.xml and update the package for the application

###logos, icon, footer image
stored in res/drawable  

1. logo.png - logo used on splash page
2. icon.png - 72x72 pixel icon for the app
3. footer.png - displayed on the bottom of all event detail pages  
 
###Strings and colors 
There are some strings and colors used for branding located:  

1. res/values/strings.xml

###Splash page 

The layout for the splash page is here if you need customization beyond just changing strings, colors, and images.  

1. res/layout/loading.xml
