package com.bahadir.wordle.common.extensions

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat
import androidx.datastore.preferences.preferencesDataStore
import com.bahadir.wordle.R
import com.bahadir.wordle.databinding.CustomSnackbarBinding
import com.google.android.material.snackbar.Snackbar

fun View.gone() {
    visibility = View.GONE
}

val Context.dataStore by preferencesDataStore("last_searched")

fun View.visible() {
    visibility = View.VISIBLE
}

fun String.titleCaseFirstChar() = replaceFirstChar(Char::titlecase)

fun Context.showCustomSnackBar(message: String, container: View?) {
    container?.let {
        val snackView = View.inflate(this, R.layout.custom_snackbar, null)
        val binding = CustomSnackbarBinding.bind(snackView)
        val snackBar = Snackbar.make(container, "", Snackbar.LENGTH_LONG)
        snackBar.apply {
            setBackgroundTint(resources.color(R.color.red))
            (view as ViewGroup).addView(binding.root)
            binding.text.text = message
            show()
        }
    }
}

fun Resources.colorStateList(@ColorRes color: Int) =
    ResourcesCompat.getColorStateList(this, color, null)

fun Resources.color(@ColorRes color: Int): Int {
    return ResourcesCompat.getColor(this, color, null)
}
