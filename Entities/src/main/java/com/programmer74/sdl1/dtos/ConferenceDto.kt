package com.programmer74.sdl1.dtos

data class ConferenceDto(
  var id: Int,
  var name: String,
  var place: String,
  var date: Long,
  var participantsFromMysql: List<Int>
)