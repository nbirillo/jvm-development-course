package org.jetbrains.kotlin.public.course.generics

inline fun crossInlineFoo(crossinline call1: () -> Unit, call2: () -> Unit) {
    call1()
    call2()
}

fun main() {
    println("Step#1")
    crossInlineFoo({
        println("Step#2")
        // return
    }, // ERROR: 'return' is not allowed here
        { println("Step#3") })
    println("Step#4")
}
