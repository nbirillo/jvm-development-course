package org.jetbrains.kotlin.public.course.parallel.practice.task

import org.jetbrains.kotlin.public.course.parallel.practice.solution.SolutionStatefulExecutor
import org.jetbrains.kotlin.public.course.parallel.practice.task.StatefulTask.MutableStatefulTask

class StatefulTask private constructor(
    val steps: List<Runnable>,
    var label: Int,
    val onError: (Exception) -> Unit
) {
    class MutableStatefulTask(
        private val steps: MutableList<Runnable> = mutableListOf(),
        private var onError: (Exception) -> Unit = {}
    ) {
        fun step(block: () -> Unit) {
            steps.add(block)
        }

        fun errorHandler(block: (Exception) -> Unit) {
            onError = block
        }

        val task: StatefulTask
            get() = StatefulTask(steps, 0, onError)

    }
}

fun statefulTask(body: MutableStatefulTask.() -> Unit) = MutableStatefulTask().apply(body).task

interface StatefulExecutor {
    fun execute(task: StatefulTask)
    fun shutdown()
}

fun main() {
    val example =
        statefulTask {
            step {
                println("step 1")
            }
            step {
                println("step 2")
            }
            step {
                throw RuntimeException("Something bad happened at step 3")
            }
            step {
                println("Unreachable step")
            }
            errorHandler {
                println("ALARM! ALARM! ${it.message}")
            }
        }

    val executor: StatefulExecutor = SolutionStatefulExecutor(3)

    executor.execute(example)

    Thread.sleep(1000)

    executor.shutdown()
}
