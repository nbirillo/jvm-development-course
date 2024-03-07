package org.jetbrains.kotlin.public.course.generics.practice.task.projections.task4

/* Projections #4

Implement a simple function copy,
that accepts two arrays of Any and copy elements from the first array to the second one
**/

fun main() {
    val ints = arrayOf(1, 2, 3)
    val any = Array<Any>(3) { "" }
    ints.forEach{ print("$it ") }
    println()
    any.forEach{ print("$it ")}
    println()
    println("_______")
    println("_______")

    // TODO: uncomment this code
   // copy(ints, any)

    ints.forEach{ print("$it ") }
    println()
    any.forEach{ print("$it ")}
}
