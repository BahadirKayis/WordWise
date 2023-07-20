package com.bahadir.wordwise

import android.net.Uri
import com.bahadir.wordwise.data.model.synonyms.SynonymsItem
import com.bahadir.wordwise.data.model.words.Definition
import com.bahadir.wordwise.data.model.words.License
import com.bahadir.wordwise.data.model.words.Meaning
import com.bahadir.wordwise.data.model.words.Phonetic
import com.bahadir.wordwise.data.model.words.WordsItem
import com.bahadir.wordwise.domain.model.DefinitionUI
import com.bahadir.wordwise.domain.model.WordsUI
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
val wordsItem = WordsItem(
    license = License(
        name = "CC BY-SA 3.0",
        url = "https://creativecommons.org/licenses/by-sa/3.0"
    ),
    meanings = listOf(
        Meaning(
            antonyms = listOf(),
            definitions = listOf(
                Definition(
                    antonyms = listOf(),
                    definition = "A dwelling.",
                    example = "",
                    synonyms = listOf()
                ),
                Definition(
                    antonyms = listOf(),
                    definition = "One’s native land; the place or country in which one dwells; the place where one’s ancestors dwell or dwelt.",
                    example = "",
                    synonyms = listOf()
                ),
                Definition(
                    antonyms = listOf(),
                    definition = "The locality where a thing is usually found, or was first found, or where it is naturally abundant; habitat; seat.",
                    example = "the home of the pine",
                    synonyms = listOf()
                ),
                Definition(
                    antonyms = listOf(),
                    definition = "A focus point.",
                    example = "",
                    synonyms = listOf()
                )
            ),
            partOfSpeech = "noun",
            synonyms = listOf(
                "home base",
                "abode",
                "domicile",
                "dwelling",
                "house",
                "residence",
                "tenement"
            )
        ),
        Meaning(
            antonyms = listOf(),
            definitions = listOf(
                Definition(
                    antonyms = listOf(),
                    definition = "(of animals) To return to its owner.",
                    example = "The dog homed.",
                    synonyms = listOf()
                ),
                Definition(
                    antonyms = listOf(),
                    definition = "(always with \"in on\") To seek or aim for something.",
                    example = "The missile was able to home in on the target.",
                    synonyms = listOf()
                )
            ),
            partOfSpeech = "verb",
            synonyms = listOf()
        ),
        Meaning(
            antonyms = listOf("away", "road", "visitor"),
            definitions = listOf(
                Definition(
                    antonyms = listOf(),
                    definition = "Of or pertaining to one’s dwelling or country; domestic; not foreign; as home manufactures; home comforts.",
                    example = "",
                    synonyms = listOf()
                ),
                Definition(
                    antonyms = listOf(),
                    definition = "(except in phrases) That strikes home; direct, pointed.",
                    example = "",
                    synonyms = listOf()
                ),
                Definition(
                    antonyms = listOf(),
                    definition = "Personal, intimate.",
                    example = "",
                    synonyms = listOf()
                ),
                Definition(
                    antonyms = listOf("away", "road", "visitor"),
                    definition = "Relating to the home team (the team at whose venue a game is played).",
                    example = "the home end, home advantage, home supporters",
                    synonyms = listOf()
                )
            ),
            partOfSpeech = "adjective",
            synonyms = listOf()
        ),
        Meaning(
            antonyms = listOf(),
            definitions = listOf(
                Definition(
                    antonyms = listOf(),
                    definition = "To one's home",
                    example = "",
                    synonyms = listOf()
                ),
                Definition(
                    antonyms = listOf(),
                    definition = "At or in one's place of residence or one's customary or official location; at home",
                    example = "Everyone's gone to watch the game; there's nobody home.",
                    synonyms = listOf()
                ),
                Definition(
                    antonyms = listOf(),
                    definition = "To a full and intimate degree; to the heart of the matter; fully, directly.",
                    example = "",
                    synonyms = listOf()
                ),
                Definition(
                    antonyms = listOf(),
                    definition = "Into the goal",
                    example = "",
                    synonyms = listOf()
                ),
                Definition(
                    antonyms = listOf(),
                    definition = "Into the right, proper or stowed position",
                    example = "sails sheeted home",
                    synonyms = listOf()
                )
            ),
            partOfSpeech = "adverb",
            synonyms = listOf("homeward")
        ),
        Meaning(
            antonyms = listOf(),
            definitions = listOf(
                Definition(
                    antonyms = listOf(),
                    definition = "A directory that contains a user's files.",
                    example = "",
                    synonyms = listOf()
                )
            ),
            partOfSpeech = "noun",
            synonyms = listOf()
        )
    ),
    phonetic = "/(h)əʊm/",
    phonetics = listOf(
        Phonetic(
            audio = "",
            license = License(
                name = "BY-SA 3.0",
                url = "https://creativecommons.org/licenses/by-sa/3.0"
            ),
            sourceUrl = "",
            text = "/(h)əʊm/"
        ),
        Phonetic(
            audio = "https://api.dictionaryapi.dev/media/pronunciations/en/home-us.mp3",
            license = License(
                name = "BY-SA 3.0",
                url = "https://creativecommons.org/licenses/by-sa/3.0"
            ),
            sourceUrl = "https://commons.wikimedia.org/w/index.php?curid=711130",
            text = "/hoʊm/"
        )
    ),
    sourceUrls = listOf(
        "https://en.wiktionary.org/wiki/home",
        "https://en.wiktionary.org/wiki/home%20directory"
    ),
    word = "home"
)



@VisibleForTesting
val lastSearchedList: MutableList<String> =
    mutableListOf("Home", "House", "Family", "Base", "School")

@VisibleForTesting
val lastSearchedString = "Home,House,Family,Base,School"

@VisibleForTesting
const val WORD = "home"

@VisibleForTesting
const val ADD_WORD = "Pencil"

@VisibleForTesting
val wordResponse = listOf(wordsItem)



