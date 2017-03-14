package com.github.thomasnield.rx2

import com.github.davidmoten.rx2.Bytes
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.io.File
import java.io.InputStream


fun InputStream.toBytesObservable(chunkSize: Int) = Bytes.from(this,chunkSize)
fun File.toBytesObservable(chunkSize: Int) = Bytes.from(this,chunkSize)
fun InputStream.toBytesUnzipObservable() = Bytes.unzip(this)
fun File.toBytesUnzipObservable() = Bytes.unzip(this)

/*
fun <T> Observable<T>.cache(duration: Long, unit: TimeUnit, worker: Scheduler.Worker) =
        Obs.cache(this,duration,unit,worker)

fun <T> Observable<T>.cache(duration: Long, unit: TimeUnit, scheduler: Scheduler = Schedulers.computation()) =
        Obs.cache(this,duration,unit,scheduler)
*/

/*

fun <T> Observable<T>.doOnEmpty(onEmpty: () -> Unit) = compose(Transformers.doOnEmpty(onEmpty))
fun <T> Observable<T>.doOnFirst(action: (T) -> Unit) = compose(Transformers.doOnFirst(action))

fun <T> Observable<T>.doOnNext(n: Int, action: (T) -> Unit) = compose(Transformers.doOnNext(n,action))
*/


/**
 * Joins the emissions of a finite `Observable` into a `String`.
 *
 * @param separator is the dividing character(s) between each element in the concatenated `String`
 *
 * @param prefix is the preceding `String` before the concatenated elements (optional)
 *
 * @param postfix is the succeeding `String` after the concatenated elements (optional)
 */
fun <T : Any> Observable<T>.joinToString(separator: String? = null,
                                         prefix: String? = null,
                                         postfix: String? = null
): Single<String> = collect({ StringBuilder(prefix ?: "") }) { builder: StringBuilder, next: T ->
    builder.append(if (builder.length == prefix?.length ?: 0) "" else separator ?: "").append(next)
}.map { it.append(postfix ?: "").toString() }


/**
 * Returns Observable that wrap all values into [IndexedValue] and populates corresponding index value.
 * Works similar to [kotlin.withIndex]
 */
fun <T : Any> Observable<T>.withIndex(): Observable<IndexedValue<T>>
        = zipWith(Observable.range(0, Int.MAX_VALUE), BiFunction { value, index -> IndexedValue(index, value) })