package com.programmer74.sdl1.oracle.entities

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "person")
data class Person(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  val id: Int? = null,

  @Column(nullable = false)
  val name: String,

  @Column(nullable = false)
  val birthDate: Instant,

  @Column(nullable = false)
  val birthPlace: String,

  @Column(nullable = false)
  val faculty: String,

  @Column(nullable = false)
  val position: String,

  @Column(nullable = false)
  val isContractStudent: Boolean,

  @Column(nullable = false)
  val contractFrom: Instant,

  @Column(nullable = false)
  val contractTo: Instant,

  @Column(nullable = false)
  val groupId: Int
)