package org.jetbrains.kotlin.public.course.parallel.practice.solution

import kotlin.random.Random

/**
 * Even better solution would be to check for interruptions in the bubble sort
 */
class BigBrainWorker : Thread() {
    private fun bubbleSort(list: MutableList<Int>) {
        for (i in 0..<list.lastIndex) {
            for (j in (i + 1)..<list.lastIndex) {
                if (list[j] > list[j + 1]) {
                    val tmp = list[j]
                    list[j] = list[j + 1]
                    list[j + 1] = tmp
                }
            }
        }
    }

    private fun work() {
        val size = 10000
        val l = List(size) { Random.nextInt(size * 10) }.toMutableList()
        bubbleSort(l)
    }

    override fun run() {
        var counter = 0
        var counterInterrupted = 0
        while (true) {
            println("Working on ${++counter}")
            work()
            if (interrupted()) {
                counterInterrupted++
                if (counterInterrupted > 1) {
                    println("okay :(")
                    return
                } else {
                    println("You're trying o interrupt me?!")
                }
            }
        }
    }
}

fun main() {
    val worker = BigBrainWorker()
    worker.start()
    Thread.sleep(1000)
    worker.interrupt()
    Thread.sleep(1000)
    worker.interrupt()
}