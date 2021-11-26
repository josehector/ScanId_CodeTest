# Mobbeel Code Test
This project is part of a Mobbeel's hiring process which try to show our skills in different areas in an app developing process.

## Getting started
This repository contain a simple verify document id Android app, written in Kotlin. This development must follow a guidelines from Mobbeel's team in order to know our skills.


## Tecnical details
### Architecture
In order to guarantee the correct architecture, we have adopted a clean architecture (https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html), where we have divided this app in different layers. These layers are:
* **data**: this layer contain repositories and services. In this layer we have created a fake login services.
* **di**: this layer contain classes related to Dependency Injection (we will describe it below).
* **domain**: this layer contain, mainly, app's use cases (application specific business rules), but it could contain domain's classes (business entities). 
* **presentation**: this layer contain everything about presentation layer. We have used a MVVM pattern (Model View ViewModel) because it helps our to separate view from business logic, and also improve testing.  
* **util**: this layer contain utility classes needed in the project.

### Code Quality
In order to guarantee the quality of the project, we have adopted a Espresso framework for UI tests.
Also, to improve this quality, we have used a static code analysis tool for Kotlin called Ktlint (https://ktlint.github.io/). To analyze the code we can execute the next gradle command:
```
$ ./gradlew ktlint
``` 
 
 ### Framework
In this project we have used 2 trendy frameworks inside Android community. These frameworks are:
* **Coroutines**: this framework helps us to manage long-running task, for example server requests, BBDD operations, etc... 
* **Hilt**: this trendy framework (in alpha version) is used to incorporate dependency injection into app. This helps us for testing, among other things (https://dagger.dev/hilt/). 
 
