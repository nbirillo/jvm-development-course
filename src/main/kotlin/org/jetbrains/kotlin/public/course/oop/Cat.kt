package org.jetbrains.kotlin.public.course.oop

class Poop

class Food {
    fun eat() = println("Ow! Fish, it was delicious!")
}

class Bowl {
    fun getFood() = Food()
}

open class Place

class Tray : Place()

class Pill

interface CatAtHospital {
    fun checkStomach()

    fun giveMedicine(pill: Pill)
}
