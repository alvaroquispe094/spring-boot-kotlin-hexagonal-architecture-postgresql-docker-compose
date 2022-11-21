package com.example.countriesdocker.application.usecase

import com.example.countriesdocker.adapter.controller.model.CountriesRest
import com.example.countriesdocker.application.port.out.CreateCountryRepositoryPort
import com.example.countriesdocker.application.port.out.SearchFilterCountryRepositoryPort
import com.example.countriesdocker.application.port.usecase.CreateCountryUseCase
import com.example.countriesdocker.application.port.usecase.SearchFilterCountryUseCase
import com.example.countriesdocker.domain.Countries
import com.example.countriesdocker.domain.CountriesSearchFilter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@DisplayName("Get PokemonAbility UseCase Test")
@ExtendWith(MockitoExtension::class)
class SearchFilterCountryUseCaseTest {
    private lateinit var searchFilterCountryRepository: SearchFilterCountryRepositoryPort
    private lateinit var searchFilterCountryUseCase: SearchFilterCountryUseCase

    @BeforeEach
    @Throws(Exception::class)
    fun createUseCase() {
        searchFilterCountryRepository = mock(SearchFilterCountryRepositoryPort::class.java)
        searchFilterCountryUseCase =
            SearchFilterCountryUseCase(searchFilterCountryRepository)
    }

    @Test
    @DisplayName("When GetCountryByNameUseCase is executed Should Return a Country")
    fun searchFilterCountryByLanguageUseCaseTest() {

        //given
        val request = REQUEST_FILTERS_DOMAIN
        val expected = LIST_COUNTRIES_EXPECTED
        `when`(searchFilterCountryRepository.searchCountry(request)).thenReturn(LIST_COUNTRIES_DOMAIN)

        //when
        val response = searchFilterCountryUseCase.execute(request)

        //then
        assertEquals(expected, response)
        assertEquals(expected.map { it.language }, response.map { it.language }) // El lenguaje en la respuesta debe ser igual al lenguaje
    }

    companion object {
        private val REQUEST_FILTERS_DOMAIN = CountriesSearchFilter(
            name = null,
            subregion = null,
            region = null,
            populationDesde = null,
            populationHasta = null,
            areaDesde = null,
            areaHasta = null,
            currency = null,
            language = "Englishs"
        )
        private val LIST_COUNTRIES_DOMAIN = listOf(
            Countries(name = "Australia", capital = "Canberra", subregion = "Australia and New Zealand", region = "Oceania2", population = 25687041, area = 7692024.0, currency = "Australian dollar", language = "English", flag = "https://flagcdn.com/au.svg"),
            Countries(name = "American Samoa", capital = "Pago Pago", subregion = "Polynesia", region = "Oceania", population = 55197, area = 199.0, currency = "United States Dollar", language = "English", flag = "https://flagcdn.com/as.svg")
        )

        private val LIST_COUNTRIES_EXPECTED = listOf(
            Countries(name = "Australia", capital = "Canberra", subregion = "Australia and New Zealand", region = "Oceania2", population = 25687041, area = 7692024.0, currency = "Australian dollar", language = "English", flag = "https://flagcdn.com/au.svg"),
            Countries(name = "American Samoa", capital = "Pago Pago", subregion = "Polynesia", region = "Oceania", population = 55197, area = 199.0, currency = "United States Dollar", language = "English", flag = "https://flagcdn.com/as.svg")
        )
    }

}
