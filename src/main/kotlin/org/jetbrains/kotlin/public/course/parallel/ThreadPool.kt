package org.jetbrains.kotlin.public.course.parallel

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

fun residentSleeper(secs: Int) {
    println("${Thread.currentThread().name} is going to sleep for $secs seconds.")
    Thread.sleep(secs * 1000L)
    println("${Thread.currentThread().name} is awake!")
}

fun main() {
    val nThreads = 4
    val threadPoolExecutor = Executors.newFixedThreadPool(nThreads)
    repeat(10) {
        threadPoolExecutor.submit {
            residentSleeper(Random.nextInt(1, 5))
        }
    }
    threadPoolExecutor.shutdown()
    threadPoolExecutor.awaitTermination(1, TimeUnit.MINUTES)
    println("done")
}