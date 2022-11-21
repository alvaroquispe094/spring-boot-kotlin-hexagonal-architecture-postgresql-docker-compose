package com.example.countriesdocker.adapter.controller

import com.example.countriesdocker.adapter.controller.model.CountriesRest
import com.example.countriesdocker.application.port.`in`.CreateCountryInPort
import com.example.countriesdocker.application.port.`in`.FindCountryByNameInPort
import com.example.countriesdocker.application.port.`in`.SearchFilterCountryInPort
import com.example.countriesdocker.config.MessageError
import com.example.countriesdocker.config.exception.ResourceNotFoundException
import com.example.countriesdocker.domain.Countries
import com.example.countriesdocker.domain.CountriesSearchFilter
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.anyArray
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@DisplayName("CountriesController Adapter Test")
//@Import(AppTestConfig::class)
@AutoConfigureMockMvc
@SpringBootTest
class CountriesControllerTest {

    @Autowired
    private val mockMvc: MockMvc? = null

    @Autowired
    private val objectMapper: ObjectMapper? = null

    @MockBean
    private val findCountryByNameInPort: FindCountryByNameInPort? = null

    @MockBean
    private val createCountryInPort: CreateCountryInPort? = null

    @MockBean
    private val searchFilterCountryInPort: SearchFilterCountryInPort? = null

    @Test
    @DisplayName("when you get a country by Id, the adapter must return a country")
    fun getCountryByName() {

        // given
        val countryName = "Australia"
        val URL: String = "/api/v1/countries/name/${countryName}"

        // when
        `when`(findCountryByNameInPort!!.execute(anyString()))
            .thenReturn(COUNTRIES_DOMAIN)

        // expect
        mockMvc!!.perform(
            MockMvcRequestBuilders.get(URL)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper!!.writeValueAsString(COUNTRIES_REST)))
    }

    @Test
    @DisplayName("when you get a country by Id, the adapter must return a NotFound exception")
    fun getCountryByNameNotFound() {

        // given
        val countryName = "Australia"
        val URL: String = "/api/v1/countries/name/${countryName}"

        // when
        `when`(findCountryByNameInPort!!.execute(anyString()))
            .thenThrow(
                ResourceNotFoundException(
                    MessageError.RESOURCE_NOT_FOUND.errorCode,
                    MessageError.RESOURCE_NOT_FOUND.defaultMessage
                )
            )

        // expect
        mockMvc!!.perform(
            MockMvcRequestBuilders.get(URL)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(MessageError.RESOURCE_NOT_FOUND.defaultMessage)))
    }

    @Test
    @DisplayName("when create is called, the adapter must return the created CountriesRest")
    fun createCountryOk() {

        // given
        val request = objectMapper!!.writeValueAsString(COUNTRIES_REST)
        val URL: String = "/api/v1/countries"

        // when
        `when`(createCountryInPort!!.execute(any()))
            .thenReturn(COUNTRIES_DOMAIN)

        // expected
        mockMvc!!.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(COUNTRIES_REST)))
    }

    @Test
    @DisplayName("when you get a countries by By filtersd, the adapter must return a list of countries")
    fun getCountriesByFilters() {

        // given
        val request = objectMapper!!.writeValueAsString(REQUEST_FILTERS_DOMAIN)
        val URL: String = "/api/v1/countries/search"


        // when
        `when`(searchFilterCountryInPort!!.execute(REQUEST_FILTERS_DOMAIN))
            .thenReturn(LIST_COUNTRIES_DOMAIN)

        // expect
        mockMvc!!.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper!!.writeValueAsString(LIST_COUNTRIES_REST)))
    }

    companion object {
        private val COUNTRIES_DOMAIN = Countries(
            name = "Australia",
            capital = "Canberra",
            subregion = "Australia and New Zealand",
            region = "Oceania2",
            population = 25687041,
            area = 7692024.0,
            currency = "Australian dollar",
            language = "English",
            flag = "https://flagcdn.com/au.svg"
        )

        private val COUNTRIES_REST = CountriesRest(
            name = "Australia",
            capital = "Canberra",
            subregion = "Australia and New Zealand",
            region = "Oceania2",
            population = 25687041,
            area = 7692024.0,
            currency = "Australian dollar",
            language = "English",
            flag = "https://flagcdn.com/au.svg"
        )

        private val REQUEST_FILTERS_DOMAIN = CountriesSearchFilter(
            name = null,
            subregion = null,
            region = null,
            populationDesde = null,
            populationHasta = null,
            areaDesde = null,
            areaHasta = null,
            currency = null,
            language = "English"
        )
        private val LIST_COUNTRIES_DOMAIN = listOf(
            Countries(name = "Australia", capital = "Canberra", subregion = "Australia and New Zealand", region = "Oceania2", population = 25687041, area = 7692024.0, currency = "Australian dollar", language = "English", flag = "https://flagcdn.com/au.svg"),
            Countries(name = "American Samoa", capital = "Pago Pago", subregion = "Polynesia", region = "Oceania", population = 55197, area = 199.0, currency = "United States Dollar", language = "English", flag = "https://flagcdn.com/as.svg")
        )

        private val LIST_COUNTRIES_REST = listOf(
            CountriesRest(name = "Australia", capital = "Canberra", subregion = "Australia and New Zealand", region = "Oceania2", population = 25687041, area = 7692024.0, currency = "Australian dollar", language = "English", flag = "https://flagcdn.com/au.svg"),
            CountriesRest(name = "American Samoa", capital = "Pago Pago", subregion = "Polynesia", region = "Oceania", population = 55197, area = 199.0, currency = "United States Dollar", language = "English", flag = "https://flagcdn.com/as.svg")
        )

    }
}