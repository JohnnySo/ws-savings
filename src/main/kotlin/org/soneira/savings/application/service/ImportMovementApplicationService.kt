package org.soneira.savings.application.service

import org.soneira.savings.domain.entity.*
import org.soneira.savings.domain.port.input.ImportMovementApplicationService
import org.soneira.savings.domain.service.SavingCreator
import org.soneira.savings.domain.vo.params.SavingsCreatorParams
import org.springframework.stereotype.Service

@Service
class ImportMovementApplicationService (
    val savingCreator: SavingCreator): ImportMovementApplicationService {
    override fun execute(movements: List<Movement>, filename: String): List<Saving> {
        //get User
        //get last saving
        //val savingsCreatorParams = SavingsCreatorParams(null, filename, movements, null)
        //savingCreator.create(savingsCreatorParams)
        TODO("Not yet implemented")
    }
}