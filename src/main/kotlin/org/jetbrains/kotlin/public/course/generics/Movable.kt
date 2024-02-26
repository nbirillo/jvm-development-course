package org.jetbrains.kotlin.public.course.generics

interface Movable {
    fun move()
}

data class Car(val label: String, val model: String) : Movable {
    override fun move() {
        println("Car is moving")
    }
}

class Plane(val type: String, val model: String) : Movable {
    override fun move() {
        println("Plane is moving")
    }
}
