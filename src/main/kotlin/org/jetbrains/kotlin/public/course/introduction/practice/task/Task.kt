package org.jetbrains.kotlin.public.course.introduction.practice.task

const val TO = 101
const val FROM = 10

// Implement a simple console game where a random number between 10 and 100 is generated,
// and the user has to guess this number.
// The program should inform the user if their guess is low or high.

// Then log all user's answers into a string and print the log og the game.

fun readAndPrepare(): Int?
{
    println("Enter a number:")
    return readlnOrNull()?.toIntOrNull()
}

fun main() {
    val secretNumber = (FROM..TO).random()
    val stringBuilder = StringBuilder()


    do {
        val number : Int? = readAndPrepare()
        when (number) {
            null -> {
                "It's not a number".let {
                    println(it)
                    stringBuilder.append("$it${System.lineSeparator()}")
                }
                continue
            }
            in FROM..<secretNumber -> {
                println("Entered number is too small")
                stringBuilder.append("$number: Entered number is too small${System.lineSeparator()}")
            }
            in secretNumber + FROM..<TO -> {
                println("Entered number is too big")
                stringBuilder.append("$number: Entered number is too big${System.lineSeparator()}")
            }
        }
        if (number !in FROM..TO) println("Number should be between 10 and 100")
    } while (number != secretNumber)
    println("You win!!!")
    println(stringBuilder.toString())
}
