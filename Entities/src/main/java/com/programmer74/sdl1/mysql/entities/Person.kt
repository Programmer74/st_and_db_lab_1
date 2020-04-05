package com.programmer74.sdl1.mysql.entities

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
  val position: String,

  @ManyToMany(mappedBy = "participants", fetch = FetchType.EAGER)
  val conferences: MutableList<Conference> = ArrayList()
)