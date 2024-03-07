package org.jetbrains.kotlin.public.course.collectionsAndFp.practice.task

// Print the elements in reverse order in N steps,
// where N is the number of arguments passed.
// At the end return the last printed string.
// Use any character as a separator, for example '-'.
//
//For example, calling task2("one", "two", "three") with the delimiter '-'
// should print the following:
//-three
//-three-two
//-three-two-one
// And returns as a result: -three-two-one
fun task2(vararg strings: String): String = TODO("Implement me!")

fun main() {
    println(task2("one", "two", "three"))
}
