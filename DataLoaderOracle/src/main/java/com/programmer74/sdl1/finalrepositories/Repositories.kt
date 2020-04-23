package com.programmer74.sdl1.finalrepositories

import com.programmer74.sdl1.finalentities.*
import org.springframework.data.jpa.repository.JpaRepository

interface MergedPersonRepository : JpaRepository<MergedPerson, Int>
interface MergedAssessmentRepository : JpaRepository<MergedAssessment, Int>

interface DisciplineRepository : JpaRepository<Discipline, Int>
interface LessonEntryRepository : JpaRepository<LessonEntry, Int>
interface StudyGroupRepository : JpaRepository<StudyGroup, Int>

interface ConferenceRepository : JpaRepository<Conference, Int>
interface PublicationRepository : JpaRepository<Publication, Int>
interface ProjectRepository : JpaRepository<Project, Int>
interface BookTakenRepository : JpaRepository<BookTaken, Int>

interface DormitoryRepository : JpaRepository<Dormitory, Int>
interface DormitoryRoomRepository: JpaRepository<DormitoryRoom, Int>
interface DormitoryInhabitatRepository: JpaRepository<DormitoryInhabitant, Int>
