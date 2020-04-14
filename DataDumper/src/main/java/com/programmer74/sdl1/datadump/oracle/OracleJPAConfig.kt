package com.programmer74.sdl1.datadump.oracle

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@Profile("oracle")
@EntityScan(basePackages = ["com.programmer74.sdl1.oracle.entities"])
@EnableJpaRepositories(basePackages = ["com.programmer74.sdl1.oracle.repositories"])
open class OracleJPAConfig