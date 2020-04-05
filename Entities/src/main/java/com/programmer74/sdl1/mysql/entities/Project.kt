package com.programmer74.sdl1.mysql.entities

import javax.persistence.*

@Entity
@Table(name = "project")
data class Project(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  val id: Int? = null,

  @Column(nullable = false)
  val name: String,

  @Column(nullable = false)
  val dateFrom: Long,

  @Column(nullable = false)
  val dateTo: Long
)