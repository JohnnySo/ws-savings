package org.soneira.savings.domain.usecase

import org.soneira.savings.domain.model.entity.EconomicPeriod
import org.soneira.savings.domain.model.entity.File
import org.soneira.savings.domain.model.entity.Movement
import java.io.InputStream

interface ImportMovementsUseCase {

    /**
     * Store the file and convert it to a list of [Movement]
     * @return the list of movement in the file
     */
    fun preview(file: InputStream, filename: String): File

    /**
     * Converts the information stored in the file and organize it in periods.
     * There are two possible strategies to organize the information:
     * - PayrollStrategy: The period is determined by the date of the payrolls
     * - MonthStrategy: The period correspond with exact month
     *
     * @param fileId the identifier of the file previously stored with preview
     */
    fun import(fileId: String): List<EconomicPeriod>
}