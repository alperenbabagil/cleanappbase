[![](https://jitpack.io/v/alperenbabagil/cleanappbase.svg)](https://jitpack.io/#alperenbabagil/cleanappbase)
# Clean App Base
![Screen Shot 2020-11-26 at 23 49 22](https://user-images.githubusercontent.com/15035624/100391306-0aadbe00-3044-11eb-887c-cd4c376a2f1f.jpg)
It is a library for implementing Clean Architecture Design to your project. It contains base structures to implement in your project. It has data, domain and presentation layer structures. They can be implemented module by module. In this project a demo app is included to show how these structures are used.

## A detailed usage is explained at [babagilalperen.medium.com](https://babagilalperen.medium.com/android-clean-app-base-library-clean-architecture-mvvm-part-i-e8614978877f)


## Installation
Add it in your root build.gradle at the end of repositories:
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Then add these to desired module:
```gradle
implementation "com.github.alperenbabagil.cleanappbase:cabdata:$cabVersion"
implementation "com.github.alperenbabagil.cleanappbase:cabdomain:$cabVersion"
implementation "com.github.alperenbabagil.cleanappbase:cabpresentation:$cabVersion"
```
There is another module that contains a DataHolder structure for carrying data. It is provided by cabdomain module but you can implement seperately if you want:
```gradle
implementation "com.github.alperenbabagil.cleanappbase:dataholder:$cabVersion"
```
There is the demo app screen record:

![](https://media.giphy.com/media/UffGYSAAK7HACEr7m4/giphy.gif)
