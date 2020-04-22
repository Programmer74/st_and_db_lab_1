package com.programmer74.sdl1.finalentities

import javax.persistence.*

@Entity
@Table(name = "study_group_reloaded")
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

  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = [
        CascadeType.PERSIST,
        CascadeType.MERGE
      ])
  @JoinTable(
      name = "study_group_lesson_entry_reloaded",
      joinColumns = [JoinColumn(name = "study_group_id")],
      inverseJoinColumns = [JoinColumn(name = "lesson_entry_id")])
  val lessons: MutableList<LessonEntry> = ArrayList(),

  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = [
        CascadeType.PERSIST,
        CascadeType.MERGE
      ])
  @JoinTable(
      name = "study_group_participant_reloaded",
      joinColumns = [JoinColumn(name = "study_group_id")],
      inverseJoinColumns = [JoinColumn(name = "person_id")])
  val participants: MutableList<MergedPerson> = ArrayList()
) {
  fun addParticipant(person: MergedPerson) {
    participants.add(person)
  }

  fun addLessonEntry(lessonEntry: LessonEntry) {
    lessons.add(lessonEntry)
  }
}