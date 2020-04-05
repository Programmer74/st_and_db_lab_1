package com.programmer74.sdl1.oracle.repositories

import com.programmer74.sdl1.oracle.entities.Assessment
import com.programmer74.sdl1.oracle.entities.LessonEntry
import com.programmer74.sdl1.oracle.entities.Person
import com.programmer74.sdl1.oracle.entities.StudyGroup
import org.springframework.data.repository.CrudRepository

interface PersonRepository : CrudRepository<Person, Int>
interface StudyGroupRepository : CrudRepository<StudyGroup, Int>
interface LessonEntryRepository : CrudRepository<LessonEntry, Int>
interface AssessmentRepository : CrudRepository<Assessment, Int>