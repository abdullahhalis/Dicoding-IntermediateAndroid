package com.dicoding.mystory.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import com.dicoding.mystory.R
import com.google.android.material.textfield.TextInputEditText

class CustomEditText: TextInputEditText {
    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                when (inputType) {
                    EMAIL -> {
                        error = if (!Patterns.EMAIL_ADDRESS.matcher(p0).matches()) {
                            context.getString(R.string.invalid_email)
                        } else {
                            null
                        }
                    }
                    PASSWORD -> {
                        error = if (p0.toString().length < 8) {
                            context.getString(R.string.invalid_password)
                        } else {
                            null
                        }
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    companion object {
        const val EMAIL = 0x00000021
        const val PASSWORD = 0x00000081
    }
}