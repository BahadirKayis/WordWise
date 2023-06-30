package com.bahadir.wordle.domain.usecase

import android.util.Log
import com.bahadir.wordle.R
import com.bahadir.wordle.common.Resource
import com.bahadir.wordle.domain.model.DefinitionUI
import com.bahadir.wordle.domain.model.WordsUI
import com.bahadir.wordle.domain.repository.WordsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {
    operator fun invoke(word: String): Flow<Resource<WordsUI>> = flow {
        wordsRepository.getWords(word).collect { w ->
            when (w) {
                is Resource.Success -> {
                    //Bazı kelimlerlde ABD-UK farkı var. Bu yüzden 2 tane definitionUI var. Bunları birleştiriyoruz.
                    if (w.data.count() > 1) {
                        val definitionUI: MutableList<DefinitionUI> = mutableListOf()
                        w.data.forEachIndexed { index, data ->
                            if (index == 0) data.definitionUI.forEach {
                                definitionUI.add(it.copy(countryFlag = R.drawable.ic_flag_us))
                            }
                            else data.definitionUI.forEach {
                                definitionUI.add(it.copy(countryFlag = R.drawable.ic_flag_uk))
                            }
                        }
                        w.data[0].definitionUI = definitionUI
                    }

                    emit(Resource.Success(w.data[0]))

                }

                is Resource.Error -> {
                    emit(Resource.Error(w.errorMessage))
                }
            }
        }
    }
}