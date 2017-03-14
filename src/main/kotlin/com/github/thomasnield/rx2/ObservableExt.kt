package com.github.thomasnield.rx2

import com.github.davidmoten.rx2.Bytes
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

