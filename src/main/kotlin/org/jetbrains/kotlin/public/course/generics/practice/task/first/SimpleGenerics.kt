package org.jetbrains.kotlin.public.course.generics.practice.task.first

/*
Simple Generic Class

In this task, you need to add a type parameter into a simple class TreeNode that represents
a node of a binary tree holding a value of a user-defined class.
Code that creates nodes passing instances of Person and Decision classes,
and tries to access their properties, fails to compile because the value type in the node is declared as Any.

Once the type argument is introduced, the code of printNodes function will become compilable.
* */

// TODO: introduce a type parameter to allow for using TreeNode as generic class.
// Function "printNodes" below should become compilable after that.

/**
 * A binary tree node that holds some value.
 */
data class TreeNode(val value: Any) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

/**
 * Person objects are supposed to serve as values in a genealogical binary tree.
 */
data class Person(val name: String, val birthYear: Int, val nick: String? = null)

/**
 * Decision objects are supposed to serve as values in a decision tree.
 */
data class Decision(val question: String, val answerLeft: String, val answerRight: String)


/**
 * In this function, we create the root nodes of two binary trees, one with Person value and another with
 * Decision value. This code is not yet compilable, but it will become compilable as soon as you make a generic
 * TreeNode class.
 */
fun printNodes(): String {
    // PLEASE DON'T CHANGE THE CODE OF THIS FUNCTION.
    val richardI = Person("Richard I", 1157, "The Lionheart")
    val genealogyTree = TreeNode(richardI)

    val decisionI = Decision("Do you choose a red or a blue pill?", "Red", "Blue")
    val decisionTree = TreeNode(decisionI)
    TODO("Uncomment code bellow and make the file compilable")
//    return "${genealogyTree.value.name} ${decisionTree.value.answerLeft}"
}

fun main() {
    println(printNodes())
}