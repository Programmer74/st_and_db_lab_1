package com.programmer74.sdl1.dtos

data class DormitoryDto(
  var address: String,
  var totalRooms: Int
)

data class DormitoryPersonDto(
  var id: String,
  var name: String,
  var isBeneficiary: Boolean,
  var isContractStudent: Boolean
)

data class DormitoryInhabitatDto(
  var person: DormitoryPersonDto,
  var price: Int,
  var latestAction: String,
  var latestActionAt: Long,
  var livesFrom: Long,
  var livesTo: Long,
  var livesAt: DormitoryRoomDto
)

data class DormitoryRoomDto(
  var dormitory: DormitoryDto,
  var roomNo: Int,
  var maxInhabitats: Int,
  var latestDesinfection: Long,
  var insects: Boolean,
  var warnings: Int
)

