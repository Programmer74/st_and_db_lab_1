package com.programmer74.sdl1.finalentities

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "merged_assessment")
data class MergedAssessment(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  var id: Int?,

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "discipline_id", nullable = false)
  val discipline: Discipline,

  @Column
  var mark: Int?,

  @Column
  var markLetter: String?,

  @Column
  var achievedAt: Instant?,

  @Column
  var teacherName: String?,

  @Column
  var teacherSid: String?,

  @Column
  var studentName: String?,

  @Column
  var studentSid: String?,

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "student_id", nullable = false)
  val student: MergedPerson
)