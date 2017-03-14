package com.github.thomasnield.rx2

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction

/**
 * Joins the emissions of a finite `Flowable` into a `String`.
 *
 * @param separator is the dividing character(s) between each element in the concatenated `String`
 *
 * @param prefix is the preceding `String` before the concatenated elements (optional)
 *
 * @param postfix is the succeeding `String` after the concatenated elements (optional)
 */
fun <T : Any> Flowable<T>.joinToString(separator: String? = null,
                                       prefix: String? = null,
                                       postfix: String? = null
): Single<String> = collect({ StringBuilder(prefix ?: "") }) { builder: StringBuilder, next: T ->
    builder.append(if (builder.length == prefix?.length ?: 0) "" else separator ?: "").append(next)
}.map { it.append(postfix ?: "").toString() }


/**
 * Returns Flowable that wrap all values into [IndexedValue] and populates corresponding index value.
 * Works similar to [kotlin.withIndex]
 */
fun <T : Any> Flowable<T>.withIndex(): Flowable<IndexedValue<T>>
        = zipWith(Flowable.range(0, Int.MAX_VALUE), BiFunction { value, index -> IndexedValue(index, value) })