package com.bahadir.wordwise.domain.mapper

import android.net.Uri
import com.bahadir.wordwise.data.model.words.Meaning
import com.bahadir.wordwise.data.model.words.WordsItem
import com.bahadir.wordwise.domain.model.DefinitionUI
import com.bahadir.wordwise.domain.model.WordsUI

fun List<WordsItem>.wordsUI() = map {
    WordsUI(
        word = it.word,
        phonetic = it.phonetic,
        definitionUI = it.meanings.definitionUI(),
        audio = Uri.parse(it.phonetics.findLast { audio -> audio.audio.contains(".mp3") }?.audio),
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