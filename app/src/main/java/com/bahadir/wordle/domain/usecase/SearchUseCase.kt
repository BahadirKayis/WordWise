package com.bahadir.wordle.domain.usecase

import com.bahadir.wordle.common.Resource
import com.bahadir.wordle.domain.repository.WordsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {
    operator fun invoke(word: String) = flow {
        wordsRepository.getWords(word).collect {
            when (it) {
                is Resource.Success -> {
                    emit(Resource.Success(it.data))
                }
                is Resource.Error -> {
                    emit(Resource.Error(it.throwable))
                }
            }
        }
    }


}