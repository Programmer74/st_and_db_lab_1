package com.programmer74.sdl1.mongo.repositories

import com.programmer74.sdl1.mongo.entities.Test
import org.springframework.data.mongodb.repository.MongoRepository

interface TestRepository : MongoRepository<Test, String>