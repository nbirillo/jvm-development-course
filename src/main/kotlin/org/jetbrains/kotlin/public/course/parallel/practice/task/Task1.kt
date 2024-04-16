package org.jetbrains.kotlin.public.course.parallel.practice.task

import kotlin.concurrent.thread
import kotlin.random.Random

/**
 * Each RaceHorse has to first get ready for the race, and then run the race.
 */
class RaceHorse(val name: String, private val readyTimeS: Int) : Runnable {
    private var ready = false

    /**
     * The Problem is that different horses take different time to get ready. Dishonest horse will not wait for its
     * opponents and run the race when they are not even ready.
     */
    fun getReady() {
        Thread.sleep(readyTimeS * 1000L)
        ready = true
        println("$name is ready to run")
    }

    override fun run() {
        if (!ready) {
            getReady()
        }
        println("$name starts running")
        val runTimeMs = Random.nextInt(5)
        Thread.sleep(runTimeMs * 1000L)
        println("$name finished")
    }

    fun reset() {
        ready = false
    }
}

fun dishonestRace(horses: List<RaceHorse>) {
    horses.map { horse ->
        thread {
            horse.run()
        }
    }.forEach { it.join() }
}

/**
 * How can we have an honest race?
 */
fun honestRace(horses: List<RaceHorse>) {
    TODO()
}

fun main() {
    val horses = listOf(
        RaceHorse("Snail", 6),
        RaceHorse("Bolt", 1),
        RaceHorse("Alfredo", 1),
        RaceHorse("Rose", 2)
    )

    println("Dishonest race:")
    dishonestRace(horses)

    horses.forEach(RaceHorse::reset)

    println("Honest race:")
    honestRace(horses)
}