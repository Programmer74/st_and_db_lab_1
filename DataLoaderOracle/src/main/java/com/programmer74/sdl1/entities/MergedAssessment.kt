package com.programmer74.sdl1.entities

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "merged_assessment")
data class MergedAssessment(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  var id: Int?,

  @Column
  var disciplineId: Int?,

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

  @Column
  var studentId: Int?
)