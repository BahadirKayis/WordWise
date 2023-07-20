package com.bahadir.wordwise.presentation.base.customcomponentview

import android.content.Context
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Transformation
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import com.bahadir.wordwise.R
import com.bahadir.wordwise.common.extensions.colorStateList
import com.bahadir.wordwise.databinding.CustomViewEdittextBinding


class CustomTextInputLayout @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle) {
    private val binding =
        CustomViewEdittextBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.obtainStyledAttributes(attributeSet, R.styleable.CustomTextInputLayout).apply {
            val startIcon = getResourceId(R.styleable.CustomTextInputLayout_cviL_startIcon, 0)
            val passwordToggle =
                getBoolean(R.styleable.CustomTextInputLayout_cviL_passwordToggle, false)

            val hintE = getString(R.styleable.CustomTextInputLayout_cviE_hint)
            val inputTypeE = getInt(R.styleable.CustomTextInputLayout_cviE_inputType, 0)

            val startIconTint = getResourceId(
                R.styleable.CustomTextInputLayout_cviL_startIconTint, R.color.ccv_neutral_dark
            )

            recycle()
            startIcon(startIcon)
            passwordToggle(passwordToggle)
            startIconTint(startIconTint)
            inputTypeE(inputTypeE)
            hintE(hintE)
        }
    }


    fun getText(): String {
        return binding.editText.text.toString()
    }

    fun imeOptions(action: (Unit) -> Unit) {
        binding.editText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                action.invoke(Unit)
            }
            false
        }
    }

    fun clear(){
        binding.editText.text?.clear()
    }

    private fun startIconTint(color: Int) {
        binding.layout.setStartIconTintList(resources.colorStateList(color))
    }

    fun starIconClickListener(click: () -> Unit) {
        binding.layout.setStartIconOnClickListener { click.invoke() }
    }

    private fun passwordToggle(passwordToggle: Boolean) {
        @Suppress("DEPRECATION")
        binding.layout.isPasswordVisibilityToggleEnabled = passwordToggle
    }

    private fun startIcon(startIcon: Int) {
        binding.layout.startIconDrawable = ResourcesCompat.getDrawable(resources, startIcon, null)
    }

    private fun hintE(hint: String?) {
        binding.editText.hint = hint

    }

    private fun inputTypeE(inputType: Int) {
        val type = when (CustomToolbarViewInputType.fromValue(inputType)) {
            CustomToolbarViewInputType.TEXT -> Transformation.TYPE_ALPHA
            CustomToolbarViewInputType.NUMBER -> InputType.TYPE_CLASS_NUMBER
            CustomToolbarViewInputType.PASSWORD -> InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
            CustomToolbarViewInputType.EMAIL -> InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }

        type.apply {
            if (this == InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT) {
                binding.editText.transformationMethod = PasswordTransformationMethod.getInstance()

            } else {
                binding.editText.inputType = type
            }
        }
    }

    enum class CustomToolbarViewInputType(val type: Int) {
        TEXT(0), NUMBER(1), PASSWORD(2), EMAIL(3);

        companion object {
            fun fromValue(value: Int): CustomToolbarViewInputType {
                return values().first { it.type == value }
            }
        }
    }
}