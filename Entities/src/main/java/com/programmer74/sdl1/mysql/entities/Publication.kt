package com.programmer74.sdl1.mysql.entities

import javax.persistence.*

@Entity
@Table(name = "publication")
data class Publication(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  val id: Int? = null,

  @Column(nullable = false)
  val name: String,

  @Column(nullable = false)
  val type: String,

  @Column(nullable = false)
  val language: String,

  @Column(nullable = false)
  val source: String,

  @Column(nullable = false)
  val pages: Int,

  @Column(nullable = false)
  val sourceType: String,

  @Column(nullable = false)
  val quoteIndex: Int,

  @Column(nullable = false)
  val date: Long
)