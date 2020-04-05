package com.programmer74.sdl1.oracle.entities

import javax.persistence.*

@Entity
@Table(name = "study_group")
data class StudyGroup(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  val id: Int? = null,

  @Column(nullable = false)
  val name: String,

  @Column(nullable = false)
  val studyForm: String,

  @Column(nullable = false)
  val school: String,

  @Column(nullable = false)
  val speciality: String,

  @Column(nullable = false)
  val qualification: String,

  @Column(nullable = false)
  val studyYear: String
)