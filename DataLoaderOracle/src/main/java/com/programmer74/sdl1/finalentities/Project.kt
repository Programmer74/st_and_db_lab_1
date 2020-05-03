package com.programmer74.sdl1.finalentities

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "project_ldd")
data class Project(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  val id: Int? = null,

  @Column(nullable = false)
  val name: String,

  @Column(nullable = false)
  val dateFrom: Instant,

  @Column(nullable = false)
  val dateTo: Instant,

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
  val workers: MutableList<MergedPerson> = ArrayList()
) {
  fun addWorker(person: MergedPerson) {
    workers.add(person)
  }

  override fun toString(): String {
    return "Project(id=$id, name='$name', dateFrom=$dateFrom, dateTo=$dateTo, workers: ${workers.size})"
  }
}