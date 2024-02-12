package org.jetbrains.kotlin.public.course.introduction

fun takeRandomTeaSack() = listOf("cookie", "waffles", "chocolate", "candy", "marmalade", TrialTeaSacks()).random()

val trialTeaSacks = listOf("waffles")
const val teaSackBoughtLastNight = "marmalade"

fun Any.serveTo(customer: Customer) = println("Dr. ${customer.name}! The tea was served with $this!")
