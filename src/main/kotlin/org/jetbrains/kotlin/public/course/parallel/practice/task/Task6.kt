package org.jetbrains.kotlin.public.course.parallel.practice.task

import org.jetbrains.kotlin.public.course.parallel.practice.solution.SolutionChainExecutor

class ChainTask(val steps: List<Runnable>)

class MutableChainTask(val steps: MutableList<Runnable> = mutableListOf()) {
    fun step(body: () -> Unit) = steps.add(body)

    fun get() = ChainTask(steps)
}

fun chainTask(f: MutableChainTask.() -> Unit) = MutableChainTask().apply(f).get()

interface ChainExecutor {
    fun execute(task: ChainTask)
    fun shutdown()
}

fun main() {
    val example =
        chainTask {
            step {
                println("${Thread.currentThread().name} step 1")
            }
            step {
                println("${Thread.currentThread().name} step 2")
            }
            step {
                println("${Thread.currentThread().name} step 3")
            }
        }

    val executor: ChainExecutor = SolutionChainExecutor(3)

    executor.execute(example)

    Thread.sleep(1000)

    executor.shutdown()
}