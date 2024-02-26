package org.jetbrains.kotlin.public.course.generics

inline fun fooNonLocalReturn(call1: () -> Unit, call2: () -> Unit) {
    call1()
    call2()
}

fun main() {
    println("Step#1")
    fooNonLocalReturn({
        println("Step#2")
        return
    }, { println("Step#3") })
    println("Step#4")
}
