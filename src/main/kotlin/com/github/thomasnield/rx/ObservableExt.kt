package com.github.thomasnield.rx

import com.github.davidmoten.rx.Obs
import com.github.davidmoten.rx.Transformers
import rx.Observable
import rx.Scheduler
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit


inline fun <T> Observable<T>.toListWhile(crossinline condition: (MutableList<T>, T) -> Boolean) =
         compose(Transformers.toListWhile { l, t -> condition.invoke(l,t)} )

fun <T> Observable<T>.toListUntileChanged() =  compose(Transformers.toListUntilChanged())


inline fun <T,R: Collection<T>> Observable<T>.collectWhile(crossinline factory: () -> R, crossinline condition: (R, T) -> Boolean) =
        compose(Transformers.collectWhile({factory.invoke()}, { r,t -> condition.invoke(r,t)}))


fun <T,R: Collection<T>> Observable<T>.collectWhile(op: CollectWhileParams<T,R>.() -> Unit): Observable<R> {
    val params = CollectWhileParams<T,R>()
    params.op()
    return this.collectWhile(params.factory!!,params.condition!!)
}

fun <T> Observable<T>.cache(duration: Long, unit: TimeUnit, worker: Scheduler.Worker) =
        Obs.cache(this,duration,unit,worker)

fun <T> Observable<T>.cache(duration: Long, unit: TimeUnit, scheduler: Scheduler = Schedulers.computation()) =
        Obs.cache(this,duration,unit,scheduler)

inline fun <T> Observable<T>.doOnNext(n: Int, crossinline action: (T) -> Unit) = compose(Transformers.doOnNext(n,{action.invoke(it)}))
inline fun <T> Observable<T>.doOnFirst(crossinline action: (T) -> Unit) = compose(Transformers.doOnFirst{ action.invoke(it) })
fun <R,T> Observable<T>.ignoreElementsThen(next: Observable<R>) = compose(Transformers.ignoreElementsThen(next))


class CollectWhileParams<T,R> {
    internal var factory: (() -> R)? = null
    internal var condition: ((R,T) -> Boolean)? = null

    fun factory(factory: () -> R) { this.factory = factory }
    fun condition(condition: (R,T) -> Boolean) { this.condition = condition }
}

