package com.programmer74.sdl1.postgre.repositories

import com.programmer74.sdl1.postgre.entities.Discipline
import org.springframework.data.repository.CrudRepository

interface DisciplineRepository : CrudRepository<Discipline, Int>