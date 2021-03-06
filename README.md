# fyber-app
Sample application that is using the [Fyber Mobile Offer API] (http://developer.fyber.com/content/current/android/offer-wall/offer-api/index.html).

![Screenshot](https://raw.githubusercontent.com/Zlate87/fyber-app/master/Screenshot_20151207-175517.png)
![Screenshot](https://raw.githubusercontent.com/Zlate87/fyber-app/master/Screenshot_20151207-175528.png)

### Running JUnit tests
The application comes with robolectric tests (more information about robolectric [here](http://robolectric.org/)). To run the robolectric tests, on the command line run:
```
gradlew test
```

### Running functional tests
The application comes with espresso tests (more information about espresso [here](https://google.github.io/android-testing-support-library/docs/espresso/index.html)). To run the tests from gradle, on the command line run:
```
gradlew connectedAndroidTest
```
NOTE: when running the espresso test, make sure that the animations are disabled on the device where the tests are running. More info [here](https://google.github.io/android-testing-support-library/docs/espresso/setup/index.html#running-tests).
