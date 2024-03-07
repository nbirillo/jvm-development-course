package org.jetbrains.kotlin.public.course.collectionsAndFp.practice.answer

// Calculate the frequency of each word in the string in percentage and print only
// words with frequency more than average frequency for all words

// The output format:
// Word				Frequency(%)
// kotlin           5.36
// ...
fun task1() {
    val textAboutKotlin = """
        Kotlin is a cross-platform, statically typed, general-purpose high-level programming language 
        with type inference. Kotlin is designed to interoperate fully with Java, and the JVM version of Kotlin 
        standard library depends on the Java Class Library, but type inference allows its syntax to be more concise.
    """.trimIndent()

    val words = textAboutKotlin.split(" ", ".", ",", "-", System.lineSeparator())
    var average = 0.0
    val separator = "\t\t\t\t"

    words
        .filter { it.isNotEmpty() }
        .map { it.lowercase() }
        .groupingBy { it }
        .eachCount()
        .mapValues { (_, freq) ->
            freq * 100.0 / words.size
        }.toList()
        .sortedBy { (_, freq) -> -freq }.also { w -> average = w.map { it.second }.average() }
        // Here we can just write this result into a variable and next print the table
        .takeWhile { (_, freq) -> freq > average }.also { println("Word${separator}Frequency(%)") }
        .forEach { (word, freq) ->
            println("$word$separator${"%.2f".format(freq)}")
        }

    // groupingBy + eachCount can be replaced with: .groupBy { it }
    //        .mapValues { (_, l) ->
    //            l.size * 100.0 / words.size
    //        }
}

fun main() {
    task1()
}
