package org.jetbrains.kotlin.public.course.parallel.practice.solution

class StoopidWorker : Thread() {
    @Volatile var work = true

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
    val worker1 = StoopidWorker()
    worker1.start()
    Thread.sleep(1500)
    worker1.interrupt()

    val worker2 = StoopidWorker()
    worker2.start()
    Thread.sleep(1500)
    worker2.work = false
}