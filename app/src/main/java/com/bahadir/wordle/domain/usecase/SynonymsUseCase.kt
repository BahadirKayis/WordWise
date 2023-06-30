package com.bahadir.wordle.domain.usecase

import com.bahadir.wordle.domain.repository.WordsRepository
import javax.inject.Inject

class SynonymsUseCase @Inject constructor(private val wordsRepository: WordsRepository) {
    operator fun invoke(word: String) = wordsRepository.getSynonyms(word)
}