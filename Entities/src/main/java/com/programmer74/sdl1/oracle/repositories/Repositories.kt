package com.programmer74.sdl1.oracle.repositories

import com.programmer74.sdl1.oracle.entities.Test
import org.springframework.data.repository.CrudRepository

interface TestRepository: CrudRepository<Test, Int>