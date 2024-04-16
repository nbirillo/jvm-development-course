package org.jetbrains.kotlin.public.course.parallel.practice.task

import org.jetbrains.kotlin.public.course.parallel.practice.task.Statement.aCount
import org.jetbrains.kotlin.public.course.parallel.practice.task.Statement.bCount
import org.jetbrains.kotlin.public.course.parallel.practice.task.Statement.cCount
import kotlin.concurrent.thread

object Statement {
    const val aCount = 33333
    const val bCount = 66666
    const val cCount = 23000

    fun getList() = (List(aCount) { 'a' } + List(bCount) { 'b' } + List(cCount) { 'c' }).shuffled().toMutableList()
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
    val lettersList = (List(aCount) { 'a' } + List(bCount) { 'b' } + List(cCount) { 'c' }).shuffled().toMutableList()
    assert(aCount == count('a', lettersList))
    assert(bCount == count('b', lettersList))
    assert(cCount == count('c', lettersList))
    clean('c', lettersList)
    assert(aCount + bCount == lettersList.size)
}

/**
 * We want to count a, count b, and remove c in parallel, and then get the final size of the list.
 */
fun main() {
    sanityCheck()

    val lettersList = Statement.getList()

    thread {
        val count = count('a', lettersList)
        println("a count: $count")
    }
    thread {
        val count = count('b', lettersList)
        println("b count: $count")
    }
    thread {
        clean('c', lettersList)
    }
    thread {
        val count = count('c', lettersList)
        println("c count: $count")
    }

    println(lettersList.size)
}