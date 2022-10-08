# Finnish Parliament App
This personal app project is created as part of OOP course belonging to Metropolia University Of Applied Sciences.
This app is created for user to browse Finnish Parliament parties, member info and view their reviews. There is also possiblity to add members into favorite list

## Tables of contents
* [App Features](#app-features)
* [In-app technology](#in-app-technology)
* [App Demo](#app-demo)

## App Features
1. Browse parties and members information. 
1. View member's reviews and their star rating based on the listed reviews
1. Add, Update and Delete reviews for each members
1. Add members to favorite list and view favorite list

## In-app technology
This Android app is built by Kotlin, targetting min SDK 24 and following MVVM software architectural pattern .

Components used in App: 
* FrontEnd: Acitivity, Fragments, Bottom navigation, RecyclerView, CardView and Glibe library. 
* BackEnd:
1. Database: ROOM database with Coroutine
1. Network: Retrofit, Moshi to fetch JSON data from network and convert to object.
1. WorkManager: Update data from network periodically once a day in background through PeriodicWorkRequest

## App Demo: 
<div align="center">

[![Finnish Parliament App Demo](http://img.youtube.com/vi/giOaz_PWACA/0.jpg)](http://www.youtube.com/watch?v=giOaz_PWACA "[Finnish Parliament App Demo")

</div>
 

 

