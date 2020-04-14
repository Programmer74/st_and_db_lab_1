package com.programmer74.sdl1.dtos

data class PublicationDto(
  var id: Int,
  var name: String,
  var type: String,
  var language: String,
  var source: String,
  var pages: Int,
  var sourceType: String,
  var quoteIndex: Int,
  var date: Long,
  var authorIDsFromMysql: List<Int>
)