package org.jetbrains.kotlin.public.course.parallel.practice.solution

import org.jetbrains.kotlin.public.course.parallel.practice.task.Statement
import java.util.Collections
import kotlin.concurrent.thread

fun synCount(ch: Char, list: List<Char>): Int {
    var count = 0
    synchronized(list) {
        list.forEach {
            if (it == ch)
                count++
        }
    }
    return count
}

fun synClean(ch: Char, list: MutableList<Char>) {
    synchronized(list) {
        val it = list.listIterator()
        while (it.hasNext()) {
            if (it.next() == ch) {
                it.remove()
            }
        }
    }
}

fun synSanityCheck() {
    val lettersList = Statement.getList()
    assert(Statement.aCount == synCount('a', lettersList))
    assert(Statement.bCount == synCount('b', lettersList))
    assert(Statement.cCount == synCount('c', lettersList))
    synClean('c', lettersList)
    assert(Statement.aCount + Statement.bCount == lettersList.size)
}

/**
 * Synchronized block is the most straightforward solution.
 */
fun main() {
    synSanityCheck()

    val original = Statement.getList()
    /**
     * This looks cool, BUT
     * "It is imperative that the user manually synchronize on the returned list when iterating over it"
     * https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#synchronizedList-java.util.List-
     * So it does little in our case.
     */
    val lettersList = Collections.synchronizedList(original)

    val t1 = thread {
        val count = synCount('a', lettersList)
        println("a count: $count")
    }
    val t2 = thread {
        val count = synCount('b', lettersList)
        println("b count: $count")
    }
    val t3 = thread {
        synClean('c', lettersList)
    }
    val t4 = thread {
        val count = synCount('c', lettersList)
        println("c count: $count")
    }

    listOf(t1, t2, t3, t4).forEach { it.join() }

    println(lettersList.size)
}