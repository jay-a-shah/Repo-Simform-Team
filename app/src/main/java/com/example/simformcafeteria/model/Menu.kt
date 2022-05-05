package com.example.simformcafeteria.Model

data class Menu(
    val Date: Date
)

data class Date(
    val Evening: Evening,
    // val Lunch: Lunch,
    // val Morning: Morning
)

data class Evening (
    val menuItem: List<MenuItem>
)

data class Lunch(
    val DisLike: Int,
    val Like: Int
)

data class MenuItem(
    val DisLike: Int,
    val Like: Int
)
"Hello world"