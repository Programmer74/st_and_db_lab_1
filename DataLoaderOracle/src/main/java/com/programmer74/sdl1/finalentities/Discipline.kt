package com.programmer74.sdl1.finalentities

import javax.persistence.*

@Entity
@Table(name = "discipline")
data class Discipline(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  val id: Int?,

  @Column
  val universityName: String? = null,

  @Column
  val studyStandard: String? = null,

  @Column
  val disciplineName: String? = null,

  @Column
  val studyForm: String? = null,

  @Column
  val faculty: String? = null,

  @Column
  val speciality: String? = null,

  @Column
  val semester: Int? = null,

  @Column
  val lectionHours: Int? = null,

  @Column
  val practiceHours: Int? = null,

  @Column
  val labHours: Int? = null,

  @Column
  val isExam: Boolean? = null,

  @Column
  val disciplineIdFromPostgre: Int? = null
)