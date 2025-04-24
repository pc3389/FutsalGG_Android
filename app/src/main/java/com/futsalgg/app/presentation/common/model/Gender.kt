package com.futsalgg.app.presentation.common.model

import com.futsalgg.app.domain.common.model.Gender as DomainGender

enum class Gender(val displayName: String) {
    MAN("남자"),
    WOMAN("여자"),
    NONE("없음");
}

fun DomainGender.fromDomain(): Gender {
    return when (this) {
        DomainGender.MAN -> Gender.MAN
        DomainGender.WOMAN -> Gender.WOMAN
        else -> Gender.NONE
    }
}

fun Gender.toDomain(): DomainGender {
    return when (this) {
        Gender.MAN -> DomainGender.MAN
        Gender.WOMAN -> DomainGender.WOMAN
        else -> DomainGender.NONE
    }
}