package org.jetbrains.kotlin.public.course.oop

import kotlin.math.absoluteValue

fun Int.toDigits(base: Int = 10): List<Int> = sequence {
    var n = this@toDigits
    require(n >= 0) { "The number should be positive!" }
    while (n != 0) {
        yield(n % base)
        n /= base
    }
}.toList().reversed()

fun Int.getFromPosition(position: Int): Int {
    val digits = this.toDigits()
    require(digits.size > position) { "Incorrect position! The number has less digits!" }
    return digits[position]
}
