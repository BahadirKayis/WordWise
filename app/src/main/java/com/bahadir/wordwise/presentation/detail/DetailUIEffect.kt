package com.bahadir.wordwise.presentation.detail

import androidx.annotation.StringRes
import com.bahadir.wordwise.base.Effect

sealed class DetailUIEffect : Effect {
    object FilterHide : DetailUIEffect()
    object StartAudio : DetailUIEffect()
    object BackPress : DetailUIEffect()
    data class Error(@StringRes val message: Int) : DetailUIEffect()

}