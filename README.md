# OnActivityResult

[![Build Status](https://travis-ci.org/vanniktech/OnActivityResult.svg?branch=master)](https://travis-ci.org/vanniktech/OnActivityResult?branch=master)
[![Codecov](https://codecov.io/github/vanniktech/OnActivityResult/coverage.svg?branch=master)](https://codecov.io/github/vanniktech/OnActivityResult?branch=master)
[![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

* Generates boilerplate code for OnActivityResult callbacks and lets you focus on what matters.
* Generated code is fully traceable and debuggable.
* Everything is generated during compile time with appropriate errors / warnings.
* No reflection used!

## Gradle

```groovy
dependencies {
  compile 'com.vanniktech:onactivityresult:0.7.0'
  annotationProcessor 'com.vanniktech:onactivityresult-compiler:0.7.0'
}
```

### Snapshots

```groovy
compile 'com.vanniktech:onactivityresult:0.7.0-SNAPSHOT'
annotationProcessor 'com.vanniktech:onactivityresult-compiler:0.7.0-SNAPSHOT'
```

Modules are located on [Maven Central](https://oss.sonatype.org/#nexus-search;quick~onactivityresult).

## Example

Override `onActivityResult` in your Activity / Fragment and call `ActivityResult.onResult`

```java
@Override
protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
  super.onActivityResult(requestCode, resultCode, data);

  ActivityResult.onResult(requestCode, resultCode, data).into(this);
}
```

Annotate your methods and get the callback

```java
@OnActivityResult(requestCode = 33)
void onActivityResultTestActivity() { /* Do something */ }

@OnActivityResult(requestCode = 1, resultCodes = { Activity.RESULT_OK })
void onActivityResultActivityOk() { /* Only do something when ok */ }

@OnActivityResult(requestCode = 1, resultCodes = { Activity.RESULT_CANCELED })
void onActivityResultActivityCanceled() { /* Only do something when canceled */ }

@OnActivityResult(requestCode = 2)
void onActivityResultPickImage(final int resultCode, final Intent intent) { /* Do something */ }
```

Various parameters are supported:

* `none`
* `int`
* `Intent`
* `int, Intent`
* `Intent, int`

Where `int` parameters will get the resultCode and `Intent` parameters will get the Intent.

**Note: Each annotated method shall only have one int and / or Intent variable.**

In addition to that other parameter annotations are supported like:

* [@IntentData](onactivityresult-annotations/src/main/java/onactivityresult/IntentData.java) `Uri uri`
* [@Extra](onactivityresult-annotations/src/main/java/onactivityresult/Extra.java) `type var`
* [@ExtraBoolean](onactivityresult-annotations/src/main/java/onactivityresult/ExtraBoolean.java) `boolean booleanVar`
* [@ExtraByte](onactivityresult-annotations/src/main/java/onactivityresult/ExtraByte.java) `byte byteVar`
* [@ExtraChar](onactivityresult-annotations/src/main/java/onactivityresult/ExtraChar.java) `char charVar`
* [@ExtraDouble](onactivityresult-annotations/src/main/java/onactivityresult/ExtraDouble.java) `double doubleVar`
* [@ExtraFloat](onactivityresult-annotations/src/main/java/onactivityresult/ExtraFloat.java) `float floatVar`
* [@ExtraInt](onactivityresult-annotations/src/main/java/onactivityresult/ExtraInt.java) `int intVar`
* [@ExtraLong](onactivityresult-annotations/src/main/java/onactivityresult/ExtraLong.java) `long longVar`
* [@ExtraShort](onactivityresult-annotations/src/main/java/onactivityresult/ExtraShort.java) `short shortVar`
* [@ExtraString](onactivityresult-annotations/src/main/java/onactivityresult/ExtraString.java) `String stringVar`

Some examples can be found [here](./onactivityresult-sample/src/main/java/com/vanniktech/onactivityresult/sample/MainActivity.java).

The [@Extra](onactivityresult-annotations/src/main/java/onactivityresult/Extra.java) annotation is generic and works with every type mentioned above. In addition it also supports custom types which are implementing `Parcelable` or `Serializable`.

The disadvantage of [@Extra](onactivityresult-annotations/src/main/java/onactivityresult/Extra.java) is that it won't let you specify a default value therefore the other annotations do exist and should be used when needed.

## Advantages over [AfterMath](https://github.com/MichaelEvans/Aftermath)

* Gives you compile error(s) when using invalid RequestCode
* Annotated method does not require `resultCode` and `Intent` to be present. It'll work with every combination (no params, resultCode, Intent, resultCode & Intent, Intent & resultCode). In addition also all custom parameter annotations can be used.
* The annotations are on mavenCentral and available as a separate artifact
* More detailed error messages
* Inheritance support
* [@IntentData](onactivityresult-annotations/src/main/java/onactivityresult/IntentData.java) annotation with @NonNull & @Nullable support.
* Specify resultCodes with e.g. `resultCodes = { Activity.RESULT_OK }`.
* [@Extra](onactivityresult-annotations/src/main/java/onactivityresult/Extra.java), [@ExtraBoolean](onactivityresult-annotations/src/main/java/onactivityresult/ExtraBoolean.java), [@ExtraByte](onactivityresult-annotations/src/main/java/onactivityresult/ExtraByte.java), [@ExtraChar](onactivityresult-annotations/src/main/java/onactivityresult/ExtraChar.java), [@ExtraDouble](onactivityresult-annotations/src/main/java/onactivityresult/ExtraDouble.java), [@ExtraFloat](onactivityresult-annotations/src/main/java/onactivityresult/ExtraFloat.java), [@ExtraInt](onactivityresult-annotations/src/main/java/onactivityresult/ExtraInt.java), [@ExtraLong](onactivityresult-annotations/src/main/java/onactivityresult/ExtraLong.java), [@ExtraShort](onactivityresult-annotations/src/main/java/onactivityresult/ExtraShort.java), [@ExtraString](onactivityresult-annotations/src/main/java/onactivityresult/ExtraString.java) annotations.

## Thanks

[Thanks to JakeWharton's ButterKnife](https://github.com/JakeWharton/butterknife)

[Thanks to Hannes Dorfmann's Annotation Processing 101](http://hannesdorfmann.com/annotation-processing/annotationprocessing101)

# License

Copyright (C) 2015 Vanniktech - Niklas Baudy

Licensed under the Apache License, Version 2.0