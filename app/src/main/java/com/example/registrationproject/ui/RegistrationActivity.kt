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
import com.example.registrationproject.data.repositoriew.UserRepositoryImpl
import com.example.registrationproject.databinding.ActivityRegistrationBinding
import com.example.registrationproject.domain.model.ErrorType
import com.example.registrationproject.domain.model.User
import com.example.registrationproject.domain.uc.DataValidatorUseCase
import com.example.registrationproject.domain.uc.GetUserNameUseCase
import com.example.registrationproject.domain.uc.SaveUserDataUseCase
import com.example.registrationproject.domain.validator.DateValidator
import com.example.registrationproject.domain.validator.NameValidator
import com.example.registrationproject.domain.validator.PasswordValidator
import com.example.registrationproject.domain.validator.SurnameValidator
import java.util.*

class RegistrationActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityRegistrationBinding.inflate(layoutInflater)
    }

    private val userRepositoryImpl = UserRepositoryImpl()

    private val saveUserDataUseCase = SaveUserDataUseCase(userRepositoryImpl)
    private val getUserDataUseCase = GetUserNameUseCase(userRepositoryImpl)
    private val dataValidatorUseCase = DataValidatorUseCase()

    private var name: String? = null
    private var surname: String? = null
    private var date: String? = null
    private var password: String? = null
    private var secondPassword: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        registrationButtonClickListener()
        calendarButtonTouchListener()
    }

    private fun registrationButtonClickListener() {
        binding.registrationButton.setOnClickListener {
            setupGetters()
            if (checkUserDataValidity()) {
                saveUserDataUseCase.execute(User(
                    name!!,
                    surname!!,
                    date!!,
                    password!!
                ))
                makeIntent()
            }

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
        name = convertDataFieldsToCorrectForm(binding.nameEditText)
    }

    private fun getSurname() {
        surname = convertDataFieldsToCorrectForm(binding.surnameEditText)
    }

    private fun getDate() {
        date = convertDataFieldsToCorrectForm(binding.dateEditText)
    }

    private fun getPassword() {
        password = binding.passwordEditText.text.toString()
    }

    private fun getConfirmatedPassword() {
        secondPassword = binding.confirmationEditText.text.toString()
    }

    private fun convertDataFieldsToCorrectForm(editText: EditText): String {
        return editText.text.toString().replace(" ", "")
    }


    private fun checkUserDataValidity(): Boolean {
        val nameCheck = checkNameValidity()
        val surnameCheck = checkSurnameValidity()
        val dateCheck = checkDateValidity()
        val passwordsCheck = checkPasswordConditionsValidity()
        return nameCheck && surnameCheck && dateCheck && passwordsCheck
    }

    private fun checkNameValidity(): Boolean {
        return helpInValidation(
            dataValidatorUseCase.execute(NameValidator(), name!!),
            binding.nameEditText,
            binding.nameErrorTextView
        )
    }

    private fun checkSurnameValidity(): Boolean {
        return helpInValidation(
            dataValidatorUseCase.execute(SurnameValidator(), surname!!),
            binding.surnameEditText,
            binding.surnameErrorTextView
        )
    }

    private fun checkDateValidity(): Boolean {
        return helpInValidation(
            dataValidatorUseCase.execute(DateValidator(), date!!),
            binding.dateEditText,
            binding.dateErrorTextView
        )
    }

    private fun checkPasswordConditionsValidity(): Boolean {
        val passwordsValidityCheck = checkPasswordValidity()
        var confirmationCheck = true
        if (!binding.passwordErrorTextView.isVisible) {
            confirmationCheck = checkPasswordsConfirmation()
        }
        return passwordsValidityCheck && confirmationCheck
    }

    private fun checkPasswordValidity(): Boolean {
        val validityCheck = helpInValidation(
            dataValidatorUseCase.execute(PasswordValidator(), password!!),
            binding.passwordEditText,
            binding.passwordErrorTextView
        )
        if (!validityCheck) {
            colorizeError(binding.confirmationEditText, binding.passwordErrorTextView)
        }
        return validityCheck

    }

    private fun checkPasswordsConfirmation(): Boolean {
        val errorId = dataValidatorUseCase.execute(
            passwordValidator = PasswordValidator(),
            firstPassword = password!!,
            secondPassword = secondPassword!!
        )
        return if (checkIsValid(errorId)) {
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
        return if (checkIsValid(errorID)) {
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

    private fun checkIsValid(id: String): Boolean {
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


    private fun makeIntent() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.INTENT_KEY, getUserDataUseCase.execute())
        startActivity(intent)

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
}