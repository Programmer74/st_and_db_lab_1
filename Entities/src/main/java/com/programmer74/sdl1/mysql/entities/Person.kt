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
  val conferences: MutableSet<Conference> = HashSet(),

  @ManyToMany(mappedBy = "workers", fetch = FetchType.EAGER)
  val projects: MutableSet<Project> = HashSet(),

  @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
  val publications: MutableSet<Publication> = HashSet(),

  @OneToMany(mappedBy = "takenBy", fetch = FetchType.EAGER)
  val booksTaken: MutableSet<BookTaken> = HashSet()
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Person

    if (id != other.id) return false
    if (name != other.name) return false
    if (position != other.position) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id ?: 0
    result = 31 * result + name.hashCode()
    result = 31 * result + position.hashCode()
    return result
  }

  override fun toString(): String {
    return "Person(id=$id, name='$name', position='$position')"
  }
}