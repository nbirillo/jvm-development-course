package org.jetbrains.kotlin.public.course.parallel.practice.solution

import org.jetbrains.kotlin.public.course.parallel.practice.task.Statement
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.*

class LockedList<T>(private val list: MutableList<T>) : AbstractMutableList<T>() {
    private val lock = ReentrantReadWriteLock()

    override fun add(index: Int, element: T) = lock.write { list.add(index, element) }

    override val size: Int
        get() = lock.read { list.size }

    override fun get(index: Int): T = lock.read { list.get(index) }

    override fun removeAt(index: Int): T = lock.write { list.removeAt(index) }

    override fun set(index: Int, element: T): T = lock.write { list.set(index, element) }

}

fun count(ch: Char, list: Collection<Char>): Int {
    var count = 0
        list.forEach {
            if (it == ch)
                count++
        }
    return count
}

fun clean(ch: Char, list: MutableCollection<Char>) {
        val it = list.iterator()
        while (it.hasNext()) {
            if (it.next() == ch) {
                it.remove()
            }
        }
}

fun sanityCheck() {
    val lettersList = Statement.getList()
    assert(Statement.aCount == count('a', lettersList))
    assert(Statement.bCount == count('b', lettersList))
    assert(Statement.cCount == count('c', lettersList))
    clean('c', lettersList)
    assert(Statement.aCount + Statement.bCount == lettersList.size)
}

/**
 * This does not work in a wierd way, how and why?
 */
fun main() {
    sanityCheck()

    val lettersList = LockedList(Statement.getList())

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