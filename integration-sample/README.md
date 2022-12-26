[![Download](https://maven-badges.herokuapp.com/maven-central/com.ecommpay/msdk-ui/badge.svg) ](https://maven-badges.herokuapp.com/maven-central/com.ecommpay/msdk-ui/badge.svg)
# Quickstart

**How to use example project**
1. Open your application (`androidApp`) module (`build.gradle.kts`);
2. Set `projectId` and `projectSecretKey` variables
3. Run sync gradle task


**Importing libraries in your project**

The SDK for Android libraries can be imported via MavenCentral. To import the libraries via
MavenCentral, you need to do the following:

1. Open your application (`androidApp`)  module  (`build.gradle.kts`);
2. In the `repositories {}` section, specify the `mavenCentral` repository:
```
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
```
3. In the `dependencies {}` section, add the following dependencies:
```
implementation "com.ecommpay:msdk-ui:LATEST_VERSION"
```
