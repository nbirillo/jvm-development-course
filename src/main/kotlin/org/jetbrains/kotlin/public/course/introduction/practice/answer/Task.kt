package org.jetbrains.kotlin.public.course.introduction.practice.answer

import kotlin.random.Random

const val MIN_NUMBER = 10
const val MAX_NUMBER = 100
val newLineSymbol: String = System.lineSeparator()

fun readNumber(): Int {
    var number: Int?
    do {
        val numberStr = readlnOrNull().orEmpty()
        number = try { numberStr.toInt() } catch (e: NumberFormatException) { null }
    } while(number == null)
    return number
}

fun main() {
    val sb = StringBuilder()
    val hiddenNumber = Random.nextInt(MIN_NUMBER, MAX_NUMBER)
    sb.append("The number $hiddenNumber was guessed$newLineSymbol")
    println("I guessed a random number in the range $MIN_NUMBER..$MAX_NUMBER. " +
            "Try to guess it in the minimal number of attempts!")
    var guess: Int
    var counter = 0
    do {
        sb.append("User attempt has started...$newLineSymbol")
        guess = readNumber()
        if (guess < hiddenNumber) {
            println("Too low! Try again!")
            sb.append("The user inputted the number $guess, this number is too low$newLineSymbol")
        } else if (guess > hiddenNumber) {
            println("Too high! Try again!")
            sb.append("The user inputted the number $guess, this number is too high$newLineSymbol")
        }
        counter++
    } while(guess != hiddenNumber)
    sb.append("The user guessed for $counter attempts$newLineSymbol")
    println("Congrats! You guessed for $counter attempts! The number is $hiddenNumber")

    println("GAME LOGS:")
    println(sb.toString())
}