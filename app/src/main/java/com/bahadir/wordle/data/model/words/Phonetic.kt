package com.bahadir.wordle.data.model.words

data class Phonetic(
    val audio: String,
    val license: License,
    val sourceUrl: String,
    val text: String
)