package com.bahadir.wordle

import android.net.Uri
import com.bahadir.wordle.data.model.synonyms.SynonymsItem
import com.bahadir.wordle.data.model.words.WordsItem
import com.bahadir.wordle.domain.model.DefinitionUI
import com.bahadir.wordle.domain.model.WordsUI
import com.google.common.annotations.VisibleForTesting

const val SYNONYM_RESPONSE_NAME = "SynonymsResponse.json"
const val WORD_RESPONSE_NAME = "WordResponse.json"

@VisibleForTesting
val synonymsItemList: List<SynonymsItem> = listOf(
    SynonymsItem(score = 9163, word = "house"),
    SynonymsItem(score = 4774, word = "family"),
    SynonymsItem(score = 4347, word = "base"),
    SynonymsItem(score = 4214, word = "place"),
    SynonymsItem(score = 3628, word = "plate")
)

@VisibleForTesting
val wordsUIList: WordsUI =
    WordsUI(
        word = "home",
        phonetic = "/(h)əʊm/",
        meaning = listOf("noun", "verb", "adjective", "adverb", "noun"),
        definitionUI = listOf(
            DefinitionUI(
                definition = "The locality where a thing is usually found, or was first found, or where it is naturally abundant; habitat; seat.",
                example = "the home of the pine",
                partOfSpeech = "noun",
                count = 3,
                countryFlag = null
            ),
            DefinitionUI(
                definition = "(of animals) To return to its owner.",
                example = "The dog homed.",
                partOfSpeech = "verb",
                count = 1,
                countryFlag = null
            ),
            DefinitionUI(
                definition = "(always with \"in on\") To seek or aim for something.",
                example = "The missile was able to home in on the target.",
                partOfSpeech = "verb",
                count = 2,
                countryFlag = null
            ),
            DefinitionUI(
                definition = "Relating to the home team (the team at whose venue a game is played).",
                example = "the home end, home advantage, home supporters",
                partOfSpeech = "adjective",
                count = 4,
                countryFlag = null
            ),
            DefinitionUI(
                definition = "At or in one's place of residence or one's customary or official location; at home",
                example = "Everyone's gone to watch the game; there's nobody home.",
                partOfSpeech = "adverb",
                count = 2,
                countryFlag = null
            ),
        ),
        audio = Uri.parse("https://api.dictionaryapi.dev/media/pronunciations/en/home-us.mp3")
    )

@VisibleForTesting
val wordsItem: WordsItem = WordsItem(
    word = "home",
    phonetic = "/(h)əʊm/",
    meanings = emptyList(),
    phonetics = emptyList(),
    sourceUrls = emptyList(),
    license = com.bahadir.wordle.data.model.words.License(
        name = "CC BY-SA 4.0",
        url = "https://creativecommons.org/licenses/by-sa/4.0"
    )
)

@VisibleForTesting
val lastSearched: MutableList<String> = mutableListOf("home", "house", "family","base")

@VisibleForTesting
const val WORD = "home"
@VisibleForTesting
const val ADD_WORD = "Pencil"

@VisibleForTesting
val wordResponse = listOf(wordsItem)



