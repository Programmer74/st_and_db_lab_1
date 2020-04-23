package com.programmer74.sdl1.finalentities

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "conference")
data class Conference(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  val id: Int? = null,

  @Column(nullable = false)
  val name: String,

  @Column(nullable = false)
  val place: String,

  @Column(nullable = false)
  val occuredAt: Instant,

  @ManyToMany(
      fetch = FetchType.EAGER,
      cascade = [
        CascadeType.PERSIST,
        CascadeType.MERGE
      ])
  @JoinTable(
      name = "conference_participant",
      joinColumns = [JoinColumn(name = "conference_id")],
      inverseJoinColumns = [JoinColumn(name = "person_id")])
  val participants: MutableList<MergedPerson> = ArrayList()
) {
  fun addParticipant(person: MergedPerson) {
    participants.add(person)
  }

  override fun toString(): String {
    return "Conference(id=$id, name='$name', place='$place', date=$occuredAt, participants: ${participants.size})"
  }
}