package com.example.registrationproject.ui.activityregistration

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.registrationproject.R
import com.example.registrationproject.databinding.ActivityRegistrationBinding
import com.example.registrationproject.domain.model.User
import com.example.registrationproject.domain.validator.DateValidator
import com.example.registrationproject.domain.validator.NameValidator
import com.example.registrationproject.domain.validator.PasswordValidator
import com.example.registrationproject.domain.validator.SurnameValidator
import com.example.registrationproject.ui.activitymain.MainActivity
import com.example.registrationproject.ui.model.TextField
import com.example.registrationproject.ui.model.TextFieldType
import java.util.*

class RegistrationActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityRegistrationBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        RegistrationActivityViewModel(this.application)
    }

    private var name: String? = null
    private var surname: String? = null
    private var date: String? = null
    private var password: String? = null
    private var secondPassword: String? = null

    private val textFieldsMap: MutableMap<TextFieldType, TextField> = mutableMapOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        registrationButtonClickListener()
        calendarButtonTouchListener()
    }

    private fun registrationButtonClickListener() {
        binding.registrationButton.setOnClickListener {
            setupGetters()
            validateUserData()
            if (checkUserDataValidity()) {
                viewModel.saveUserData(User(name!!, surname!!, date!!, password!!))
                makeIntent()
            }

        }
    }

    private fun setupGetters() {
        getName()
        getSurname()
        getDate()
        getPassword()
        getConfirmationPassword()
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

    private fun getConfirmationPassword() {
        secondPassword = binding.confirmationEditText.text.toString()
    }

    private fun convertDataFieldsToCorrectForm(editText: EditText): String {
        return editText.text.toString().replace(" ", "")
    }


    private fun checkUserDataValidity(): Boolean {
        for (textField in textFieldsMap) {
            if (!textField.value.isValid()) {
                return false
            }
        }
        return true
    }


    private fun validateUserData() {
        validateName()
        validateSurname()
        validateDate()
        validatePassword()
        validateConfirmation()
    }

    private fun validateName() {
        val nameTextField = TextField(
            type = TextFieldType.NAME,
            errorId = viewModel.validateData(NameValidator(), name!!),
            editText = binding.nameEditText,
            textView = binding.nameErrorTextView
        )
        textFieldsMap[TextFieldType.NAME] = nameTextField
        changeTextFieldsByValidity(nameTextField)
    }

    private fun validateSurname() {
        val surnameTextField = TextField(
            type = TextFieldType.SURNAME,
            errorId = viewModel.validateData(SurnameValidator(), surname!!),
            editText = binding.surnameEditText,
            textView = binding.surnameErrorTextView
        )
        textFieldsMap[TextFieldType.SURNAME] = surnameTextField
        changeTextFieldsByValidity(surnameTextField)
    }

    private fun validateDate() {
        val dateTextField = TextField(
            type = TextFieldType.DATE,
            errorId = viewModel.validateData(DateValidator(), date!!),
            editText = binding.dateEditText,
            textView = binding.dateErrorTextView
        )
        textFieldsMap[TextFieldType.DATE] = dateTextField
        changeTextFieldsByValidity(dateTextField)
    }

    private fun validatePassword() {
        val passwordTextField = TextField(
            type = TextFieldType.PASSWORD,
            errorId = viewModel.validateData(PasswordValidator(), password!!),
            editText = binding.passwordEditText,
            textView = binding.passwordErrorTextView
        )
        textFieldsMap[TextFieldType.PASSWORD] = passwordTextField
        changeTextFieldsByValidity(passwordTextField)
    }

    private fun validateConfirmation() {
        val confirmationTextField = TextField(
            type = TextFieldType.CONFIRMATION,
            errorId = viewModel.validateData(PasswordValidator(), password!!, secondPassword!!),
            editText = binding.confirmationEditText,
            textView = binding.confirmationErrorTextView
        )
        textFieldsMap[TextFieldType.CONFIRMATION] = confirmationTextField
        changeTextFieldsByValidity(confirmationTextField)
    }

    private fun changeTextFieldsByValidity(textField: TextField) {
        if (textField.isValid()) {
            returnTextFieldsToNormalView(textField.textView, textField.editText)
            if (textField.type == TextFieldType.CONFIRMATION) {
                returnTextFieldsToNormalView(
                    binding.confirmationErrorTextView,
                    binding.passwordEditText
                )
            }
        } else {
            showValidatingError(textField.editText, textField.textView, textField.errorId)
        }
    }


    private fun showValidatingError(editText: EditText, textView: TextView, errorId: Int) {
        changeTextByErrorType(textView, errorId)
        colorizeError(editText, textView)
    }

    private fun changeTextByErrorType(textView: TextView, errorId: Int) {
        textView.text = resources.getString(errorId)
    }

    private fun colorizeError(editText: EditText, textView: TextView) {
        editText.setHintTextColor(Color.RED)
        editText.text.clear()
        textView.visibility = View.VISIBLE
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


    private fun makeIntent() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.INTENT_KEY, viewModel.getUserNameSurname())
        startActivity(intent)
        overridePendingTransition(0, 0)
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
