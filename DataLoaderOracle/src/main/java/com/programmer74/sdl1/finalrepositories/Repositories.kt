package com.programmer74.sdl1.finalrepositories

import com.programmer74.sdl1.finalentities.Discipline
import com.programmer74.sdl1.finalentities.MergedAssessment
import com.programmer74.sdl1.finalentities.MergedPerson
import org.springframework.data.jpa.repository.JpaRepository

interface MergedPersonRepository : JpaRepository<MergedPerson, Int>
interface MergedAssessmentRepository : JpaRepository<MergedAssessment, Int>
interface DisciplineRepository : JpaRepository<Discipline, Int>