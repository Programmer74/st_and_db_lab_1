package com.programmer74.sdl1.finalrepositories

import com.programmer74.sdl1.finalentities.*
import org.springframework.data.jpa.repository.JpaRepository

interface MergedPersonRepository : JpaRepository<MergedPerson, Int>
interface MergedAssessmentRepository : JpaRepository<MergedAssessment, Int>
interface DisciplineRepository : JpaRepository<Discipline, Int>
interface LessonEntryRepository : JpaRepository<LessonEntry, Int>
interface StudyGroupRepository : JpaRepository<StudyGroup, Int>