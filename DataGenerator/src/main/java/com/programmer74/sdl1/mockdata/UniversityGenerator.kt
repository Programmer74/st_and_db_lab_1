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

  val positionNames = listOf(
      "студент бакалавриата", "студент магистратуры", "доцент"
  )

  val conferenceNames = listOf(
      "КМУ", "Майоровские чтения"
  )

  val publicationTypes = listOf(
      "статья", "тезисы"
  )

  val languages = listOf(
      "русский"
  )

  val sources = listOf(
      "Москва", "Тверь", "Санкт-Петербург"
  )

  val sourceTypes = listOf(
      "ВАК", "РИНЦ"
  )

  val groupNames = listOf(
      "P3111",
      "P3211",
      "P3311",
      "P3411",
      "P4116",
      "P41142"
  )

  val schoolNames = listOf(
      "09.04.04 – Программная инженерия"
  )

  val qualificationNames = listOf(
      "бакалавр", "магистр"
  )

  val dormitories = listOf(
      "Вяземский пер., 3А",
      "Белорусская ул., 6"
  )

  fun getRandomSemesterNumber() = (1 until 8).random()
  fun getRandomHours() = (0 until 40).random()
  fun getRandomScore() = (0 until 100).random()
}