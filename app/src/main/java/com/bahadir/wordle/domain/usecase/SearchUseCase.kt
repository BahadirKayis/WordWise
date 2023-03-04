package com.bahadir.wordle.domain.usecase

import com.bahadir.wordle.R
import com.bahadir.wordle.common.Resource
import com.bahadir.wordle.domain.model.DefinitionUI
import com.bahadir.wordle.domain.repository.WordsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {
    operator fun invoke(word: String) = flow {
        val words = wordsRepository.getWords(word)
        val synonyms = wordsRepository.getSynonyms(word)
        wordsRepository.setWords(word)
        words.combine(synonyms) { w, s ->
            when {
                w is Resource.Success && s is Resource.Success -> {
                    //Bazı kelimlerlde ABD-UK farkı var. Bu yüzden 2 tane definitionUI var. Bunları birleştiriyoruz.
                    val definitionUI: MutableList<DefinitionUI> = mutableListOf()
                    if (w.data.count() > 1) {
                        w.data.forEachIndexed { index, data ->
                            if (index == 0) data.definitionUI.forEach {
                                definitionUI.add(it.copy(countryFlag = R.drawable.ic_flag_us))
                            }
                            else data.definitionUI.forEach {
                                definitionUI.add(it.copy(countryFlag = R.drawable.ic_flag_uk))
                            }
                        }
                    }
                    w.data[0].definitionUI = definitionUI
                    emit(Resource.Success(Pair(w.data[0], s.data)))

                }
                w is Resource.Error -> {
                    emit(Resource.Error(w.errorMessage))
                }
                s is Resource.Error -> {
                    emit(Resource.Error(s.errorMessage))
                }
            }
            true
        }.collect()
    }
}