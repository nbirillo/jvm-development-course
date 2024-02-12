package org.jetbrains.kotlin.public.course.introduction

data class Employee(val department: Department? = null)

data class Department(val id: String, val head: Head? = null)

data class Head(val id: String, val name: String)
