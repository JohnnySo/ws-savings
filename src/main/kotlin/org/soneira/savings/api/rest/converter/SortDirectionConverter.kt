package org.soneira.savings.api.rest.converter

import org.soneira.savings.domain.vo.SortDirection
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class SortDirectionConverter : Converter<String, SortDirection> {
    override fun convert(source: String): SortDirection? {
        return SortDirection.fromString(source)
    }

}