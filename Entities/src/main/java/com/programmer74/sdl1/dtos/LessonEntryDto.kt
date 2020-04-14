package com.programmer74.sdl1.dtos

data class LessonEntryDto(
  var id: Int,
  var name: String,
  var teacherIdFromOracle: Int,
  var weekday: Int,
  var hour: Int,
  var minute: Int,
  var room: String
)