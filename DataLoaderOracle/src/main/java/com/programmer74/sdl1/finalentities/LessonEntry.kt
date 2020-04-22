package com.programmer74.sdl1.finalentities

import javax.persistence.*

@Entity
@Table(name = "lesson_entry_reloaded")
data class LessonEntry(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  val id: Int? = null,

  @Column(nullable = false)
  val name: String,

  @Column(nullable = false)
  val teacherId: Int,

  @Column(nullable = false)
  val weekday: Int,

  @Column(nullable = false)
  val hour: Int,

  @Column(nullable = false)
  val minute: Int,

  @Column(nullable = false)
  val room: String
)