package com.example.countriesdocker.adapter.controller

import com.example.countriesdocker.adapter.controller.model.CountriesRest
import com.example.countriesdocker.application.port.`in`.CreateCountryInPort
import com.example.countriesdocker.application.port.`in`.FindAllCountriesInPort
import com.example.countriesdocker.application.port.`in`.FindCountryByIdInPort
import com.example.countriesdocker.application.port.`in`.FindCountryByNameInPort
import com.example.countriesdocker.shared.CompanionLogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class CountriesController(
    private val findAllCountriesInPort: FindAllCountriesInPort,
    private val findCountryByIdInPort: FindCountryByIdInPort,
    private val createCountryInPort: CreateCountryInPort,
    private val findCountryByNameInPort: FindCountryByNameInPort,
) {

    @GetMapping("/countries")
    fun findAll(): ResponseEntity< List<CountriesRest> > = run {
        logger.info("Llamada al controller api/v1/countries")
        findAllCountriesInPort.execute()
            .map { CountriesRest.from(it) }
            .let { ResponseEntity.status(HttpStatus.OK).body(it) }
            .log { info("Fin a llamada al controller /api/v1/countries/{}", it) }
    }

    @GetMapping("/countries/id/{id}")
    fun findById(@PathVariable("id") id: Long): ResponseEntity<CountriesRest> = id
        .log { info("Llamada al controller /api/v1/countries/id/{}", it) }
        .let { findCountryByIdInPort.execute(it) }
        .let { p -> CountriesRest.from(p) }
        .let { ResponseEntity.status(HttpStatus.OK).body(it) }
        .log { info("Fin a llamada al controller /api/v1/countries/id/{}", it) }

    @PostMapping("/countries")
    fun create(@RequestBody @Validated req: CountriesRest): ResponseEntity<CountriesRest> = req
        .log { info("Llamada a controller de creacion de countries") }
        .let { req.toDomain() }
        .let { createCountryInPort.execute(it) }
        .let { p -> CountriesRest.from(p) }
        .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }
        .log { info("Fin a llamada a controller de creacion de countries {}", it) }

    @GetMapping("/countries/name/{name}")
    fun findByName(@PathVariable("name") name: String): ResponseEntity<CountriesRest> = name
        .log { info("Llamada al controller /api/v1/countries/name/{}", it) }
        .let { findCountryByNameInPort.execute(it) }
        .let { p -> CountriesRest.from(p) }
        .let { ResponseEntity.status(HttpStatus.OK).body(it) }
        .log { info("Fin a llamada al controller /api/v1/countries/name/{}", it) }

    companion object: CompanionLogger()

}