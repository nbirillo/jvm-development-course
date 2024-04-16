package org.jetbrains.kotlin.public.course.parallel.practice.solution

import org.jetbrains.kotlin.public.course.parallel.practice.task.Statement
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.*

class LockedListV2<T>(private val list: MutableList<T>) {
    private val lock = ReentrantReadWriteLock()

    fun read(operation: (List<T>) -> Unit) = lock.read { operation(list) }

    fun write(operation: (MutableList<T>) -> Unit) = lock.write { operation(list) }
}

fun main() {
    val lockedList = LockedListV2(Statement.getList())

    val t1 = thread {
        lockedList.read { lettersList ->
            val count = count('a', lettersList)
            println("a count: $count")
        }

    }
    val t2 = thread {
        lockedList.read { lettersList ->
            val count = count('b', lettersList)
            println("b count: $count")
        }
    }
    val t3 = thread {
        lockedList.write { lettersList ->
            clean('c', lettersList)
        }
    }
    val t4 = thread {
        lockedList.read { lettersList ->
            val count = count('c', lettersList)
            println("c count: $count")
        }
    }

    listOf(t1, t2, t3, t4).forEach { it.join() }

    lockedList.read {
        println(it.size)
    }

}