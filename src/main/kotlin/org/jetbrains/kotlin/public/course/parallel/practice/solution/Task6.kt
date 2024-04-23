package org.jetbrains.kotlin.public.course.parallel.practice.solution

import org.jetbrains.kotlin.public.course.parallel.practice.task.ChainExecutor
import org.jetbrains.kotlin.public.course.parallel.practice.task.ChainTask
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque

class SolutionChainExecutor(val nThreads: Int) : ChainExecutor {
    private val tasks: Deque<Runnable> = ConcurrentLinkedDeque()
    private var done = false
    private val workers = Array(nThreads) { Worker("Pool-worker-#${it}").also { it.start() } }

    override fun execute(task: ChainTask) {
        if (done) throw IllegalStateException("Shutdown was already called!")
        tasks.add { step(0, task) }
    }

    private fun step(i: Int, task: ChainTask) {
        val current = task.steps.getOrNull(i) ?: return
        current.run()
        tasks.add { step(i + 1, task) }
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
                try {
                    tasks.poll()?.run()
                } catch (e: InterruptedException) {
                    if (done) {
                        exiting()
                        return
                    } else {
                        println("[$name] encountered interruption while running a task!")
                    }
                }
            }
            exiting()
        }

    }
}
