package com.bahadir.wordwise.presentation.detail

import com.bahadir.wordwise.base.Event


sealed class DetailEvent : Event {
    object Voice : DetailEvent()
    data class Filter(val meaning: String, val count: Int) : DetailEvent()
    object FilterClear : DetailEvent()
    object BackPress : DetailEvent()
}