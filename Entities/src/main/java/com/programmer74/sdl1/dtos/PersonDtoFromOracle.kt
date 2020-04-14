package com.programmer74.sdl1.dtos

data class PersonDtoFromOracle(
  var id: Int,
  var sid: String,
  var name: String,
  var birthDate: Long,
  var birthPlace: String,
  var faculty: String,
  var position: String,
  var isContractStudent: Boolean,
  var contractFrom: Long,
  var contractTo: Long
)