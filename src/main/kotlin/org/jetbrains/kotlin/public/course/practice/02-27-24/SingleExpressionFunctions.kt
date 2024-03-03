package org.jetbrains.kotlin.public.course.practice.`02-27-24`

fun double(x: Int, y: Int): Int {
    return x + y
}

fun safeReadLine(): String {
    return readlnOrNull() ?: throw IllegalStateException("bla bla bla")
}

fun isEven(number: Int): Boolean {
    if (number % 2 == 0) {
        return true
    } else {
        return false
    }
}

// real-example from Hangman project
fun isLost(complete: Boolean, attempts: Int, maxAttemptsCount: Int): Boolean{
    return if (!complete && (attempts>maxAttemptsCount)){
        true
    } else {
        false
    }
}

fun main() {
    print(double(5, 10))
}
