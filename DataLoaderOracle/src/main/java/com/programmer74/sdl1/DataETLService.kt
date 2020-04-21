package com.programmer74.sdl1

import com.programmer74.sdl1.dataretrieve.MongoDumpedDataRetriever
import com.programmer74.sdl1.dataretrieve.MysqlDumpedDataRetriever
import com.programmer74.sdl1.dataretrieve.OracleDumpedDataRetriever
import com.programmer74.sdl1.dataretrieve.PostgreDumpedDataRetriever
import com.programmer74.sdl1.dtos.AssessmentDtoFromOracle
import com.programmer74.sdl1.dtos.AssessmentDtoFromPostgre
import com.programmer74.sdl1.dtos.PersonDtoFromMysql
import com.programmer74.sdl1.dtos.PersonDtoFromOracle
import com.programmer74.sdl1.finalentities.Discipline
import com.programmer74.sdl1.finalentities.MergedAssessment
import com.programmer74.sdl1.finalentities.MergedPerson
import com.programmer74.sdl1.finalrepositories.DisciplineRepository
import com.programmer74.sdl1.finalrepositories.MergedAssessmentRepository
import com.programmer74.sdl1.finalrepositories.MergedPersonRepository
import mu.KLogging
import org.springframework.stereotype.Service
import java.lang.Integer.max
import java.time.Instant
import javax.annotation.PostConstruct

@Service
class DataETLService(
  private val mongoRetriever: MongoDumpedDataRetriever,
  private val mysqlRetriever: MysqlDumpedDataRetriever,
  private val oracleRetriever: OracleDumpedDataRetriever,
  private val postgreRetriever: PostgreDumpedDataRetriever,

  private val mergedPersonRepository: MergedPersonRepository,
  private val mergedAssessmentRepository: MergedAssessmentRepository,
  private val disciplineRepository: DisciplineRepository
) {

  lateinit var mergedPersons: List<MergedPerson>

  lateinit var mergedAssessments: List<MergedAssessment>

  lateinit var disciplines: List<Discipline>

  @PostConstruct
  fun start() {
    retrieveData()
    val i = 1
  }

  private fun retrieveData() {
    if (mergedPersonRepository.findAll().isEmpty()) {
      logger.warn { "Merging and dumping mergedPersons to db" }
      val oraclePersons = oracleRetriever.getOraclePersons()
      val mysqlPerson = mysqlRetriever.getMysqlPersons()
      mergedPersons = mergePersons(oraclePersons, mysqlPerson)
      mergedPersons.forEach { mergedPersonRepository.saveAndFlush(it) }
    } else {
      logger.warn { "Already dumped mergedPersons to db" }
    }
    mergedPersons = mergedPersonRepository.findAll()

    if (disciplineRepository.findAll().isEmpty()) {
      logger.warn { "Loading disciplineRepository to db" }
      loadDisciplines()
    }
    disciplines = disciplineRepository.findAll()

    if (mergedAssessmentRepository.findAll().isEmpty()) {
      logger.warn { "Merging and dumping mergedPersons to db" }
      val oracleAssessments = oracleRetriever.getOracleAssessments()
      val postgreAssessments = postgreRetriever.getPostgreAssessments()
      mergedAssessments = mergeAssessments(oracleAssessments, postgreAssessments)
      //      mergedAssessments.forEach { mergedAssessmentRepository.saveAndFlush(it) }
    } else {
      logger.warn { "Already dumped mergedPersons to db" }
    }
    //    mergedAssessments = mergedAssessmentRepository.findAll()
    val o = 1
  }

  private fun loadDisciplines() {
    val disciplinesFromPostgre = postgreRetriever.getPostgreDisciplines()
    disciplinesFromPostgre.map {
      Discipline(
          it.id,
          it.universityName,
          it.studyStandard,
          it.disciplineName,
          it.studyForm,
          it.faculty,
          it.speciality,
          it.semester,
          it.lectionHours,
          it.practiceHours,
          it.labHours,
          it.isExam)
    }.forEach { disciplineRepository.saveAndFlush(it) }
  }

  private fun mergePersons(
    oraclePersons: List<PersonDtoFromOracle>,
    mysqlPersons: List<PersonDtoFromMysql>
  ): List<MergedPerson> {

    return mergeCollections(
        oraclePersons,
        mysqlPersons,
        { oracle, mysql -> oracle.name == mysql.name },
        {
          MergedPerson(
              -1,
              it.sid,
              it.name,
              Instant.ofEpochMilli(it.birthDate),
              it.birthPlace,
              it.faculty,
              it.position,
              it.isContractStudent,
              Instant.ofEpochMilli(it.contractFrom),
              Instant.ofEpochMilli(it.contractTo),
              it.id,
              null)
        },
        { it, mysql ->
          MergedPerson(
              -1,
              it.sid,
              it.name,
              Instant.ofEpochMilli(it.birthDate),
              it.birthPlace,
              it.faculty,
              it.position,
              it.isContractStudent,
              Instant.ofEpochMilli(it.contractFrom),
              Instant.ofEpochMilli(it.contractTo),
              it.id,
              mysql.id)
        },
        {
          MergedPerson(
              -1,
              null,
              it.name,
              null,
              null,
              null,
              it.position,
              null,
              null,
              null,
              null,
              it.id)
        },
        { it.name }
    )
  }

  private fun personByOracleId(id: Int) =
      mergedPersons.firstOrNull { it.idFromOracle != null && it.idFromOracle == id }

  private fun personByMysqlId(id: Int) =
      mergedPersons.firstOrNull { it.idFromMysql != null && it.idFromMysql == id }

  private fun personByName(name: String) =
      mergedPersons.firstOrNull { it.name == name }

  private fun mergeAssessments(
    oracleAssessments: List<AssessmentDtoFromOracle>,
    postgreAssessments: List<AssessmentDtoFromPostgre>
  ): List<MergedAssessment> {

    return mergeCollections(
        oracleAssessments,
        postgreAssessments,
        { oracle, postgre ->
          val person = personByOracleId(oracle.achievedByIdFromOracle)
          (oracle.achievedAt == postgre.date) &&
              (person != null) &&
              (person.name == postgre.studentName)
        }, //TODO: FIX DISCIPLINES
        {
          val person = personByOracleId(it.achievedByIdFromOracle)
          MergedAssessment(
              -1,
              null,
              it.mark,
              it.markLetter,
              Instant.ofEpochMilli(it.achievedAt),
              null,
              null,
              person?.name,
              person?.sid,
              person?.id
          )
        },
        { ora, ps ->
          MergedAssessment(
              -1,
              ps.disciplineIdFromPostgre,
              max(ps.score, ora.mark),
              ora.markLetter,
              Instant.ofEpochMilli(ps.date),
              ps.teacherName,
              null,
              ps.studentName,
              null,
              personByName(ps.studentName)?.id
          )
        },
        { ps ->
          MergedAssessment(
              -1,
              ps.disciplineIdFromPostgre,
              ps.score,
              null,
              Instant.ofEpochMilli(ps.date),
              ps.teacherName,
              null,
              ps.studentName,
              null,
              personByName(ps.studentName)?.id
          )
        },
        { "${it.achievedAt} ${it.studentName} ${it.mark}" }
    )
  }

  companion object : KLogging()
}