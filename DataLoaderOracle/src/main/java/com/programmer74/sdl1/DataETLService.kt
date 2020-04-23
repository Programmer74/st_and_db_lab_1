package com.programmer74.sdl1

import com.programmer74.sdl1.dataretrieve.MongoDumpedDataRetriever
import com.programmer74.sdl1.dataretrieve.MysqlDumpedDataRetriever
import com.programmer74.sdl1.dataretrieve.OracleDumpedDataRetriever
import com.programmer74.sdl1.dataretrieve.PostgreDumpedDataRetriever
import com.programmer74.sdl1.dtos.AssessmentDtoFromOracle
import com.programmer74.sdl1.dtos.AssessmentDtoFromPostgre
import com.programmer74.sdl1.dtos.PersonDtoFromMysql
import com.programmer74.sdl1.dtos.PersonDtoFromOracle
import com.programmer74.sdl1.finalentities.*
import com.programmer74.sdl1.finalrepositories.*
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
  private val lessonEntryRepository: LessonEntryRepository,
  private val studyGroupRepository: StudyGroupRepository,
  private val conferenceRepository: ConferenceRepository,
  private val publicationRepository: PublicationRepository,
  private val projectRepository: ProjectRepository,
  private val bookTakenRepository: BookTakenRepository
) {

  //oracle:  Assessment, Person, LessonEntry, StudyGroup         - 4/4 - OK
  //postgre: Assessment, Discipline                              - 2/2 - OK
  //mysql:   Person, Conference, Publication, Project, BookTaken - 5/5 - OK
  //mongo                                                        - 0/4

  lateinit var mergedPersons: MutableList<MergedPerson>

  lateinit var mergedAssessments: List<MergedAssessment>

  lateinit var disciplines: MutableList<Discipline>

  lateinit var lessonEntries: List<LessonEntry>

  lateinit var studyGroups: List<StudyGroup>

  lateinit var conferences: List<Conference>

  lateinit var publications: List<Publication>

  lateinit var projects: List<Project>

  lateinit var booksTaken: List<BookTaken>

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

    loadSimplePostgreCollections()
    loadSimpleOracleCollections()
    loadSimpleMysqlCollections()

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

  private fun loadSimplePostgreCollections() {
    disciplines = getOrLoadCollection(
        "disciplines",
        postgreRetriever.getPostgreDisciplines(),
        disciplineRepository, { it.toDiscipline() })
  }

  private fun loadSimpleOracleCollections() {
    lessonEntries = getOrLoadCollection(
        "lesson entries",
        oracleRetriever.getOracleLessonEntries(),
        lessonEntryRepository, { it.toLessonEntry() })
    studyGroups = getOrLoadCollection(
        "study groups",
        oracleRetriever.getOracleStudyGroups(),
        studyGroupRepository, {
      StudyGroup(
          it.id, it.name, it.studyForm, it.school, it.speciality, it.qualification, it.studyYear,
          it.lessonsEntryIdsFromOracle.map { lessonEntryById(it) }.toMutableList(),
          it.participantsIdsFromOracle.map { getOrAddPersonByOracleId(it) }.toMutableList()
      )
    })
  }

  private fun loadSimpleMysqlCollections() {
    conferences = getOrLoadCollection(
        "conferences",
        mysqlRetriever.getMysqlConferences(),
        conferenceRepository, {
      Conference(
          it.id, it.name, it.place, Instant.ofEpochMilli(it.date),
          it.participantsFromMysql.map { getOrAddPersonByMysqlId(it) }.toMutableList())
    })
    publications = getOrLoadCollection(
        "publications",
        mysqlRetriever.getMysqlPublications(),
        publicationRepository, {
      Publication(
          it.id,
          it.name,
          it.type,
          it.language,
          it.source,
          it.pages,
          it.sourceType,
          it.quoteIndex,
          Instant.ofEpochMilli(it.date),
          it.authorIDsFromMysql.map { getOrAddPersonByMysqlId(it) }.toMutableList())
    })
    projects = getOrLoadCollection(
        "projects",
        mysqlRetriever.getMysqlProjects(),
        projectRepository, {
      Project(
          it.id,
          it.name,
          Instant.ofEpochMilli(it.dateFrom),
          Instant.ofEpochMilli(it.dateTo),
          it.workersFromMysql.map { getOrAddPersonByMysqlId(it) }.toMutableList())
    })
    booksTaken = getOrLoadCollection(
        "books taken",
        mysqlRetriever.getMysqlBooksTaken(),
        bookTakenRepository, {
      BookTaken(
          it.id,
          it.bookName,
          getOrAddPersonByMysqlId(it.takenByIdFromMysql),
          Instant.ofEpochMilli(it.takenAt),
          Instant.ofEpochMilli(it.returnedAt))
    })
  }

  private fun personByOracleId(id: Int) =
      mergedPersons.firstOrNull { it.idFromOracle != null && it.idFromOracle == id }

  private fun personByMysqlId(id: Int) =
      mergedPersons.firstOrNull { it.idFromMysql != null && it.idFromMysql == id }

  private fun personByName(name: String) =
      mergedPersons.firstOrNull { it.name == name }

  private fun disciplineByPostgresId(id: Int) =
      disciplines.first { it.disciplineIdFromPostgre == id }

  private fun lessonEntryById(id: Int) = lessonEntries.first { it.id == id }

  private fun getOrAddPersonByOracleId(id: Int): MergedPerson {
    val ans = personByOracleId(id)
    if (ans != null) return ans
    logger.error { "Unable to find person by oracle id $id; trying search in filedump" }
    val ans2 = oracleRetriever.getOraclePersons().firstOrNull { it.id == id }
      ?: error("Unable to find person by oracle id $id in both DB and FILEDUMP")
    val ans3 = personByName(ans2.name)
    if (ans3 != null) return ans3
    logger.error {
      "Found person by oracle id $id but its name ${ans2.name} is unknown; " +
          "adding new person for such name"
    }
    val newPerson = ans2.toNewMergedPerson()
    val savedPerson = mergedPersonRepository.saveAndFlush(newPerson)
    mergedPersons.add(savedPerson)
    return savedPerson
  }

  private fun getOrAddPersonByMysqlId(id: Int): MergedPerson {
    val ans = personByMysqlId(id)
    if (ans != null) return ans
    logger.error { "Unable to find person by mysql id $id; trying search in filedump" }
    val ans2 = mysqlRetriever.getMysqlPersons().firstOrNull { it.id == id }
      ?: error("Unable to find person by oracle id $id in both DB and FILEDUMP")
    val ans3 = personByName(ans2.name)
    if (ans3 != null) return ans3
    logger.error {
      "Found person by mysql id $id but its name ${ans2.name} is unknown; " +
          "adding new person for such name"
    }
    val newPerson = ans2.toNewMergedPerson()
    val savedPerson = mergedPersonRepository.saveAndFlush(newPerson)
    mergedPersons.add(savedPerson)
    return savedPerson
  }

  private fun getOrAddDisciplineByName(name: String): Discipline {
    val existingDiscipline = disciplines.firstOrNull { it.disciplineName == name }
    if (existingDiscipline != null) return existingDiscipline
    val newDiscipline = Discipline(id = null, disciplineName = name)
    val addedDiscipline = disciplineRepository.saveAndFlush(newDiscipline)
    disciplines.add(addedDiscipline)
    return addedDiscipline
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
          it.toNewMergedPerson()
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