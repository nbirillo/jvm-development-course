package org.jetbrains.kotlin.public.course.parallel.practice.solution

import org.jetbrains.kotlin.public.course.parallel.practice.task.*
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque

class SolutionContextExecutor(val nThreads: Int) : ContextExecutor {
    private val importantTasks: Deque<ContextTask> = ConcurrentLinkedDeque()
    private val ordinaryTasks: Deque<ContextTask> = ConcurrentLinkedDeque()
    private val backlogTasks: Deque<ContextTask> = ConcurrentLinkedDeque()
    private var shutdown = false
    private val workers = Array(nThreads - 1) { Worker("Pool-worker-#${it}").also { it.start() } }
    private val importantWorker = Worker("Important-worker", important = true).also { it.start() }

    private fun thereAreSomeTasks() =
        importantTasks.isNotEmpty() || ordinaryTasks.isNotEmpty() || backlogTasks.isNotEmpty()

    private fun getQueue(context: Context) =
        when (context) {
            Context.IMPORTANT -> importantTasks
            Context.ORDINARY -> ordinaryTasks
            Context.BACKLOG -> backlogTasks
        }

    override fun execute(task: ContextTask) {
        if (shutdown) throw IllegalStateException("Shutdown was already called!")

        getQueue(task.context).add(task)
    }

    override fun shutdown(grace: Boolean) {
        shutdown = true
        Worker("Main", last = true).run()
        workers.forEach { it.interrupt() }
        importantWorker.interrupt()
        workers.forEach { it.join() }
        importantWorker.join()
    }

    private inner class Worker(
        name: String,
        val last: Boolean = false,
        val important: Boolean = false
    ) : Thread(name) {
        private fun exiting() {
            println("[$name] is exiting")
        }

        fun getTask(): ContextTask? {
            if (important)
                return importantTasks.poll()
            else {
                return ordinaryTasks.poll() ?: backlogTasks.poll()
            }

        }

        override fun run() {
            // while not interrupted, and [we are not the last (main) thread, or we are, and there are more tasks]
            while (!interrupted() && (!last || thereAreSomeTasks())) {
                val task = getTask() ?: continue
                val current = task.steps.getOrNull(task.label) ?: continue
                try {
                    task.label++
                    current.run()
                    getQueue(task.context).add(task)
                } catch (e: InterruptedException) {
                    if (shutdown) {
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
