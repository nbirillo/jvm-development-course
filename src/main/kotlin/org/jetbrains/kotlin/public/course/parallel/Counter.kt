package org.jetbrains.kotlin.public.course.parallel

import kotlinx.atomicfu.*
import kotlin.concurrent.thread

interface Counter {
    fun increment()

    fun decrement()

    fun value(): Int
}

class UnsafeCounter : Counter {
    private var c = 0

    override fun increment() {
        c += 1
    }

    override fun decrement() {
        c -= 1
    }

    override fun value() = c
}

class AtomicFuCounter : Counter {
    private val c = atomic(0)

    override fun increment() {
        c += 1
    }

    override fun decrement() {
        c -= 1
    }

    override fun value(): Int {
        return c.value
    }
}

fun testCounter(counter: Counter, expectedFinalValue: Int = 10000) {
    val t1 = thread(false) {
        repeat(expectedFinalValue) {
            counter.increment()
        }
    }
    val t2 = thread(false) {
        repeat(expectedFinalValue) {
            counter.increment()
        }
    }
    val t3 = thread(false) {
        repeat(expectedFinalValue) {
            counter.decrement()
        }
    }

    listOf(t1, t2, t3).shuffled().forEach { it.start() }
    listOf(t1, t2, t3).forEach { it.join() }

    println("Expected: $expectedFinalValue, Actual: ${counter.value()}")
}

fun main() {
    testCounter(UnsafeCounter())
    testCounter(AtomicFuCounter())
}