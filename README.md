# RickAndMorty
Android app showing a Clean Architecture implementation using Rick and Morty API

This is a fork of [the original repo from @juan1393][1], adding some refactors and improvements

Huge thanks to [Juan][2] for the original codebase to start

 - Added Instrumentation Tests with espresso
 - Configured koin to replace all dependencies with Test doubles of our preference
 - Replaced custom `Either` class with `Either` from [arrow-kt][3]
 - Added Continous Integration with [travis-ci][4]
 - Renamed Views

![Screenshot](https://github.com/voghDev/RickAndMorty/screenshots/rickandmorty.gif)

[1]: https://github.com/juan1393/RickAndMorty
[2]: https://github.com/juan1393
[3]: https://arrow-kt.io/
[4]: https://travis-ci.com