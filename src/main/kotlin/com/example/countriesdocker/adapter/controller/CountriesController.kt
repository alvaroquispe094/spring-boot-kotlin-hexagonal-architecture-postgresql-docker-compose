package com.example.countriesdocker.adapter.controller

import com.example.countriesdocker.adapter.controller.model.CountriesSearchFilterRest
import com.example.countriesdocker.adapter.controller.model.CountriesRest
import com.example.countriesdocker.application.port.`in`.*
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
    private val deleteCountryInPort: DeleteCountryInPort,
    private val searchFilterCountryInPort: SearchFilterCountryInPort,
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

    @DeleteMapping("/countries/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<HttpStatus> = id
        .log { info("Llamada delete al controller /api/v1/countries/id/{}", it) }
        .let { deleteCountryInPort.execute(it) }
        //.let { p -> CountriesRest.from(p) }
        .let { ResponseEntity<HttpStatus>( HttpStatus.OK) }
        .log { info("Fin a llamada al controller /api/v1/countries/id/{}", it) }

    @PostMapping("/countries/search")
    fun search(@RequestBody req: CountriesSearchFilterRest): ResponseEntity< List<CountriesRest> > = req
        .log { info("Llamada a controller searching de countries by filters") }
        .let { req.toDomain() }
        .let { searchFilterCountryInPort.execute(it) }
        .map { CountriesRest.from(it) }
        .let { ResponseEntity.status(HttpStatus.OK).body(it) }
        .log { info("Fin a llamada al controller /api/v1/countries/search/{}", it) }

    companion object: CompanionLogger()

}