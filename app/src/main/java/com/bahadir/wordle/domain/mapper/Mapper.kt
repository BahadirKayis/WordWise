package com.bahadir.wordle.domain.mapper

import com.bahadir.wordle.data.model.words.Meaning
import com.bahadir.wordle.data.model.words.WordsItem
import com.bahadir.wordle.domain.model.DefinitionUI
import com.bahadir.wordle.domain.model.MeaningUI
import com.bahadir.wordle.domain.model.WordsUI

fun List<WordsItem>.wordsUI() = map {
    WordsUI(
        word = it.word,
        phonetic = it.phonetic,
        meanings = it.meanings.meaningUI(),
        audio = it.phonetics.last().audio
    )

}

private fun List<Meaning>.meaningUI() = map {
    MeaningUI(
        meaning = it.partOfSpeech,
        definition = it.definitions.map { definition ->
            DefinitionUI(
                definition = definition.definition,
                example = definition.example
            )
        },
    )
}