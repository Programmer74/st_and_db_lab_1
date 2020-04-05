package com.programmer74.sdl1.oracle.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Test(

  @Id
  val a: Int,

  @Column
  val b: Int
)