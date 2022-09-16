package com.example.registrationproject.ui.model

import android.widget.EditText
import android.widget.TextView
import com.example.registrationproject.domain.model.ErrorType

class TextField(
    val type: TextFieldType,
    val errorId: Int,
    val editText: EditText,
    val textView: TextView
) {

    fun isValid(): Boolean{
        return errorId == ErrorType.OK
    }
}
