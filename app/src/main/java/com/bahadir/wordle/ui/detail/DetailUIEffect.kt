package com.bahadir.wordle.ui.detail

import androidx.annotation.StringRes
import com.bahadir.wordle.ui.base.Effect

sealed class DetailUIEffect : Effect {
    object FilterHide : DetailUIEffect()
    object StartAudio : DetailUIEffect()
    object BackPress : DetailUIEffect()
    data class Error(@StringRes val message: Int) : DetailUIEffect()

}