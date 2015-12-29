# OnActivityResult

[![Build Status](https://travis-ci.org/vanniktech/OnActivityResult.svg)](https://travis-ci.org/vanniktech/OnActivityResult)
[![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

* Generates boilerplate code for OnActivityResult callbacks and lets you focus on what matters.
* Generated code is fully traceable.
* Everything is generated during compile time.
* No reflection used!

## Gradle

```groovy
buildscript {
    dependencies {
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
    }
}

apply plugin: 'com.neenbedankt.android-apt'

dependencies {
    compile 'com.vanniktech:onactivityresult:0.1.0'
    apt 'com.vanniktech:onactivityresult-compiler:0.1.0'
}
```

### Snapshots

```groovy
compile 'com.vanniktech:onactivityresult:0.1.1-SNAPSHOT'
apt 'com.vanniktech:onactivityresult-compiler:0.1.1-SNAPSHOT'
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
void onActivityResultTestActivity() {
    Toast.makeText(this, "Got activity for result", Toast.LENGTH_SHORT).show();
}

@OnActivityResult(requestCode = 1)
void onActivityResultTestActivity(final int resultCode) {
    Toast.makeText(this, "Got activity for result " + resultCode, Toast.LENGTH_SHORT).show();
}

@OnActivityResult(requestCode = 2)
void onActivityResultPickImage(final int resultCode, final Intent intent) {
    Toast.makeText(this, "Got image for result " + resultCode + " with intent " + intent, Toast.LENGTH_SHORT).show();
}
```

## Thanks

[Thanks to JakeWharton's ButterKnife](https://github.com/JakeWharton/butterknife)

[Thanks to Hannes Dorfmann's Annotation Processing 101](hannesdorfmann.com/annotation-processing/annotationprocessing101/)

# License

Copyright (C) 2015 Vanniktech - Niklas Baudy

Licensed under the Apache License, Version 2.0