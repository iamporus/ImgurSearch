# ImgurSearch

A simple app to search images on Imgur portal and post comments on to them.

### Code Base

- 80% Kotlin and 20% Java
- Follows MVVM
- Retrofit + Gson for Networking
- Coroutines(Kotlin) and ExecutorService(Java) for concurrency
- Room Database with LiveModel integration for persistence
- Service Locator pattern ([main branch](https://github.com/iamporus/ImgurSearch)) and Dagger 2 ([dagger branch](https://github.com/iamporus/ImgurSearch/tree/dagger)) for dependency injection

### TBD
- [ ] writing tests for networking APIs and room database queries
- [ ] infinite scroll
- [ ] dagger subcomponents
- [ ] ui enhancements (styles, dip values for components as per device buckets)
