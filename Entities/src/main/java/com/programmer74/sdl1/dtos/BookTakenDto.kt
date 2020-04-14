package com.programmer74.sdl1.dtos

data class BookTakenDto(
  var id: Int,
  var bookName: String,
  var takenByIdFromMysql: Int,
  var takenAt: Long,
  var returnedAt: Long
)