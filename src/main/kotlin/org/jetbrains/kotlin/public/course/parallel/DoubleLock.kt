package org.jetbrains.kotlin.public.course.parallel

import kotlin.concurrent.thread

class ComplicatedThing {
    var value = 3

    init {
        Thread.sleep(value * 1000L)
        value = 23
    }
}

object DoubleCheckLockAntiPattern {
    private var thingy: ComplicatedThing? = null

    fun getThingy(): ComplicatedThing {
        if (thingy == null) {
            synchronized(this) {
                if (thingy == null) {
                    thingy = ComplicatedThing()
                }
            }
        }
        return checkNotNull(thingy)
    }
}

/**
 * In theory the first thread can start creating the thingy, while the second might see intermediary state: value of 3.
 * @Volatile fixes that
 * by lazy {} make it even better
 */
fun main() {
    val t1 = thread {
        val v = DoubleCheckLockAntiPattern.getThingy().value
        println(v)
    }
    val t2 = thread {
        val v = DoubleCheckLockAntiPattern.getThingy().value
        println(v)
    }
    t1.join()
    t2.join()
}