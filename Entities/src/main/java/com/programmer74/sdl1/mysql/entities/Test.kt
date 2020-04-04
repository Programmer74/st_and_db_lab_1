package com.programmer74.sdl1.mysql.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "test")
data class Test(
  @Id
  val A: Int,
  @Column
  val B: Int
)