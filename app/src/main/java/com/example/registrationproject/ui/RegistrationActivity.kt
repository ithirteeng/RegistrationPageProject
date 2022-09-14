package com.example.registrationproject.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.registrationproject.R
import com.example.registrationproject.databinding.ActivityRegistrationBinding
import com.example.registrationproject.domain.DateValidator
import com.example.registrationproject.domain.NameValidator
import com.example.registrationproject.domain.PasswordValidator
import java.util.*

class RegistrationActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityRegistrationBinding.inflate(layoutInflater)
    }

    private var name: String? = null
    private var surname: String? = null
    private var date: String? = null
    private var password: String? = null
    private var confirmatedPassword: String? = null
    private val nameValidator = NameValidator()
    private val dateValidator = DateValidator()
    private val passwordValidator = PasswordValidator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        registrationButtonClickListener()
        calendarButtonTouchListener()
        returnNormalColor()
    }

    private fun registrationButtonClickListener() {
        binding.registrationButton.setOnClickListener {
            setupGetters()
            validateData()
        }
    }

    private fun setupGetters() {
        getName()
        getSurname()
        getDate()
        getPassword()
        getConfirmatedPassword()
    }


    private fun calendarButtonTouchListener() {
        binding.calendarButton.setOnClickListener {
            pickDate()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun pickDate() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, day ->
                var dayString = day.toString()
                var monthString = month.toString()
                if (day < 10) {
                    dayString = "0$day"
                }
                if (month < 10) {
                    monthString = "0$month"
                }
                binding.dateEditText.setText("$dayString.$monthString.$year")
                Log.d("DATE", "$date")
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }


    private fun getName() {
        name = binding.nameEditText.text.toString()
    }

    private fun getSurname() {
        surname = binding.surnameEditText.text.toString()
    }

    private fun getDate() {
        date = binding.dateEditText.text.toString()


    }

    private fun getPassword() {
        password = binding.passwordEditText.text.toString()
    }

    private fun getConfirmatedPassword() {
        confirmatedPassword = binding.passwordConfirmationEditText.text.toString()
    }


    private fun validateData() {
        if (!nameValidator.checkValidity(name!!)) {
            showValidatingError(
                binding.nameEditText,
                resources.getString(R.string.name_error)
            )
        } else if (!nameValidator.checkValidity(surname!!)) {
            showValidatingError(
                binding.surnameEditText,
                resources.getString(R.string.surname_error)
            )
        } else if (!dateValidator.checkValidity(date!!)) {
            showValidatingError(
                binding.dateEditText,
                resources.getString(R.string.date_error)
            )
        } else if (!passwordValidator.checkValidity(password!!)) {
            showValidatingError(
                binding.passwordEditText,
                resources.getString(R.string.password_error)
            )
        } else if (!passwordValidator.checkEqualityPasswords(password!!, confirmatedPassword!!)) {
            showValidatingError(
                binding.passwordEditText,
                resources.getString(R.string.confirmation_error)
            )
            showValidatingError(binding.passwordConfirmationEditText, "")
        } else {
            showToast("INTENT")
        }
    }

    private fun showValidatingError(editText: EditText, message: String) {
        colorizeEditText(editText)
        if (editText != binding.passwordConfirmationEditText) {
            showToast(message)
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun colorizeEditText(editText: EditText) {
        editText.setHintTextColor(Color.RED)
        editText.text.clear()
    }

    private fun returnNormalColor() {
        for (editTextId in binding.editTextsGroup.referencedIds) {
            val editText = findViewById<EditText>(editTextId)
            findViewById<View>(editTextId).setOnFocusChangeListener { _, isFocused ->
                if (isFocused) {
                    if (editText.text.isEmpty()) {
                        editText.setHintTextColor(
                            resources.getColor(
                                R.color.edit_text_hint_color,
                                this.theme
                            )
                        )
                    }
                } else {
                    editText.defaultFocusHighlightEnabled = false
                }
            }
        }
    }

}