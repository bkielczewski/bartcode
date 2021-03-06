package uk.co.bartcode.service

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableAsync
class BartcodeService

fun main(args: Array<String>) {
    SpringApplication.run(BartcodeService::class.java, *args)
}