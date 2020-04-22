package com.programmer74.sdl1

import com.programmer74.sdl1.dataretrieve.MongoDumpedDataRetriever
import com.programmer74.sdl1.dataretrieve.MysqlDumpedDataRetriever
import com.programmer74.sdl1.dataretrieve.OracleDumpedDataRetriever
import com.programmer74.sdl1.dataretrieve.PostgreDumpedDataRetriever
import com.programmer74.sdl1.dtos.*
import com.programmer74.sdl1.finalentities.Discipline
import com.programmer74.sdl1.finalentities.LessonEntry
import com.programmer74.sdl1.finalentities.MergedAssessment
import com.programmer74.sdl1.finalentities.MergedPerson
import com.programmer74.sdl1.finalrepositories.DisciplineRepository
import com.programmer74.sdl1.finalrepositories.LessonEntryRepository
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
  private val disciplineRepository: DisciplineRepository,
  private val lessonEntryRepository: LessonEntryRepository
) {

  //oracle:  Assessment, Person     - 2/4
  //postgre: Assessment, Discipline - 2/2 - OK
  //mysql:   Person - 1/5
  //mongo - 0/4

  lateinit var mergedPersons: List<MergedPerson>

  lateinit var mergedAssessments: List<MergedAssessment>

  lateinit var disciplines: MutableList<Discipline>

  lateinit var lessonEntries: List<LessonEntry>

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
      mergedPersons = mergePersons(oraclePersons, mysqlPerson).toMutableList()
      mergedPersons.forEach { mergedPersonRepository.saveAndFlush(it) }
    } else {
      logger.warn { "Already dumped mergedPersons to db" }
    }
    mergedPersons = mergedPersonRepository.findAll()

    disciplines = getOrLoadCollection("disciplines",
        postgreRetriever.getPostgreDisciplines(),
        disciplineRepository) { it.toDiscipline() }
    lessonEntries = getOrLoadCollection("lesson entries",
        oracleRetriever.getOracleLessonEntries(),
        lessonEntryRepository) { it.toLessonEntry() }

    if (mergedAssessmentRepository.findAll().isEmpty()) {
      logger.warn { "Merging and dumping mergedAssessments to db" }
      val oracleAssessments = oracleRetriever.getOracleAssessments()
      val postgreAssessments = postgreRetriever.getPostgreAssessments()
      mergedAssessments = mergeAssessments(oracleAssessments, postgreAssessments).toMutableList()
      mergedAssessments.forEach { mergedAssessmentRepository.saveAndFlush(it) }
    } else {
      logger.warn { "Already dumped mergedAssessments to db" }
    }
    mergedAssessments = mergedAssessmentRepository.findAll()
    val o = 1
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

  private fun getOrAddDisciplineByName(name: String): Discipline {
    val existingDiscipline = disciplines.firstOrNull { it.disciplineName == name }
    if (existingDiscipline != null) return existingDiscipline
    val newDiscipline = Discipline(id = null, disciplineName = name)
    val addedDiscipline = disciplineRepository.saveAndFlush(newDiscipline)
    disciplines.add(addedDiscipline)
    return addedDiscipline
  }

  private fun disciplineByPostgresId(id: Int) =
      disciplines.first { it.disciplineIdFromPostgre == id }

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
        },
        {
          val person = personByOracleId(it.achievedByIdFromOracle)
          MergedAssessment(
              -1,
              getOrAddDisciplineByName(it.disciplineName).id!!,
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
              disciplineByPostgresId(ps.disciplineIdFromPostgre).id!!,
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
              disciplineByPostgresId(ps.disciplineIdFromPostgre).id!!,
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