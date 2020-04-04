package com.programmer74.sdl1.mockdata

object UniversityGenerator {

  val universityNames = listOf(
      "НИУ ИТМО"
  )

  val studyStandardNames = listOf(
      "старый",
      "новый"
  )

  val disciplineNames = listOf(
      "Компьютерная графика (2018449043-И)"
  )

  val studyFormNames = listOf(
      "очная",
      "заочная"
  )

  val facultyNames = listOf(
      "ФПИиКТ"
  )

  val specialityNames = listOf(
      "09.03.04 – Разработка программно-информационных систем (Академический магистр)"
  )

  fun getRandomSemesterNumber() = (1 until 8).random()
  fun getRandomHours() = (0 until 40).random()
  fun getRandomScore() = (0 until 100).random()
}