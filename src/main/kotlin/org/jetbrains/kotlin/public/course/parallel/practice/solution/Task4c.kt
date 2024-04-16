package org.jetbrains.kotlin.public.course.parallel.practice.solution

import org.jetbrains.kotlin.public.course.parallel.practice.task.Statement
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.concurrent.*

/**
 * Use the standard library!
 */
fun main() {
    sanityCheck()

    val lettersList = ConcurrentLinkedQueue(Statement.getList())

    val t1 = thread {
        val count = count('a', lettersList)
        println("a count: $count")
    }
    val t2 = thread {
        val count = count('b', lettersList)
        println("b count: $count")
    }
    val t3 = thread {
        clean('c', lettersList)
    }
    val t4 = thread {
        val count = count('c', lettersList)
        println("c count: $count")
    }

    listOf(t1, t2, t3, t4).forEach { it.join() }

    println(lettersList.size)
}