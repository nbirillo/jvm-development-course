package org.jetbrains.kotlin.public.course.parallel.practice.solution

import kotlinx.atomicfu.locks.ReentrantLock
import java.util.*
import java.util.concurrent.Executor
import kotlin.concurrent.withLock
import kotlin.random.Random

class SimpleThreadPool(val nThreads: Int) : Executor {
    private val tasks: Deque<Runnable> = LinkedList()
    private val tasksLock = ReentrantLock()
    private val tasksAvailable = tasksLock.newCondition()
    private var done = false
    private val workers = Array(nThreads) { Worker("Pool-worker-#${it}").also { it.start() } }

    override fun execute(command: Runnable) {
        if (done) throw IllegalStateException("Shutdown was already called!")
        tasksLock.withLock {
            tasks.add(command)
            tasksAvailable.signal()
        }
    }

    fun shutdown() {
        done = true
        Worker("Main", true).run()
        workers.forEach { it.interrupt() }
        workers.forEach { it.join() }
    }

    private inner class Worker(name: String, val shutDownOnEmpty: Boolean = false) : Thread(name) {
        private fun exiting() {
            println("[$name] is exiting")
        }


        override fun run() {
            while (true) {
                val currentTask: Runnable
                tasksLock.withLock {
                    while (tasks.isEmpty()) {
                        if (shutDownOnEmpty || interrupted()) {
                            exiting()
                            return
                        }
                        try {
                            tasksAvailable.await()
                        } catch (e: InterruptedException) {
                            exiting()
                            return
                        }
                    }
                    currentTask = tasks.pop()
                }
                try {
                    currentTask.run()
                } catch (e: InterruptedException) {
                    if (done) {
                        exiting()
                        return
                    } else {
                        println("[$name] encountered interruption while running a task!")
                    }
                }
            }
        }

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