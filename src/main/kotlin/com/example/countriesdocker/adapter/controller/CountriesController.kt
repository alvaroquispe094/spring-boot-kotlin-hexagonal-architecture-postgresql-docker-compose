package com.example.countriesdocker.adapter.controller

import com.example.countriesdocker.adapter.controller.model.CountriesRest
import com.example.countriesdocker.application.port.`in`.CreateCountryInPort
import com.example.countriesdocker.application.port.`in`.FindAllCountriesInPort
import com.example.countriesdocker.application.port.`in`.FindCountryByIdInPort
import com.example.countriesdocker.shared.CompanionLogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class CountriesController(
    private val findAllCountriesInPort: FindAllCountriesInPort,
    private val findAllCountryByIdInPort: FindCountryByIdInPort,
    private val createCountryInPort: CreateCountryInPort,
) {

    @GetMapping("/countries")
    fun findAll(): ResponseEntity< List<CountriesRest> > = run {
        logger.info("Llamada al controller api/v1/countries")
        findAllCountriesInPort.execute()
            .map { CountriesRest.from(it) }
            .let { ResponseEntity.status(HttpStatus.OK).body(it) }
            .log { info("Fin a llamada al controller /api/v1/countries/{}", it) }
    }

    @GetMapping("/countries/{id}")
    fun find(@PathVariable("id") id: Long): ResponseEntity<CountriesRest> = id
        .log { info("Llamada al controller /api/v1/countries/{}", it) }
        .let { findAllCountryByIdInPort.execute(it) }
        .let { p -> CountriesRest.from(p) }
        .let { ResponseEntity.status(HttpStatus.OK).body(it) }
        .log { info("Fin a llamada al controller /api/v1/countries/{}", it) }

    @PostMapping("/countries")
    fun create(@RequestBody @Validated req: CountriesRest): ResponseEntity<CountriesRest> = req
        .log { info("Llamada a controller de creacion de countries") }
        .let { req.toDomain() }
        .let { createCountryInPort.execute(it) }
        .let { p -> CountriesRest.from(p) }
        .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }
        .log { info("Fin a llamada a controller de creacion de countries {}", it) }

    companion object: CompanionLogger()

}