package com.programmer74.sdl1.repositories

import com.programmer74.sdl1.entities.MergedAssessment
import com.programmer74.sdl1.entities.MergedPerson
import org.springframework.data.jpa.repository.JpaRepository

interface MergedPersonRepository : JpaRepository<MergedPerson, Int>
interface MergedAssessmentRepository : JpaRepository<MergedAssessment, Int>