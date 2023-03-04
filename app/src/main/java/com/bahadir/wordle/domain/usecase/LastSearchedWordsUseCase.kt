package com.bahadir.wordle.domain.usecase

import com.bahadir.wordle.domain.repository.WordsRepository
import javax.inject.Inject

class LastSearchedWordsUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {
    operator fun invoke() = wordsRepository.getLastSearchedWords()
}