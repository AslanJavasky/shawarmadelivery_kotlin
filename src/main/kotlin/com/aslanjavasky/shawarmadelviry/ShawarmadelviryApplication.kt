package com.aslanjavasky.shawarmadelviry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
class ShawarmadelviryApplication

fun main(args: Array<String>) {
	runApplication<ShawarmadelviryApplication>(*args)
}
