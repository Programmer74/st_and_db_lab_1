package com.programmer74.sdl1.dtos

data class AssessmentDtoFromOracle(
  var id: Int,
  var name: String,
  var mark: Int,
  var markLetter: String,
  var achievedAt: Long,
  var achievedByIdFromOracle: Int
)