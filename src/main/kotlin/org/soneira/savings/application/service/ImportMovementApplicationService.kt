package org.soneira.savings.application.service

import org.soneira.savings.domain.entity.*
import org.soneira.savings.domain.port.input.ImportMovementApplicationService
import org.soneira.savings.domain.service.PeriodCreator
import org.springframework.stereotype.Service

@Service
class ImportMovementApplicationService (
    val periodCreator: PeriodCreator): ImportMovementApplicationService {
    override fun execute(movements: List<Movement>, filename: String): List<EconomicPeriod> {
        //get User
        //get last period
        //val periodCreatorParams = PeriodCreatorParams(null, filename, movements, null)
        //periodCreator.create(periodCreatorParams)
        TODO("Not yet implemented")
    }
}