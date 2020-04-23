package com.programmer74.sdl1.finalentities

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "dormitory_room")
data class DormitoryRoom(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  val id: Int? = null,

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "dormitory_id", nullable = false)
  val dormitory: Dormitory,

  @Column(nullable = false)
  val roomNo: Int,

  @Column(nullable = false)
  val maxInhabitats: Int,

  @Column(nullable = false)
  var latestDesinfection: Instant,

  @Column(nullable = false)
  var insects: Boolean,

  @Column(nullable = false)
  var warnings: Int
)