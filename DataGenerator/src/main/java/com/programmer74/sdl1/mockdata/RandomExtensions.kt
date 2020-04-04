package com.programmer74.sdl1.mockdata

import java.util.*

fun IntRange.random() =
    Random().nextInt((endInclusive + 1) - start) + start

fun randomBool() = (0..1).random() == 0
fun List<String>.random() = this[(0 until this.size).random()]