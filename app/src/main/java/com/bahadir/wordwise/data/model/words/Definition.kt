package com.bahadir.wordwise.data.model.words

data class Definition(
    val antonyms: List<String>,
    val definition: String,
    val example: String,
    val synonyms: List<Any>
)