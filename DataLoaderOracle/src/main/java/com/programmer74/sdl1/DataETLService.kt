package com.programmer74.sdl1

import com.programmer74.sdl1.dataretrieve.MongoDumpedDataRetriever
import com.programmer74.sdl1.dataretrieve.MysqlDumpedDataRetriever
import com.programmer74.sdl1.dataretrieve.OracleDumpedDataRetriever
import com.programmer74.sdl1.dataretrieve.PostgreDumpedDataRetriever
import com.programmer74.sdl1.dtos.PersonDtoFromMysql
import com.programmer74.sdl1.dtos.PersonDtoFromOracle
import com.programmer74.sdl1.entities.MergedPerson
import org.springframework.stereotype.Service
import java.time.Instant
import javax.annotation.PostConstruct

@Service
class DataETLService(
  private val mongoRetriever: MongoDumpedDataRetriever,
  private val mysqlRetriever: MysqlDumpedDataRetriever,
  private val oracleRetriever: OracleDumpedDataRetriever,
  private val postgreRetriever: PostgreDumpedDataRetriever
) {

  lateinit var mergedPersons: List<MergedPerson>

  @PostConstruct
  fun start() {
    retrieveData()
    val i = 1
  }

  private fun retrieveData() {
    val oraclePersons = oracleRetriever.getOraclePersons()
    val mysqlPerson = mysqlRetriever.getMysqlPersons()
    mergedPersons = mergePersons(oraclePersons, mysqlPerson)
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
}