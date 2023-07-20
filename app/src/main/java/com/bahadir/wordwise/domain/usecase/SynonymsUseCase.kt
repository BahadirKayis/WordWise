package com.bahadir.wordwise.domain.usecase

import com.bahadir.wordwise.domain.repository.WordsRepository
import javax.inject.Inject

class SynonymsUseCase @Inject constructor(private val wordsRepository: WordsRepository) {
    operator fun invoke(word: String) = wordsRepository.getSynonyms(word)
}
