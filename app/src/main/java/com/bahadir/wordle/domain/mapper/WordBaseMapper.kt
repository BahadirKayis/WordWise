package com.bahadir.wordle.domain.mapper

interface WordBaseMapper<I, O> {
    fun map(input: I): O
}