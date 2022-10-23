package com.example.countriesdocker.adapter.persistance

import com.example.countriesdocker.adapter.persistance.mapper.CountriesMapper
import com.example.countriesdocker.adapter.persistance.repository.SpringDataCountriesRepository
import com.example.countriesdocker.application.port.out.CountriesRepositoryPort
import com.example.countriesdocker.application.port.out.CountryByIdRepositoryPort
import com.example.countriesdocker.application.port.out.CreateCountryRepositoryPort
import com.example.countriesdocker.config.MessageError
import com.example.countriesdocker.config.exception.DaoException
import com.example.countriesdocker.config.exception.ResourceNotFoundException
import com.example.countriesdocker.domain.Countries
import com.example.countriesdocker.shared.CompanionLogger
import org.springframework.stereotype.Repository

@Repository
class CountriesRepositoryAdapter(
    private val repository: SpringDataCountriesRepository
): CountriesRepositoryPort, CountryByIdRepositoryPort, CreateCountryRepositoryPort {

    override fun findAllCountries(): List<Countries> {
        return repository.findAll().map { it.toCountriesDomain() }
    }

    override fun findCountryById(countryId: Long) = try {
        countryId
            .log { info("CountryWebService Request - id = {}", countryId) }
            .let { repository.findById(it) }
            .orElseThrow { ResourceNotFoundException(MessageError.RESOURCE_NOT_FOUND.errorCode, "No se encontro el country con id $countryId") }
            .toCountriesDomain()
            .log { info("CountryWebService Response: $it") }
    }catch (e: ResourceNotFoundException){
        logger.error("Error al acceder al recurso con id : $countryId")
        throw ResourceNotFoundException(MessageError.RESOURCE_NOT_FOUND.errorCode, "No se encontro el country con id = $countryId")
    }catch (e: IllegalArgumentException){
        logger.error("Error al acceder al recurso, el id es null")
        throw DaoException(MessageError.BAD_REQUEST.errorCode, "No se pudo encontrar el country debido a que el id es null")
    }

    override fun createCountry(countries: Countries) = try {
        countries
            .log { info("CountryWebService Request: {}", countries) }
            .let { CountriesMapper.toCountriesEntity(it) }
            .let { repository.save(it) }
            .toCountriesDomain()
            .log {  info("CountryWebService Response: $it")}
    } catch (e: IllegalArgumentException){
        logger.error("Error al grabar al recurso, la entidad es null")
        throw DaoException(MessageError.BAD_REQUEST.errorCode, "No se pudo grabar el country debido a que la entidad dominio es null")
    }

    companion object: CompanionLogger()
}