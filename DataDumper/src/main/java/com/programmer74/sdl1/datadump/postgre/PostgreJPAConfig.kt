package com.programmer74.sdl1.datadump.postgre

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@Profile("postgre")
@EntityScan(basePackages = ["com.programmer74.sdl1.postgre.entities"])
@EnableJpaRepositories(basePackages = ["com.programmer74.sdl1.postgre.repositories"])
open class PostgreJPAConfig