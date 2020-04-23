package com.programmer74.sdl1.finalentities

import java.time.Instant
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

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "taken_by", nullable = false)
  val takenBy: MergedPerson,

  @Column(nullable = false)
  val takenAt: Instant,

  @Column(nullable = false)
  var returnedAt: Instant
)