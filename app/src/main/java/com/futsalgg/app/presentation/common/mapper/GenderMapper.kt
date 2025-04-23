package com.futsalgg.app.presentation.common.mapper

import com.futsalgg.app.domain.common.model.Gender as DomainGender
import com.futsalgg.app.presentation.common.model.Gender as PresentationGender

object GenderMapper {
    fun fromDomain(gender: DomainGender): PresentationGender {
        return when (gender) {
            DomainGender.MAN -> PresentationGender.MAN
            DomainGender.WOMAN -> PresentationGender.WOMAN
            else -> PresentationGender.NONE
        }
    }

    fun PresentationGender.toDomain(): DomainGender {
        return when (this) {
            PresentationGender.MAN -> DomainGender.MAN
            PresentationGender.WOMAN -> DomainGender.WOMAN
            else -> DomainGender.NONE
        }
    }
}