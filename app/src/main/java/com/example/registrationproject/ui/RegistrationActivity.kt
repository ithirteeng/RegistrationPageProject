package com.example.registrationproject.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
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
import com.example.registrationproject.domain.SurnameValidator
import com.example.registrationproject.model.ErrorType
import java.util.*

class RegistrationActivity : AppCompatActivity() {

    companion object {
        const val INTENT_KEY = "key"
    }

    private val binding by lazy {
        ActivityRegistrationBinding.inflate(layoutInflater)
    }

    private var name: String? = null
    private var surname: String? = null
    private var date: String? = null
    private var password: String? = null
    private var secondPassword: String? = null

    private val nameValidator = NameValidator()
    private val surnameValidator = SurnameValidator()
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
            createIntent()
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
            R.style.date_picker_style,
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

    private fun createIntent() {
        if (validateData()) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(INTENT_KEY, "$surname $name")
            startActivity(intent)
        }

    }

    private fun validateData(): Boolean {
        val nameCheck = validateName()
        val surnameCheck = validateSurname()
        val dateCheck = validateDate()
        val passwordsCheck = validatePasswords()
        return nameCheck && surnameCheck && dateCheck && passwordsCheck
    }

    private fun validateName(): Boolean {
        return helpInValidation(
            nameValidator.checkValidity(name!!),
            binding.nameEditText,
            binding.nameErrorTextView
        )
    }

    private fun validateSurname(): Boolean {
        return helpInValidation(
            surnameValidator.checkValidity(surname!!),
            binding.surnameEditText,
            binding.surnameErrorTextView
        )
    }

    private fun validateDate(): Boolean {
        return helpInValidation(
            dateValidator.checkValidity(date!!),
            binding.dateEditText,
            binding.dateErrorTextView
        )
    }

    private fun validatePasswords(): Boolean {
        val firstCheck = validateFirstPassword()
        var secondCheck = true
        if (!binding.passwordErrorTextView.isVisible) {
            secondCheck = validateConfirmation()
        }
        return firstCheck && secondCheck
    }

    private fun validateFirstPassword(): Boolean {
        val validityCheck = helpInValidation(
            passwordValidator.checkValidity(password!!),
            binding.passwordEditText,
            binding.passwordErrorTextView
        )
        if (!validityCheck) {
            colorizeError(binding.confirmationEditText, binding.passwordErrorTextView)
        }
        return validityCheck

    }

    private fun validateConfirmation(): Boolean {
        return if (checkIsValidate(
                passwordValidator.checkEqualityPasswords(
                    password!!,
                    secondPassword!!
                )
            )
        ) {
            showValidatingError(
                binding.confirmationEditText,
                binding.confirmationErrorTextView,
                ErrorType.CONFIRMATION_ERROR
            )
            false
        } else {
            returnTextFieldsToNormalView(
                binding.confirmationErrorTextView,
                binding.confirmationEditText
            )
            returnTextFieldsToNormalView(
                binding.confirmationErrorTextView,
                binding.passwordEditText
            )
            true
        }
    }


    private fun helpInValidation(errorID: String, editText: EditText, textView: TextView): Boolean {
        return if (checkIsValidate(errorID)) {
            showValidatingError(editText, textView, errorID.toInt())
            false
        } else {
            returnTextFieldsToNormalView(textView, editText)
            true
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