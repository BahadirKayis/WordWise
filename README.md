<h1 align="center">Word Wise</h1></br>
<p align="center">  
Showing synonyms and vocabulary information of the entered word. An application I wrote to learn the unit test.
</p>
</br>

<p align="center">
 <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat"/></a>
 <a href="https://github.com/BahadirKayis"><img alt="Profile" src="https://img.shields.io/badge/GitHub-BahadirKayis-1D267D"/></a> 
 <a href="https://www.linkedin.com/in/bahad%C4%B1r-kay%C4%B1%C5%9F-b27573228/"><img alt="Profile" src="https://img.shields.io/badge/LinkedIn-BahadirKayis-3795BD"/></a> 
</p>

## Tech stack & Open-source libraries
- Minimum SDK level 24
- [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) and [Flow](https://developer.android.com/kotlin/flow)
- Android Effect-Event-State(EES) -Android EES is an architectural approach to UI management that organizes the relationship of effects, events, and states.
   - Event: An event that occurs in an application, such as a button click or the start of a process.
   - Effect: An action that occurs after an event. For example, it could be to fetch data when a button is clicked, or to show a user a notification.
   - State: A set of data that represents the current state of an application. For example, it could be the data loading state, or the data itself.
- [Jetpack](https://developer.android.com/jetpack/getting-started)- It is a collection of libraries, tools and guidelines that simplify and empower Android app development.
  - [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
    - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform an action when lifecycle state changes
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
    - [UseCases](https://developer.android.com/topic/architecture/domain-layer) - Located domain layer that sits between the UI layer and the data layer. 
    - [Repository](https://developer.android.com/topic/architecture/data-layer) - Located in data layer that contains application data and business logic.
  - [DataStore](https://developer.android.com/jetpack/androidx/releases/datastore) - It is an important component used to manage data and facilitate data storage operations.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) - Is a design pattern that facilitates the management of dependencies and the integration of components in a flexible, testable, and maintainable manner
- [Android Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Dependency Injection Library
- Testing
  - [Mockito](https://site.mockito.org/) - A mocking framework that tastes really good. It lets you write beautiful tests with a clean & simple API
  - [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) - A scriptable web server for testing HTTP clients
  - [Truth](https://truth.dev/) - A library for performing assertions in tests
  - [Turbine](https://github.com/cashapp/turbine) - A small testing library for kotlinx.coroutines Flow

## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture

![](https://user-images.githubusercontent.com/21035435/69536839-9f4c8e80-0fa0-11ea-85ee-d7823e5a46b0.png)

## Find this repository useful? :heart:
__[follow](https://github.com/BahadirKayis)__ me for my next creations! 🤩
