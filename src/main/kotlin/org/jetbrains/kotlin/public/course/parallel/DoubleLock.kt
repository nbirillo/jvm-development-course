package org.jetbrains.kotlin.public.course.parallel

class ComplicatedThing {
    var value = 3

    init {
        /**
         * Imagine that this is actually a very complicated constructor.
         */
        Thread.sleep(value * 1000L)
        value = 23
    }
}

/**
 * We want a thread-safe singleton, which is instantiated only when needed (might not happen at all).
 * There are obvious problems with this solution in a multithreaded application.
 */
object SingleThingyBroken {
    private var thingy: ComplicatedThing? = null

    fun getThingy(): ComplicatedThing {
        if (thingy == null) {
            thingy = ComplicatedThing()
        }
        return checkNotNull(thingy)
    }
}

/**
 * We may try to fix the problem by moving the Thingy construction into a synchronized section.
 * In theory, one thread can start creating the Thingy, while another might see intermediary state: value of 3.
 */
object DoubleCheckLockAntiPattern {
    private var thingy: ComplicatedThing? = null

    fun getThingy(): ComplicatedThing {
        if (thingy == null) {
            synchronized(this) {
                if (thingy == null) {
                    thingy = ComplicatedThing()
                }
            }
        }
        return checkNotNull(thingy)
    }
}

/**
 * `@Volatile` fixes that by making read happen only after constructor finished.
 * `by lazy {}` makes it even better, thanks Kotlin!
 */
object Better {
    @Volatile
    private var thingy1: ComplicatedThing? = null
    private val thingy2: ComplicatedThing? by lazy {
        ComplicatedThing()
    }

    fun getThingy1(): ComplicatedThing {
        if (thingy1 == null) {
            synchronized(this) {
                if (thingy1 == null) {
                    thingy1 = ComplicatedThing()
                }
            }
        }
        return checkNotNull(thingy1)
    }
}
