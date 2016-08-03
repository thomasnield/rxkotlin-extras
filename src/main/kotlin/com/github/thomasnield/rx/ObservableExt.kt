package com.github.thomasnield.rx

import com.github.davidmoten.rx.Bytes
import com.github.davidmoten.rx.Obs
import com.github.davidmoten.rx.Transformers
import rx.Observable
import rx.Scheduler
import rx.schedulers.Schedulers
import java.io.File
import java.io.InputStream
import java.util.concurrent.TimeUnit


fun InputStream.toBytesObservable(chunkSize: Int) = Bytes.from(this,chunkSize)
fun File.toBytesObservable(chunkSize: Int) = Bytes.from(this,chunkSize)
fun InputStream.toBytesUnzipObservable() = Bytes.unzip(this)
fun File.toBytesUnzipObservable() = Bytes.unzip(this)

fun <T> Observable<T>.cache(duration: Long, unit: TimeUnit, worker: Scheduler.Worker) =
        Obs.cache(this,duration,unit,worker)

fun <T> Observable<T>.cache(duration: Long, unit: TimeUnit, scheduler: Scheduler = Schedulers.computation()) =
        Obs.cache(this,duration,unit,scheduler)


fun <T> Observable<T>.doOnEmpty(onEmpty: () -> Unit) = compose(Transformers.doOnEmpty(onEmpty))
fun <T> Observable<T>.doOnFirst(action: (T) -> Unit) = compose(Transformers.doOnFirst(action))

fun <T> Observable<T>.doOnNext(n: Int, action: (T) -> Unit) = compose(Transformers.doOnNext(n,action))

//TODO
//fun <T> Observable<T>.retryWhen() =

fun <T> Observable<T>.toListWhile(condition: (MutableList<T>, T) -> Boolean): Observable<List<T>> =
         compose(Transformers.toListWhile(condition))

fun <T> Observable<T>.toListUntilChanged() = compose(Transformers.toListUntilChanged())

fun <T,R: MutableCollection<T>> Observable<T>.collectWhile(factory: (() -> R), action: (R,T) -> Unit, condition: (R,T) -> Boolean) =
        compose(Transformers.collectWhile(factory,action,condition))

fun <R,T> Observable<T>.ignoreElementsThen(next: Observable<R>) = compose(Transformers.ignoreElementsThen(next))

