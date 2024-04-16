package org.jetbrains.kotlin.public.course.parallel.practice.solution

import java.util.concurrent.Phaser
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
    }.forEach(Thread::join)
}

/**
 * In an honest race, we use Phaser to synchronize the steps that horses have made so far.
 * After readying up, each horse arrives at Phaser and waits for other horses to also arrive.
 */
fun honestRace(horses: List<RaceHorse>) {
    val phaser = Phaser(horses.size)
    horses.map { horse ->
        thread {
            phaser.arriveAndAwaitAdvance()
            horse.getReady()
            phaser.arriveAndAwaitAdvance()
            horse.run()
        }
    }.forEach(Thread::join)
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