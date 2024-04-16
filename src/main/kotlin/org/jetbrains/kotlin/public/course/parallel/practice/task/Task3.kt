package org.jetbrains.kotlin.public.course.parallel.practice.task

import kotlin.random.Random

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
        while (true) {
            println("Working on ${++counter}")
            work()
        }
    }
}

fun main() {
    val worker = BigBrainWorker()
    worker.start()
    // how to stop the worker?
    TODO()
}