package com.programmer74.sdl1.datadump.mysql

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@Profile("mysql")
@EntityScan(basePackages = ["com.programmer74.sdl1.mysql.entities"])
@EnableJpaRepositories(basePackages = ["com.programmer74.sdl1.mysql.repositories"])
open class MysqlJPAConfig