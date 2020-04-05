package com.programmer74.sdl1.mysql.repositories

import com.programmer74.sdl1.mysql.entities.*
import org.springframework.data.repository.CrudRepository

interface PersonRepository : CrudRepository<Person, Int>
interface ConferenceRepository : CrudRepository<Conference, Int>
interface PublicationRepository : CrudRepository<Publication, Int>
interface ProjectRepository : CrudRepository<Project, Int>
interface BookTakenRepository : CrudRepository<BookTaken, Int>