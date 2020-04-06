package com.programmer74.sdl1.mockdata

import java.time.Duration
import java.time.Instant

object NameSurnameGenerator {

  fun getRandomName(): String {
    val shouldBeFemale = randomBool()

    val nameIndex = (0 until maleNames.size).random()
    val name = if (shouldBeFemale) femaleNames[nameIndex] else maleNames[nameIndex]

    val surnameIndex = (0 until maleSurnames.size).random()
    val surname = if (shouldBeFemale) femaleSurnames[surnameIndex] else maleSurnames[surnameIndex]

    return "$surname $name"
  }

  fun getRandomName(value: Double): String {
    val shouldBeFemale = value <= 0.5

    val nameIndex = (value * (maleNames.size - 1)).toInt()
    val name = if (shouldBeFemale) femaleNames[nameIndex] else maleNames[nameIndex]

    val surnameIndex = ((1.0 - value) * (maleNames.size - 1)).toInt()
    val surname = if (shouldBeFemale) femaleSurnames[surnameIndex] else maleSurnames[surnameIndex]

    return "$surname $name"
  }

  fun generateRandomBirthDate() =
      Instant.now().minusMillis(Duration.ofHours(20 * 365 * 24).toMillis())

  fun studentIdByName(s: String) = "s${s.hashCode()}"

  val birthPlaces = listOf(
      "Москва",
      "Санкт-Петербург",
      "Челябинск"
  )

  private val maleNames = listOf(
      "Александр",
      "Дмитрий",
      "Максим",
      "Сергей",
      "Андрей",
      "Алексей",
      "Артем",
      "Илья",
      "Кирилл",
      "Михаил",
      "Владимир",
      "Денис",
      "Егор",
      "Иван",
      "Матвей",
      "Никита",
      "Роман",
      "Тимофей"
  )

  private val femaleNames = listOf(
      "София",
      "Мария",
      "Анна",
      "Алиса",
      "Полина",
      "Алена",
      "Кира",
      "Кристина",
      "Вероника",
      "Таисия",
      "Лилия",
      "Анжелика",
      "Белла",
      "Илона",
      "Анастасия",
      "Нелли",
      "Екатерина",
      "Юлия"
  )

  private val maleSurnames = listOf(
      "Иванов",
      "Смирнов",
      "Кузнецов",
      "Попов",
      "Васильев",
      "Петров",
      "Соколов",
      "Михайлов",
      "Новиков",
      "Фёдоров",
      "Морозов",
      "Волков",
      "Алексеев",
      "Лебедев",
      "Семёнов",
      "Егоров",
      "Павлов",
      "Козлов",
      "Степанов",
      "Николаев",
      "Орлов",
      "Андреев",
      "Макаров",
      "Никитин",
      "Захаров"
  )

  private val femaleSurnames = maleSurnames.map { "${it}а" }
}