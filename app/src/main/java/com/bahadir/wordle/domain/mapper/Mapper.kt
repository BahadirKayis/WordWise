package com.bahadir.wordle.domain.mapper

import androidx.core.net.toUri
import com.bahadir.wordle.data.model.words.Meaning
import com.bahadir.wordle.data.model.words.WordsItem
import com.bahadir.wordle.domain.model.DefinitionUI
import com.bahadir.wordle.domain.model.WordsUI

fun List<WordsItem>.wordsUI() = map {
    WordsUI(
        word = it.word,
        phonetic = it.phonetic,
        definitionUI = it.meanings.definitionUI(),
        audio = it.phonetics.last().audio.toUri(),
        meaning = it.meanings.map { meaning -> meaning.partOfSpeech }
    )
}

private fun List<Meaning>.definitionUI() = map {
    it.definitions.mapIndexed { index, definition ->
        DefinitionUI(
            definition = definition.definition,
            example = definition.example,
            partOfSpeech = it.partOfSpeech,
            count = index + 1,
            countryFlag = null
        )
    }
}.flatten()