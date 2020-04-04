package com.programmer74.sdl1.mysql.repositories

import com.programmer74.sdl1.mysql.entities.Test
import org.springframework.data.repository.CrudRepository

interface TestRepository : CrudRepository<Test, Int> {
}