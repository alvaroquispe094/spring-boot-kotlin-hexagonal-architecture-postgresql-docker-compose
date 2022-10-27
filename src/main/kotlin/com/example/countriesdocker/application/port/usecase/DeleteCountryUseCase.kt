package com.example.countriesdocker.application.port.usecase

import com.example.countriesdocker.application.port.`in`.DeleteCountryInPort
import com.example.countriesdocker.application.port.out.DeleteRepositoryPort
import com.example.countriesdocker.shared.CompanionLogger
import org.springframework.stereotype.Component

@Component
class DeleteCountryUseCase(
    private val deleteRepositoryPort: DeleteRepositoryPort,
): DeleteCountryInPort {

    override fun execute(idCountry: Long) =
        idCountry
            .log {  info("Inicio de proceso de busqueda de country con id = {}", it) }
            .let { deleteRepositoryPort.deleteCountry(it) }
            .log { info("Fin de proceso de busqueda de country - response : $it") }

    companion object: CompanionLogger()
}