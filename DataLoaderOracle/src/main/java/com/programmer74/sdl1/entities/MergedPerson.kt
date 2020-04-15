package com.programmer74.sdl1.entities

import java.time.Instant

data class MergedPerson(
  var id: Int,
  var sid: String?,
  var name: String,
  var birthDate: Instant?,
  var birthPlace: String?,
  var faculty: String?,
  var position: String,
  var isContractStudent: Boolean?,
  var contractFrom: Instant?,
  var contractTo: Instant?,
  var idFromOracle: Int?,
  var idFromMysql: Int?
)