package org.jetbrains.kotlin.public.course.parallel.practice.solution

import kotlin.random.Random

class BigBrainWorker : Thread() {
    /**
     * When a worker does not have any methods that check for interruptions, we can check for them manually.
     * More than that, we can ignore those interruptions.
     * Even better solution would be to check for interruptions in the bubble sort, to react asap.
     */
    private var counterInterrupted = 0
    private fun weShouldStop(): Boolean {
        if (interrupted()) {
            println("You're trying to interrupt me?!")
            counterInterrupted++
            if (counterInterrupted > 1) {
                println("okay :(")
            }
        }
        return counterInterrupted > 1
    }

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
        while (true) {
            println("Working on ${++counter}")
            work()
            if (weShouldStop()) break
        }
        // some cleanup might be here
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