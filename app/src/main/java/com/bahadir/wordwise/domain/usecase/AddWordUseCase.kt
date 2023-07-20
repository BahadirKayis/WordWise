package com.bahadir.wordwise.domain.usecase

import com.bahadir.wordwise.domain.repository.WordsRepository
import javax.inject.Inject

class AddWordUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {
    suspend operator fun invoke(word: String, lastSearch: List<String>) =
        wordsRepository.addSearchedWord(word, lastSearch)
}