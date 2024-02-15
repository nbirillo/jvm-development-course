package org.jetbrains.kotlin.public.course.oop

sealed class Base {
    open var value: Int = 23

    open fun foo() = value * 2
}

open class Child1 : Base() {
    override fun foo() = value * 3

    final override var value: Int = 10
        set(value) = run { field = super.foo() }
}

class Child2 : Base()
