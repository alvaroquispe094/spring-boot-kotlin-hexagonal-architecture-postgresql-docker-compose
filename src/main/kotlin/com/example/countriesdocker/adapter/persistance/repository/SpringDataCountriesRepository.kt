package com.example.countriesdocker.adapter.persistance.repository

import com.example.countriesdocker.adapter.persistance.model.CountriesEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface SpringDataCountriesRepository: CrudRepository<CountriesEntity, Long>{
    fun findByName(name: String): Optional<CountriesEntity>

    @Query("SELECT p FROM CountriesEntity p"
                + " WHERE (p.name = :name OR :name IS NULL OR :name = '' )"
                + " AND (p.subregion = :subregion OR :subregion IS NULL OR :subregion = '')"
                + " AND (p.region = :region OR :region IS NULL OR :region = '')"
                + " AND (p.population >= :populationDesde OR :populationDesde IS NULL OR :populationDesde <> 0)"
                + " AND (p.population <= :populationHasta OR :populationHasta IS NULL OR :populationHasta <> 0)"
                + " AND (p.area >= :areaDesde OR :areaDesde IS NULL OR :areaDesde = 0)"
                + " AND (p.area <= :areaHasta OR :areaHasta IS NULL OR :areaHasta = 0)"
                + " AND (p.currency = :currency OR :currency IS NULL OR :currency = '')"
                + " AND (p.language = :language OR :language IS NULL OR :language = '')"
    )
    fun searchCountriesByFilters(
        @Param("name") name: String?, @Param("subregion") subregion: String?,
        @Param("region") region: String?, @Param("populationDesde") populationDesde: Int?,
        @Param("populationHasta") populationHasta: Int?, @Param("areaDesde") areaDesde: Number?,
        @Param("areaHasta") areaHasta: Number?, @Param("currency") currency: String?,
        @Param("language") language: String?
    ): List<CountriesEntity?>
}