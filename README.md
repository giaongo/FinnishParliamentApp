# Finnish Parliament App
This personal app project is created as a part of an OOP course belonging to Metropolia University Of Applied Sciences. 
This app is for users to browse Finnish Parliament parties, view member info, and add reviews. Besides that, there is also the possibility of adding members to the favorite list, sending emails to specific members, and viewing party statistics. 

## Tables of contents
* [App Features](#app-features)
* [In-app technology](#in-app-technology)
* [App Demo](#app-demo)

## App Features
1. Browse parties and members information. 
1. View member's reviews and their star rating based on the listed reviews.
1. Add, Update and Delete reviews for each members.
1. Send emails to Parliament members.
1. Add members to favorite list and view favorite list.
1. View statistic of party in Finnish parliament.

## In-app technology
This Android app is built by Kotlin, targetting min SDK 24 and following MVVM software architectural pattern. 

Components used in App: 
* FrontEnd: Acitivity, Fragments, Bottom navigation, RecyclerView, CardView, Glibe library and MPAndroidChart library.
* BackEnd:
1. Database: ROOM database with Coroutine and LiveData
1. Network: Retrofit, Moshi to fetch JSON data from network and convert to object.
1. WorkManager: Update data from network periodically once a day in background through PeriodicWorkRequest
1. Unit Testing: JUnit 4 (for app main functions)
1. Implicit intent to send email
1. MPAndroidChart library to draw pie chart

## App Demo: 
<div align="center">

[![Finnish Parliament App Demo](http://img.youtube.com/vi/jhamcarcMUk/0.jpg)](http://www.youtube.com/watch?v=jhamcarcMUk "[Finnish Parliament App Demo")

</div>
 

 

