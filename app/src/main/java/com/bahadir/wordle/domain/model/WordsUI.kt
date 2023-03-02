package com.bahadir.wordle.domain.model

data class WordsUI(
    val word: String,
    val phonetic: String,
    val meanings: List<MeaningUI>,
    val audio: String
)
