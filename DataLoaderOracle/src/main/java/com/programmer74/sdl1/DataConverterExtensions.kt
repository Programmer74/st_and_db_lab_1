package com.programmer74.sdl1

import com.programmer74.sdl1.dtos.DisciplineDto
import com.programmer74.sdl1.dtos.LessonEntryDto
import com.programmer74.sdl1.finalentities.Discipline
import com.programmer74.sdl1.finalentities.LessonEntry

fun DisciplineDto.toDiscipline() = Discipline(
    this.id,
    this.universityName,
    this.studyStandard,
    this.disciplineName,
    this.studyForm,
    this.faculty,
    this.speciality,
    this.semester,
    this.lectionHours,
    this.practiceHours,
    this.labHours,
    this.isExam,
    this.id)

fun LessonEntryDto.toLessonEntry() = LessonEntry(
    this.id, this.name, this.teacherIdFromOracle, this.weekday, this.hour, this.minute, this.room
)
