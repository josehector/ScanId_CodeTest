# Mobbeel Code Test
This project is part of a Mobbeel's hiring process which try to show our skills in different areas in an app developing process.

## Getting started
This repository contain a simple verify document id Android app, written in Kotlin. This development must follow a guidelines from Mobbeel's team in order to know our skills.

<img src="https://github.com/josehector/ScanId_CodeTest/blob/22403a637927d507dd21187af36d90e0fcc8519e/screenshots/screenshot.jpg" width="250">


## Tecnical details
### Architecture
In order to guarantee the correct architecture, we have adopted a clean architecture (https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html), where we have divided this app in different layers. These layers are:
* **data**: this layer contain repositories and services. In this layer we have created a fake login services.
* **di**: this layer contain classes related to Dependency Injection (we will describe it below).
* **domain**: this layer contain, mainly, app's use cases (application specific business rules), but it could contain domain's classes (business entities). 
* **presentation**: this layer contain everything about presentation layer. We have used a MVVM pattern (Model View ViewModel) because it helps our to separate view from business logic, and also improve testing.  

### Code Quality
In order to guarantee the quality of the project, we have adopted a Espresso framework for UI tests.
 
 ### Framework
In this project we have used 2 trendy frameworks inside Android community. These frameworks are:
* **Coroutines**: this libraries helps us to manage long-running task, for example server requests, BBDD operations, etc... 
* **Hilt**: this trendy libraries (in alpha version) is used to incorporate dependency injection into app. This helps us for testing, among other things (https://dagger.dev/hilt/).
* **Rocket-Beer**: set of libraries that provide us boilerplate code we usually need to code in our projects. For example, rocket-core-data-network, to manage network access (https://github.com/Rocket-Beer).
* **MockWebServer** this library let us to mock web services responses, in order to test our app when we make HTTP and HTTPS call (https://github.com/square/okhttp/tree/master/mockwebserver).
 
