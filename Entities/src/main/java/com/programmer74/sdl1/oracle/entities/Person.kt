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
  val sid: String,

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

  @OneToMany(mappedBy = "achievedBy", fetch = FetchType.EAGER)
  val assessments: MutableSet<Assessment> = HashSet(),

  @ManyToMany(mappedBy = "participants", fetch = FetchType.EAGER)
  val studyGroups: MutableSet<StudyGroup> = HashSet()
) {
  override fun toString(): String {
    return "Person(id=$id, sid='$sid', name='$name', birthDate=$birthDate, birthPlace='$birthPlace', " +
        "faculty='$faculty', position='$position', isContractStudent=$isContractStudent, " +
        "contractFrom=$contractFrom, contractTo=$contractTo)"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Person

    if (id != other.id) return false
    if (name != other.name) return false
    if (birthDate != other.birthDate) return false
    if (birthPlace != other.birthPlace) return false
    if (faculty != other.faculty) return false
    if (position != other.position) return false
    if (isContractStudent != other.isContractStudent) return false
    if (contractFrom != other.contractFrom) return false
    if (contractTo != other.contractTo) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id ?: 0
    result = 31 * result + name.hashCode()
    result = 31 * result + birthDate.hashCode()
    result = 31 * result + birthPlace.hashCode()
    result = 31 * result + faculty.hashCode()
    result = 31 * result + position.hashCode()
    result = 31 * result + isContractStudent.hashCode()
    result = 31 * result + contractFrom.hashCode()
    result = 31 * result + contractTo.hashCode()
    return result
  }
}