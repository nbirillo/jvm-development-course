package org.jetbrains.kotlin.public.course.generics

import kotlin.concurrent.thread

inline fun drive(crossinline specialCall: (String) -> Unit, call: (String) -> Unit) {
    val nightCall = Runnable { specialCall("There's something inside you") }
    call("I'm giving you a nightcall to tell you how I feel")
    thread { nightCall.run() }
    call("I'm gonna drive you through the night, down the hills")
}

fun main() {
    drive({ System.err.println(it) }) { println(it) }
}