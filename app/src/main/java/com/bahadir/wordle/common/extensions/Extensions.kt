package com.bahadir.wordle.common.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern


fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}


fun Context.setShared(key: String, value: String) {
    val sharedPref = getSharedPreferences("shared", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString(key, value)
        apply()
    }
}

fun Context.getShared(key: String): String? {
    val sharedPref = getSharedPreferences("shared", Context.MODE_PRIVATE)
    return sharedPref.getString(key, null)
}

fun Activity.hideSystemUI() {
    //  @Suppress("DEPRECATION")
//    window.decorView.apply {
//        // Hide both the navigation bar and the status bar.
//        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
//        // a general rule, you should design your app to hide the status bar whenever you
//        // hide the navigation bar.
//
//        systemUiVisibility = View.STATUS_BAR_VISIBLE
//    }
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}


fun TextInputLayout.isValidEmail(string: String, errorMessage: String): Boolean {
    return if (Patterns.EMAIL_ADDRESS.matcher(string).matches()) {
        isErrorEnabled = false
        true
    } else {
        error = errorMessage
        false
    }
}

fun View.snackBar(message: String) {
    Snackbar.make(this, message, 5000).show()
}


fun Resources.colorStateList(@ColorRes color: Int) =
    ResourcesCompat.getColorStateList(this, color, null)

fun Resources.color(@ColorRes color: Int): Int {
    return ResourcesCompat.getColor(this, color, null)
}


fun String.customEdittextNullControl() {
    if (this.isEmpty()) {
        throw Exception("CustomViewTextInputLayout's hint can not be null")
    }
}


fun TextInputLayout.isValidPassword(password: String, errorMessage: String): Boolean {
    val passwordREGEX = Pattern.compile(
        "^" + "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                ".{4,}" +               //at least 8 characters
                "$"
    )
    return if (passwordREGEX.matcher(password).matches()) {
        isErrorEnabled = false
        true
    } else {
        error = errorMessage
        false
    }
}

//    val passwordREGEX = Pattern.compile(
//        "^" +
//                "(?=.*[0-9])" +         //at least 1 digit
//                "(?=.*[a-z])" +         //at least 1 lower case letter
//                "(?=.*[A-Z])" +         //at least 1 upper case letter
//                "(?=.*[a-zA-Z])" +      //any letter
//                "(?=.*[@#$%^&+=])" +    //at least 1 special character
//                "(?=\\S+$)" +           //no white spaces
//                ".{8,}" +               //at least 8 characters
//                "$"
//    )