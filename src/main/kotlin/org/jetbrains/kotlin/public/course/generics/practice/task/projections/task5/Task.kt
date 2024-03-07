package org.jetbrains.kotlin.public.course.generics.practice.task.projections.task5

/* Projections #5

[1]
Create a new class "Printer", that can accept only T elements and print them
    - add a "print" function that prints item with type T;

[2]
Create a new abstract class "Builder" that generates instances of T based on parameter of P type.
This class cannot produce P and consume T:
    - add an abstract "build" function that accepts P and returns T;
Create a new class "IntegerBuilder", that implements Builder and can convert String to Int
**/

fun main() {
    // [1]
    // TODO: uncomment me
//    val integerPrinter = Printer<Int>()
//    val stringPrinter = Printer<String>()
//
//    integerPrinter.print(2)
//    stringPrinter.print("Bla bla bla")

    println("________")

    // [2]
    // TODO: uncomment me
//    val intBuilder = IntegerBuilder()
//    val x = intBuilder.build("1")
//    println("We build [ $x ]")
}
