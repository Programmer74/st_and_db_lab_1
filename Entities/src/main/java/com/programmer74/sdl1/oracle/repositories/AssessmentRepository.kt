package com.programmer74.sdl1.postgre.repositories

import com.programmer74.sdl1.postgre.entities.Assessment
import org.springframework.data.repository.CrudRepository

interface AssessmentRepository : CrudRepository<Assessment, Int>