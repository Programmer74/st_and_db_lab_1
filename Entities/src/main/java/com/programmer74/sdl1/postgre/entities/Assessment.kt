package com.programmer74.sdl1.postgre.entities

import javax.persistence.*

@Entity
@Table(name = "assessment")
data class Assessment(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  val id: Int?,

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "discipline_id", nullable = false)
  val discipline: Discipline,

  @Column(nullable = false)
  val score: Int,

  @Column(nullable = false)
  val date: Long,

  @Column(nullable = false)
  val teacherName: String,

  @Column(nullable = false)
  val teacherId: String,

  @Column(nullable = false)
  val studentName: String,

  @Column(nullable = false)
  val studentId: String
) {
  override fun toString(): String {
    return "Assessment(id=$id, discipline=${discipline.id}-${discipline.disciplineName}, " +
        "score=$score, date=$date, teacherName='$teacherName', teacherId='$teacherId', " +
        "studentName='$studentName', studentId='$studentId')"
  }
}