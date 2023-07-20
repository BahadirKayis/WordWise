package com.bahadir.wordwise.domain.model

import android.net.Uri

data class WordsUI(
    val word: String,
    val phonetic: String,
    val meaning: List<String>,
    var definitionUI: List<DefinitionUI>,
    val audio: Uri? = null
)
