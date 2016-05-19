# rxkotlin-extras
Kotlin Extensions to the [rxjava-extras](https://github.com/davidmoten/rxjava-extras) library.

This is in early development and any help is appreciated! The goal is take David Moten's excellent operators for RxJava and make them extensions onto the `Observable.`


**Quick Example:**

```kotlin
val source = Observable.just("Alpha","Beta","Gamma", "Delta", "Epsilon")

source.toListWhile { list,t -> list.isEmpty() || list[0].length == t.length }
        .subscribe { println(it) }
```

**OUTPUT**
```
[Alpha]
[Beta]
[Gamma, Delta]
[Epsilon]
```
