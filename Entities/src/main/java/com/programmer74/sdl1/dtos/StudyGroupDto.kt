package com.programmer74.sdl1.dtos

data class StudyGroupDto(
  val id: Int,
  val name: String,
  val studyForm: String,
  val school: String,
  val speciality: String,
  val qualification: String,
  val studyYear: String,
  val lessonsEntryIdsFromOracle: MutableList<Int>,
  val participantsIdsFromOracle: MutableList<Int>
)