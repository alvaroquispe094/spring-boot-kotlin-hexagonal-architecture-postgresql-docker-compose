package com.example.countriesdocker.adapter.controller

import com.example.countriesdocker.adapter.controller.model.CountriesRest
import com.example.countriesdocker.application.port.`in`.CreateCountryInPort
import com.example.countriesdocker.application.port.`in`.FindCountryByNameInPort
import com.example.countriesdocker.config.AppTestConfig
import com.example.countriesdocker.config.MessageError
import com.example.countriesdocker.config.exception.ResourceNotFoundException
import com.example.countriesdocker.domain.Countries
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@DisplayName("CountriesController Adapter Test")
@Import(AppTestConfig::class)
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

    @Test
    @DisplayName("when you get a country by Id, the adapter must return a country")
    fun getCountryByName() {

        // given
        val countryName = "Australia"

        // when
        `when`(findCountryByNameInPort!!.execute(anyString()))
            .thenReturn(COUNTRIES_DOMAIN)

       /* val result = mockMvc!!.get("/api/v1/countries/name/{name}", countryName) {
            //header("Authorization", "Bearer ${anyString()}")
            contentType = MediaType.APPLICATION_JSON
        }.andReturn()*/

        // expect
        mockMvc!!.get("/api/v1/countries/name/{name}", countryName) {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            MockMvcResultMatchers.content().json(objectMapper!!.writeValueAsString(COUNTRIES_REST))
        }

    }

    @Test
    @DisplayName("when you get a country by Id, the adapter must return a NotFound exception")
    fun getCountryByNameNotFound() {

        // given
        val countryName = "Australia"

        // when
        `when`(findCountryByNameInPort!!.execute(anyString()))
            .thenThrow(
                ResourceNotFoundException(
                    MessageError.RESOURCE_NOT_FOUND.errorCode,
                    MessageError.RESOURCE_NOT_FOUND.defaultMessage
                )
            )

        // expect
        mockMvc!!.get("/api/v1/countries/name/{name}", countryName) {
            header("Authorization", "Bearer ${anyString()}")
        }.andDo {
            print()
        }.andExpect {
            MockMvcResultMatchers.status().isNotFound
            content { string(Matchers.containsString(MessageError.RESOURCE_NOT_FOUND.defaultMessage)) }
        }

    }

    @Test
    @DisplayName("when create is called, the adapter must return the created CountriesRest")
    fun createCountryOk() {

        // given
        val request = objectMapper!!.writeValueAsString(COUNTRIES_REST)

        // when
        `when`(createCountryInPort!!.execute(any())).thenReturn(COUNTRIES_DOMAIN)

        // expected
        mockMvc!!.post("/api/v1/countries") {
            contentType = MediaType.APPLICATION_JSON
            content = request
        }.andExpect {
            status { isCreated() }
            MockMvcResultMatchers.content().json(objectMapper!!.writeValueAsString(COUNTRIES_REST))
        }
    }

    companion object {
        private val COUNTRIES_DOMAIN = Countries(
            name = "Australia",
            capital = "Canberra",
            subregion = "Australia and New Zealand",
            region = "Oceania2",
            population = 25687041,
            area = 76920240F,
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
            area = 76920240F,
            currency = "Australian dollar",
            language = "English",
            flag = "https://flagcdn.com/au.svg"
        )
    }
}