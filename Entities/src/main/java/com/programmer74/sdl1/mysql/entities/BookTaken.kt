package com.programmer74.sdl1.mysql.entities

import javax.persistence.*

@Entity
@Table(name = "book_taken")
data class BookTaken(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  val id: Int? = null,

  @Column(nullable = false)
  val bookName: String,

  @Column(nullable = false)
  val takenBy: Int,

  @Column(nullable = false)
  val takenAt: Long,

  @Column(nullable = false)
  var returnedAt: Long
)