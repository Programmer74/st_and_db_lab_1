package com.programmer74.sdl1.finalentities

import javax.persistence.*

@Entity
@Table(name = "dormitory")
data class Dormitory(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  val id: Int? = null,

  @Column(nullable = false)
  val address: String,

  @Column(nullable = false)
  val totalRooms: Int
)