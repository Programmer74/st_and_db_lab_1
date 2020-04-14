package com.programmer74.sdl1.dtos

data class ProjectDto(
  var id: Int,
  var name: String,
  var dateFrom: Long,
  var dateTo: Long,
  var workersFromMysql: List<Int>
)