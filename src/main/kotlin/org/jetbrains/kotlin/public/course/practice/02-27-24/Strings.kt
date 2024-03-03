package org.jetbrains.kotlin.public.course.practice.`02-27-24`


fun main() {
    val line1 = "Kotlin is a cross-platform, statically typed, general-purpose high-level programming language with type inference."
    val line2 = "Kotlin is designed to interoperate fully with Java, and the JVM version of Kotlin's standard library depends on the Java Class Library, but type inference allows its syntax to be more concise."

    // way 1
    println(line1)
    println(line2)

    // way 2
    println("$line1${System.lineSeparator()}$line2")

    // way 3
    println("""
        $line1
        $line2
    """.trimIndent())

    // Using string Builder
    val stringBuilder = StringBuilder().apply {
        append(line1)
        append(System.lineSeparator()) // Appending the system's line separator
        append(line2)
    }

    println(stringBuilder.toString())
}
