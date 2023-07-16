package com.bahadir.wordle.domain

import com.bahadir.wordle.domain.mapper.wordsUI
import com.bahadir.wordle.domain.model.WordsUI
import com.bahadir.wordle.wordsItem
import com.bahadir.wordle.wordsUIList
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

class MapperTest {

    private lateinit var wordsTestUI: WordsUI
     //2 şekilde yapılabilir
    //  private val wordMapper: WordMapperEntity = WordMapperEntity()


    @Before
    fun setUp() {
        wordsTestUI = listOf(wordsItem).wordsUI()[0] //wordMapper.map(listOf(wordsItem))[0]
        println(wordsTestUI)

    }

    @Test
    fun word_whenWordsUIMappedWithWord_isSame() {
        Truth.assertThat(wordsTestUI.word).isEqualTo(wordsUIList.word)
    }

    @Test
    fun word_whenWordsUIMappedWithWord_isNotSame() {
        Truth.assertThat(wordsTestUI.word).isNotEqualTo(wordsUIList.phonetic)
    }

    @Test
    fun phonetic_whenWordsUIMappedWithPhonetic_isSame() {
        Truth.assertThat(wordsTestUI.phonetic).isEqualTo(wordsUIList.phonetic)
    }

    @Test
    fun mp3Url_whenWordsUIMappedWithMp3Url_isSame() {
        Truth.assertThat(wordsTestUI.audio).isEqualTo(wordsUIList.audio)
    }
}