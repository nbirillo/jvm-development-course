package org.jetbrains.kotlin.public.course.parallel.practice.solution

import org.jetbrains.kotlin.public.course.parallel.practice.task.*
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque

class SolutionStatefulExecutor(val nThreads: Int) : StatefulExecutor {
    private val tasks: Deque<StatefulTask> = ConcurrentLinkedDeque()
    private var done = false
    private val workers = Array(nThreads) { Worker("Pool-worker-#${it}").also { it.start() } }

    override fun execute(task: StatefulTask) {
        if (done) throw IllegalStateException("Shutdown was already called!")
        tasks.add(task)
    }

    override fun shutdown() {
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
            while (!done && !interrupted()) {
                val task = tasks.poll() ?: continue
                val current = task.steps.getOrNull(task.label) ?: continue
                try {
                    task.label++
                    current.run()
                    tasks.add(task)
                } catch (e: InterruptedException) {
                    if (done) {
                        exiting()
                        return
                    } else {
                        println("[$name] encountered interruption while running a task!")
                    }
                } catch (e: Exception) {
                    task.onError(e)
                }
            }
            exiting()
        }

    }
}
