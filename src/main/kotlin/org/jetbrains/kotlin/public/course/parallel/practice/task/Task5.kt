package org.jetbrains.kotlin.public.course.parallel.practice.task

import java.util.concurrent.Executor
import kotlin.random.Random

/**
 * A harder task would be to implement ExecutorService, though AbstractExecutorService is of help.
 */
class SimpleThreadPool(val nThreads: Int) : Executor {
    override fun execute(command: Runnable) {
        TODO("Not yet implemented")
    }

    fun shutdown() {
        TODO()
    }
}

fun residentSleeper(secs: Int) {
    println("${Thread.currentThread().name} is going to sleep for $secs seconds.")
    Thread.sleep(secs * 1000L)
    println("${Thread.currentThread().name} is awake!")
}

fun main() {
    val nThreads = 4
    val simpleThreadPool = SimpleThreadPool(nThreads)
    repeat(10) {
        simpleThreadPool.execute {
            residentSleeper(Random.nextInt(1, 5))
        }
    }
    simpleThreadPool.shutdown()
    println("done")
}