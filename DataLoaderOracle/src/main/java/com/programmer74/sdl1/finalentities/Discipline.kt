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
  val universityName: String,

  @Column
  val studyStandard: String,

  @Column
  val disciplineName: String,

  @Column
  val studyForm: String,

  @Column
  val faculty: String,

  @Column
  val speciality: String,

  @Column
  val semester: Int,

  @Column
  val lectionHours: Int,

  @Column
  val practiceHours: Int,

  @Column
  val labHours: Int,

  @Column
  val isExam: Boolean
)