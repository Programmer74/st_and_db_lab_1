package com.programmer74.sdl1.oracle.entities

import javax.persistence.*

@Entity
@Table(name = "lesson_entry")
data class LessonEntry(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  val id: Int? = null,

  @Column(nullable = false)
  val name: String,

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "teacher_id", nullable = false)
  val teacher: Person,

  @Column(nullable = false)
  val weekday: Int,

  @Column(nullable = false)
  val hour: Int,

  @Column(nullable = false)
  val minute: Int,

  @Column(nullable = false)
  val room: String,

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "group_id", nullable = false)
  val studyGroup: StudyGroup
)