package com.bahadir.wordle.domain.model

import androidx.annotation.DrawableRes

data class DefinitionUI(
    val partOfSpeech: String,
    val count: Int,
    val definition: String,
    val example: String?,
    @DrawableRes var countryFlag:Int?
)
