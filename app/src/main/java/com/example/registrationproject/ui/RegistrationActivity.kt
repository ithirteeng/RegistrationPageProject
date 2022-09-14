package com.example.registrationproject.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.registrationproject.R
import com.example.registrationproject.databinding.ActivityRegistrationBinding
import com.example.registrationproject.domain.DateValidator
import com.example.registrationproject.domain.NameValidator
import com.example.registrationproject.domain.PasswordValidator
import com.example.registrationproject.model.ErrorType
import java.util.*

class RegistrationActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityRegistrationBinding.inflate(layoutInflater)
    }

    private var name: String? = null
    private var surname: String? = null
    private var date: String? = null
    private var password: String? = null
    private var secondPassword: String? = null
    private val nameValidator = NameValidator()
    private val dateValidator = DateValidator()
    private val passwordValidator = PasswordValidator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        registrationButtonClickListener()
        calendarButtonTouchListener()
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
        secondPassword = binding.confirmationEditText.text.toString()
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
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun validateData() {
        validateName()
        validateSurname()
        validateDate()
        validatePasswords()
    }

    private fun validateName() {
        if (checkIsValidate(nameValidator.checkValidity(name!!))) {
            showValidatingError(
                binding.nameEditText,
                binding.nameErrorTextView,
                nameValidator.checkValidity(name!!).toInt()
            )
        } else {
            returnTextFieldsToNormalView(binding.nameErrorTextView, binding.nameEditText)
        }
    }

    private fun validateSurname() {
        if (checkIsValidate(nameValidator.checkValidity(surname!!))) {
            if (nameValidator.checkValidity(surname!!).toInt() == ErrorType.FIRST_NAME_ERROR) {
                showValidatingError(
                    binding.surnameEditText,
                    binding.surnameErrorTextView,
                    ErrorType.FIRST_SURNAME_ERROR
                )
            } else {
                showValidatingError(
                    binding.surnameEditText,
                    binding.surnameErrorTextView,
                    ErrorType.SECOND_SURNAME_ERROR
                )
            }
        } else {
            returnTextFieldsToNormalView(binding.surnameErrorTextView, binding.surnameEditText)
        }
    }

    private fun validateDate() {
        if (checkIsValidate(dateValidator.checkValidity(date!!))) {
            showValidatingError(
                binding.dateEditText,
                binding.dateErrorTextView,
                ErrorType.DATE_ERROR
            )
        } else {
            returnTextFieldsToNormalView(binding.dateErrorTextView, binding.dateEditText)
        }
    }

    private fun validatePasswords() {
        validateFirstPassword()
        if (!binding.passwordErrorTextView.isVisible) {
            validateConfirmation()
        }
    }

    private fun validateFirstPassword() {
        if (checkIsValidate(passwordValidator.checkValidity(password!!))) {
            showValidatingError(
                binding.passwordEditText,
                binding.passwordErrorTextView,
                ErrorType.PASSWORD_ERROR
            )
            colorizeError(binding.confirmationEditText, binding.passwordErrorTextView)
        } else {
            returnTextFieldsToNormalView(binding.passwordErrorTextView, binding.passwordEditText)
        }
    }

    private fun validateConfirmation() {
        if (checkIsValidate(passwordValidator.checkEqualityPasswords(password!!, secondPassword!!))) {
            showValidatingError(
                binding.confirmationEditText,
                binding.confirmationErrorTextView,
                ErrorType.CONFIRMATION_ERROR
            )
        } else {
            returnTextFieldsToNormalView(
                binding.confirmationErrorTextView,
                binding.confirmationEditText
            )
            returnTextFieldsToNormalView(
                binding.confirmationErrorTextView,
                binding.passwordEditText
            )
        }
    }

    private fun returnTextFieldsToNormalView(textView: TextView, editText: EditText) {
        textView.visibility = View.INVISIBLE
        editText.setHintTextColor(
            resources.getColor(
                R.color.edit_text_hint_color,
                this.theme
            )
        )
    }

    private fun checkIsValidate(id: String): Boolean {
        return id != ErrorType.OK
    }

    private fun showValidatingError(editText: EditText, textView: TextView, id: Int) {
        changeText(textView, id)
        colorizeError(editText, textView)
    }

    private fun changeText(textView: TextView, id: Int) {
        textView.text = resources.getString(id)
    }

    private fun colorizeError(editText: EditText, textView: TextView) {
        editText.setHintTextColor(Color.RED)
        editText.text.clear()
        textView.visibility = View.VISIBLE
    }

}