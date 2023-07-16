package com.bahadir.wordle.domain.usecase

import com.bahadir.wordle.domain.repository.WordsRepository
import javax.inject.Inject

class AddWordUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {
    suspend operator  fun invoke(word: String, lastSearch: List<String>) =
        wordsRepository.addSearchedWord(word, lastSearch)
}