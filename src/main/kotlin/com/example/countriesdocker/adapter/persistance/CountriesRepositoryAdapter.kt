package com.example.countriesdocker.adapter.persistance

import com.example.countriesdocker.adapter.persistance.mapper.CountriesMapper
import com.example.countriesdocker.adapter.persistance.model.CountriesEntity
import com.example.countriesdocker.adapter.persistance.repository.SpringDataCountriesRepository
import com.example.countriesdocker.application.port.out.*
import com.example.countriesdocker.config.MessageError
import com.example.countriesdocker.config.exception.BadArgumentException
import com.example.countriesdocker.config.exception.DaoException
import com.example.countriesdocker.config.exception.ResourceNotFoundException
import com.example.countriesdocker.domain.Countries
import com.example.countriesdocker.domain.CountriesSearchFilter
import com.example.countriesdocker.shared.CompanionLogger
import org.springframework.stereotype.Repository

@Repository
class CountriesRepositoryAdapter(
    val repository: SpringDataCountriesRepository
): CountriesRepositoryPort, CountryByIdRepositoryPort, CreateCountryRepositoryPort, CountryByNameRepositoryPort, DeleteRepositoryPort, SearchFilterCountryRepositoryPort {

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

    override fun findCountryByName(name: String)= try {
        name
            .log { info("CountryWebService Request - name = {}", name) }
            .let { repository.findByName(it) }
            .orElseThrow { ResourceNotFoundException(MessageError.RESOURCE_NOT_FOUND.errorCode, "No se encontro el country con name = $name") }
            .toCountriesDomain()
            .log { info("CountryWebService Response: $it") }
    }catch (e: ResourceNotFoundException){
        logger.error("Error al acceder al recurso con name : $name")
        throw ResourceNotFoundException(MessageError.RESOURCE_NOT_FOUND.errorCode, "No se encontro el country con name = $name")
    }

    override fun deleteCountry(countryId: Long) = try {
        countryId
            .log { info("CountryWebService delete Request - id = {}", countryId) }
            .let { repository.deleteById(it) }
            .log {  info("CountryWebService delete Response")}
    }catch (e: ResourceNotFoundException){
        logger.error("Error al acceder al recurso con id : $countryId")
        throw ResourceNotFoundException(MessageError.RESOURCE_NOT_FOUND.errorCode, "No se encontro el country con id = $countryId")
    }catch (e: Exception){
        logger.error("Error al acceder al recurso, el id es null")
        throw BadArgumentException(MessageError.ILLEGAL_ARGUMENT.errorCode, "No se pudo encontrar el country debido a que el id no existe")
    }

    override fun searchCountry(searchFilter: CountriesSearchFilter): List<Countries> {
        val countries: List<CountriesEntity?> =  repository.searchCountriesByFilters(
            searchFilter.name,
            searchFilter.subregion,
            searchFilter.region,
            searchFilter.populationDesde,
            searchFilter.populationHasta,
            searchFilter.areaDesde,
            searchFilter.areaHasta,
            searchFilter.currency,
            searchFilter.language
        )
        return countries.map { it?.toCountriesDomain()!! }
    }

    companion object: CompanionLogger()
}