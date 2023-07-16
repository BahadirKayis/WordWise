package com.bahadir.wordle.presentation.detail

import androidx.annotation.StringRes
import com.bahadir.wordle.presentation.base.Effect

sealed class DetailUIEffect : Effect {
    object FilterHide : DetailUIEffect()
    object StartAudio : DetailUIEffect()
    object BackPress : DetailUIEffect()
    data class Error(@StringRes val message: Int) : DetailUIEffect()

}