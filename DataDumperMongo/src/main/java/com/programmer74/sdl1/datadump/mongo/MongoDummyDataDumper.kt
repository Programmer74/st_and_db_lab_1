package com.programmer74.sdl1.datadump.mongo

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.programmer74.sdl1.datadump.mongo.repositories.DormitoryInhabitatRepository
import com.programmer74.sdl1.datadump.mongo.repositories.DormitoryPersonRepository
import com.programmer74.sdl1.dtos.DormitoryDto
import com.programmer74.sdl1.dtos.DormitoryInhabitatDto
import com.programmer74.sdl1.dtos.DormitoryPersonDto
import com.programmer74.sdl1.dtos.DormitoryRoomDto
import com.programmer74.sdl1.mapToFile
import com.programmer74.sdl1.mongo.entities.DormitoryInhabitat
import com.programmer74.sdl1.mongo.entities.DormitoryPerson
import mu.KLogging
import org.springframework.stereotype.Service
import java.io.File
import javax.annotation.PostConstruct

@Service
class MongoDummyDataDumper(
  private val dormitoryPersonRepository: DormitoryPersonRepository,
  private val dormitoryInhabitatRepository: DormitoryInhabitatRepository
) {

  private val destPath = File("/home/hotaro/st_and_db_labs_dumps/mongo")

  @PostConstruct
  fun startDumper() {
    if (dormitoryInhabitatRepository.findAll().isEmpty()) {
      logger.error { "Empty repository, cannot proceed" }
    } else {
      dumpAll()
    }
  }

  private fun dumpAll() {
    destPath.listFiles()?.forEach { it.delete() }
    destPath.mkdirs()
    val mapper = ObjectMapper().registerModule(KotlinModule())

    dormitoryPersonRepository.mapToFile(File(destPath, "1__dormitory_persons.json"), mapper) {
      it.toDto()
    }
    dormitoryInhabitatRepository.mapToFile(File(destPath, "2__dormitory_inhabitats.json"), mapper) {
      it.toDto()
    }

    logger.warn { "all OK" }
  }

  fun DormitoryPerson.toDto() = DormitoryPersonDto(id, name, isBeneficiary, isContractStudent)
  fun DormitoryInhabitat.toDto() = DormitoryInhabitatDto(
      DormitoryPersonDto(
          person.id,
          person.name,
          person.isBeneficiary,
          person.isContractStudent
      ),
      price,
      latestAction,
      latestActionAt.toEpochMilli(),
      livesFrom.toEpochMilli(),
      livesTo.toEpochMilli(),
      DormitoryRoomDto(
          DormitoryDto(livesAt.dormitory.address, livesAt.dormitory.totalRooms),
          livesAt.roomNo,
          livesAt.maxInhabitats,
          livesAt.latestDesinfection.toEpochMilli(),
          livesAt.insects,
          livesAt.warnings
      ))

  companion object : KLogging()
}