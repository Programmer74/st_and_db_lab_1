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
  val dateTo: Long,

  @ManyToMany(
      fetch = FetchType.EAGER,
      cascade = [
        CascadeType.PERSIST,
        CascadeType.MERGE
      ])
  @JoinTable(
      name = "project_worker",
      joinColumns = [JoinColumn(name = "project_id")],
      inverseJoinColumns = [JoinColumn(name = "person_id")])
  val workers: MutableList<Person> = ArrayList()
) {
  fun addWorker(person: Person) {
    workers.add(person)
    person.projects.add(this)
  }

  override fun toString(): String {
    return "Project(id=$id, name='$name', dateFrom=$dateFrom, dateTo=$dateTo, workers: ${workers.size})"
  }
}