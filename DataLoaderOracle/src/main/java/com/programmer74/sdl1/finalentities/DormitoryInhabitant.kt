package com.programmer74.sdl1.finalentities

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "dormitory_inhabitat")
data class DormitoryInhabitant(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  val id: Int? = null,

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "person_id", nullable = false)
  val person: MergedPerson,

  @Column(nullable = false)
  var price: Int,

  @Column(nullable = false)
  var latestAction: String,

  @Column(nullable = false)
  var latestActionAt: Instant,

  @Column(nullable = false)
  var livesFrom: Instant,

  @Column(nullable = false)
  var livesTo: Instant,

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "dormitory_room_id", nullable = false)
  val dormitoryRoom: DormitoryRoom
)