package org.jetbrains.kotlin.public.course.parallel.practice.task

import org.jetbrains.kotlin.public.course.parallel.practice.solution.SolutionContextExecutor

enum class Context {
    IMPORTANT,
    ORDINARY,
    BACKLOG
}

class ContextTask private constructor(
    val steps: List<Runnable>,
    var label: Int,
    val onError: (Exception) -> Unit,
    val context: Context
) {
    class MutableContextTask(
        private val context: Context,
        private val steps: MutableList<Runnable> = mutableListOf(),
        private var onError: (Exception) -> Unit = {}
    ) {
        fun step(block: () -> Unit) {
            steps.add(block)
        }

        fun errorHandler(block: (Exception) -> Unit) {
            onError = block
        }

        val task: ContextTask
            get() = ContextTask(steps, 0, onError, context)

    }
}

fun contextTask(context: Context, body: ContextTask.MutableContextTask.() -> Unit) =
    ContextTask.MutableContextTask(context).apply(body).task

interface ContextExecutor {
    fun execute(task: ContextTask)
    fun shutdown(grace: Boolean)
}

fun main() {
    val important =
        contextTask(Context.IMPORTANT) {
            sleepStep(.5, "imp_1")
            sleepStep(1.5, "imp_2")
        }

    val backlog1 = contextTask(Context.BACKLOG) {
        sleepStep(1.0, "back_1_1")
        sleepStep(1.0, "back_1_2")
    }

    val backlog2 = contextTask(Context.BACKLOG) {
        sleepStep(1.0, "back_2_1")
        sleepStep(1.0, "back_2_2")
    }

    val ordinary1 = contextTask(Context.ORDINARY) {
        sleepStep(2.0, "ord_1_1")
        sleepStep(1.0, "ord_2_1")
    }

    val ordinary2 = contextTask(Context.ORDINARY) {
        sleepStep(.7, "ord_2_1")
        sleepStep(.6, "ord_1_1")
    }

    val executor: ContextExecutor = SolutionContextExecutor(2) // important + 2 workers

    executor.execute(ordinary1)
    executor.execute(backlog1)
    executor.execute(important)
    executor.execute(backlog2)
    executor.execute(ordinary2)

    Thread.sleep(5000)
    executor.shutdown(true)
}

// Helper function
fun ContextTask.MutableContextTask.sleepStep(
    sleepSeconds: Double,
    message: String
) {
    step {
        Thread.sleep((sleepSeconds * 1000).toLong())
        println("[${Thread.currentThread().name}]: $message")
    }
}