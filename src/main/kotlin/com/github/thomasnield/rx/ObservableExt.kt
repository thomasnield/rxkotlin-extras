package com.github.thomasnield.rx

import com.github.davidmoten.rx.Obs
import com.github.davidmoten.rx.Transformers
import rx.Observable
import rx.Scheduler
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit


fun <T> Observable<T>.toListWhile(condition: (MutableList<T>, T) -> Boolean): Observable<List<T>> =
         compose(Transformers.toListWhile(condition))

fun <T> Observable<T>.toListUntilChanged() = compose(Transformers.toListUntilChanged())

fun <T,R: MutableCollection<T>> Observable<T>.collectWhile(factory: (() -> R), action: (R,T) -> Unit, condition: (R,T) -> Boolean) =
        compose(Transformers.collectWhile(factory,action,condition))


fun <T> Observable<T>.cache(duration: Long, unit: TimeUnit, worker: Scheduler.Worker) =
        Obs.cache(this,duration,unit,worker)

fun <T> Observable<T>.cache(duration: Long, unit: TimeUnit, scheduler: Scheduler = Schedulers.computation()) =
        Obs.cache(this,duration,unit,scheduler)

fun <T> Observable<T>.doOnNext(n: Int, action: (T) -> Unit) = compose(Transformers.doOnNext(n,action))
fun <T> Observable<T>.doOnFirst(action: (T) -> Unit) = compose(Transformers.doOnFirst(action))
fun <R,T> Observable<T>.ignoreElementsThen(next: Observable<R>) = compose(Transformers.ignoreElementsThen(next))


class CollectWhileParams<T,R> {
    internal var factory: (() -> R)? = null
    internal var condition: ((R,T) -> Boolean)? = null

    fun factory(factory: () -> R) { this.factory = factory }
    fun condition(condition: (R,T) -> Boolean) { this.condition = condition }
}

