package org.jetbrains.kotlin.public.course.collectionsAndFp.practice.answer

// Create a class ArithmeticProgression with the following fields:
//  - startValue, the default value is 0
//  - d, the progression difference, the default value is 1
//  - maxN, the maximum number of values, that can be generated, the default value is 100
// This class should be able to generate an arithmetic progression in a loop, e.g. this code
//     for (item in a) {
//        println(item)
//    }
// will print numbers from 0 until 99
class ArithmeticProgression(val startValue: Int = 0, val d: Int = 1, val maxN: Int = 100)

// Note: we don't throw any errors etc inside the iterator,
// since in this task the goal is to show the difference between
// the classic OOP approach with inheritance and more functional one with extensions functions.
// Also, to show the difference between operator fun iterator() and fun iterator().
operator fun ArithmeticProgression.iterator(): Iterator<Int> = object : Iterator<Int> {
    private var currentIndex: Int = 0
    private var currentValue: Int = startValue

    override fun hasNext() = currentIndex < maxN

    override fun next(): Int = currentValue.also {
        currentValue += d
        currentIndex++
    }
}

// But in real life (when you don't need to see the difference between extensions functions, operator, etc)
// for this task in real life is better to use sequence
fun task3(startValue: Int = 0, d: Int = 1, maxN: Int = 100) = sequence {
    var currentValue = startValue
    repeat(maxN) {
        yield(currentValue)
        currentValue += d
    }
}

fun main() {
    val a = ArithmeticProgression()
    for (item in a) {
        println(item)
    }

    println(task3().take(100).toList())
    // We can omit take since we have a limited sequence
    println(task3().toList())
}
