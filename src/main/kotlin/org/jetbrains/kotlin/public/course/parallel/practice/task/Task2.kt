package org.jetbrains.kotlin.public.course.parallel.practice.task

class StoopidWorker : Thread() {
    var work = true

    override fun run() {
        var counter = 0
        while(work) {
            println("Working on ${++counter}")
            counter++
            sleep(500)
        }
    }
}

fun main() {
    val worker = StoopidWorker()
    worker.start()
    // how to stop the worker?
    TODO()
}