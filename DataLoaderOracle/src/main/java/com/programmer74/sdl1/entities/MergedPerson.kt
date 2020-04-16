package com.programmer74.sdl1.entities

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "merged_person")
data class MergedPerson(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  var id: Int?,

  @Column
  var sid: String?,

  @Column
  var name: String,

  @Column
  var birthDate: Instant?,

  @Column
  var birthPlace: String?,

  @Column
  var faculty: String?,

  @Column
  var position: String,

  @Column
  var isContractStudent: Boolean?,

  @Column
  var contractFrom: Instant?,

  @Column
  var contractTo: Instant?,

  @Column
  var idFromOracle: Int?,

  @Column
  var idFromMysql: Int?
)