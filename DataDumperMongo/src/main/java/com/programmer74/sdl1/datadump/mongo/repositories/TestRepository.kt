package com.programmer74.sdl1.datadump.mongo.repositories

import com.programmer74.sdl1.mongo.entities.DormitoryInhabitat
import com.programmer74.sdl1.mongo.entities.DormitoryPerson
import org.springframework.data.mongodb.repository.MongoRepository

interface DormitoryPersonRepository: MongoRepository<DormitoryPerson, String>
interface DormitoryInhabitatRepository: MongoRepository<DormitoryInhabitat, String>