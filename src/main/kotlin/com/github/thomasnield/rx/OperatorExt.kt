package com.github.thomasnield.rx

import com.github.davidmoten.rx.Transformers
import rx.Observable


inline fun <T> Observable<T>.toListWhile(crossinline condition: (MutableList<in T>, T) -> Boolean) =
         compose(Transformers.toListWhile { l, t -> condition.invoke(l,t)} )

fun <T> Observable<T>.toListUntileChanged() =  compose(Transformers.toListUntilChanged())


inline fun <T,R: Collection<T>> Observable<T>.collectWhile(crossinline factory: () -> R, crossinline condition: (R, T) -> Boolean) =
        compose(Transformers.collectWhile({factory.invoke()}, { r,t -> condition.invoke(r,t)}))


fun <T,R: Collection<T>> Observable<T>.collectWhile(op: CollectWhileParams<T,R>.() -> Unit): Observable<R> {
    val params = CollectWhileParams<T,R>()
    params.op()
    return this.collectWhile(params.factory!!,params.condition!!)
}
class CollectWhileParams<T,R> {
    internal var factory: (() -> R)? = null
    internal var condition: ((R,T) -> Boolean)? = null

    fun factory(factory: () -> R) { this.factory = factory }
    fun condition(condition: (R,T) -> Boolean) { this.condition = condition }
}
