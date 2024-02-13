package org.jetbrains.kotlin.public.course.introduction

fun takeRandomTeaSnack() = listOf("cookie", "waffles", "chocolate", "candy", "marmalade", TrialTeaSacks()).random()

val trialTeaSnacks = listOf("waffles")
const val teaSnackBoughtLastNight = "marmalade"

fun Any.serveTo(customer: Customer) = println("Dr. ${customer.name}! The tea was served with $this!")
