package com.programmer74.sdl1

import com.programmer74.sdl1.dtos.DisciplineDto
import com.programmer74.sdl1.dtos.LessonEntryDto
import com.programmer74.sdl1.dtos.PersonDtoFromMysql
import com.programmer74.sdl1.dtos.PersonDtoFromOracle
import com.programmer74.sdl1.finalentities.Discipline
import com.programmer74.sdl1.finalentities.LessonEntry
import com.programmer74.sdl1.finalentities.MergedPerson
import java.time.Instant

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

fun PersonDtoFromOracle.toNewMergedPerson() = MergedPerson(
    -1,
    this.sid,
    this.name,
    Instant.ofEpochMilli(this.birthDate),
    this.birthPlace,
    this.faculty,
    this.position,
    this.isContractStudent,
    Instant.ofEpochMilli(this.contractFrom),
    Instant.ofEpochMilli(this.contractTo),
    this.id,
    null)

fun PersonDtoFromMysql.toNewMergedPerson() = MergedPerson(
    -1,
    null,
    this.name,
    null,
    null,
    null,
    this.position,
    null,
    null,
    null,
    this.id,
    null)