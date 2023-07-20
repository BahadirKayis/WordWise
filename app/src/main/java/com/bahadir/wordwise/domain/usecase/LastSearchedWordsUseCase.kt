package com.bahadir.wordwise.domain.usecase

import com.bahadir.wordwise.domain.repository.WordsRepository
import javax.inject.Inject

class LastSearchedWordsUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {
    operator fun invoke() = wordsRepository.getLastSearchedWords()
}