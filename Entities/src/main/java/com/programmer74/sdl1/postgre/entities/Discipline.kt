package com.programmer74.sdl1.postgre.entities

import javax.persistence.*

@Entity
@Table(name = "discipline")
data class Discipline(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  val id: Int?,

  @Column(nullable = false)
  val universityName: String,

  @Column(nullable = false)
  val studyStandard: String,

  @Column(nullable = false)
  val disciplineName: String,

  @Column(nullable = false)
  val studyForm: String,

  @Column(nullable = false)
  val faculty: String,

  @Column(nullable = false)
  val speciality: String,

  @Column(nullable = false)
  val semester: Int,

  @Column(nullable = false)
  val lectionHours: Int,

  @Column(nullable = false)
  val practiceHours: Int,

  @Column(nullable = false)
  val labHours: Int,

  @Column(nullable = false)
  val isExam: Boolean
)