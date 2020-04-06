package com.programmer74.sdl1.mongo.entities

import java.time.Instant

data class Dormitory(
  val address: String,
  val totalRooms: Int
)

data class DormitoryPerson(
  val id: String,
  val name: String,
  val isBeneficiary: Boolean,
  val isContractStudent: Boolean
)

data class DormitoryInhabitat(
  val person: DormitoryPerson,
  var price: Int,
  var latestAction: String,
  var latestActionAt: Instant,
  var livesFrom: Instant,
  var livesTo: Instant,
  var livesAt: DormitoryRoom
)

data class DormitoryRoom(
  val dormitory: Dormitory,
  val roomNo: Int,
  val maxInhabitats: Int,
  var latestDesinfection: Instant,
  var insects: Boolean,
  var warnings: Int
)

