package com.programmer74.sdl1.oracle.entities

import javax.persistence.*

@Entity
@Table(name = "study_group")
data class StudyGroup(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  val id: Int? = null,

  @Column(nullable = false)
  val name: String,

  @Column(nullable = false)
  val studyForm: String,

  @Column(nullable = false)
  val school: String,

  @Column(nullable = false)
  val speciality: String,

  @Column(nullable = false)
  val qualification: String,

  @Column(nullable = false)
  val studyYear: String,

  @OneToMany(mappedBy = "studyGroup", fetch = FetchType.EAGER)
  val lessons: MutableSet<LessonEntry> = HashSet(),

  @ManyToMany(
      fetch = FetchType.EAGER,
      cascade = [
        CascadeType.PERSIST,
        CascadeType.MERGE
      ])
  @JoinTable(
      name = "study_group_participant",
      joinColumns = [JoinColumn(name = "study_group_id")],
      inverseJoinColumns = [JoinColumn(name = "person_id")])
  val participants: MutableList<Person> = ArrayList()
) {
  fun addParticipant(person: Person) {
    participants.add(person)
    person.studyGroups.add(this)
  }

  override fun toString(): String {
    return "StudyGroup(id=$id, name='$name', studyForm='$studyForm', school='$school', " +
        "speciality='$speciality', qualification='$qualification', studyYear='$studyYear', " +
        "participants: ${participants.size})"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as StudyGroup

    if (id != other.id) return false
    if (name != other.name) return false
    if (studyForm != other.studyForm) return false
    if (school != other.school) return false
    if (speciality != other.speciality) return false
    if (qualification != other.qualification) return false
    if (studyYear != other.studyYear) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id ?: 0
    result = 31 * result + name.hashCode()
    result = 31 * result + studyForm.hashCode()
    result = 31 * result + school.hashCode()
    result = 31 * result + speciality.hashCode()
    result = 31 * result + qualification.hashCode()
    result = 31 * result + studyYear.hashCode()
    return result
  }
}