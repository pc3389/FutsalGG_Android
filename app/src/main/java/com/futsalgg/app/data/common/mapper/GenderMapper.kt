package com.futsalgg.app.data.common.mapper

import com.futsalgg.app.remote.api.team.model.response.Gender
import com.futsalgg.app.domain.common.model.Gender as DomainGender

object GenderMapper {
    fun Gender.toDomain(): DomainGender {
        return when (this) {
            Gender.MAN -> DomainGender.MAN
            Gender.WOMAN -> DomainGender.WOMAN
            else -> DomainGender.NONE
        }
    }
}