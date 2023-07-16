package com.bahadir.wordle.domain.usecase

import com.bahadir.wordle.common.Resource
import com.bahadir.wordle.domain.model.DefinitionUI
import com.bahadir.wordle.domain.model.WordsUI
import com.bahadir.wordle.domain.repository.WordsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock


