# json-config4j
A simple library for creating JSON configuration files in Java.

## Usage
### Installation
You can use json-config4j via Gradle.

To use json-config4j in your project, modify your `project.gradle`:
```groovy
repositories {
    mavenCentral()
    
    maven {
        url "https://repo.lclpnet.work/repository/internal"
    }
}

dependencies {
    implementation 'work.lclpnet:json-config4j:1.0.0'  // replace with your version
}
```
All available versions can be found [here](https://repo.lclpnet.work/#artifact/work.lclpnet/json-config4j).

### Example Code
For example code, please review the [unit tests](https://github.com/LCLPYT/json-config4j/tree/main/src/test/java/work/lclpnet/config/json) of this project.
