package com.turing.android

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.turing.android.databinding.ActivityAddPersonBinding
import com.turing.android.dto.TuringPerson

/**
 * Активити добавления нового активиста
 *
 * @author Diagorn
 */
class AddTuringPersonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPersonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addPersonButton.setOnClickListener {
            if (isFormInvalid()) {
                Toast.makeText(
                    this@AddTuringPersonActivity,
                    getString(R.string.fill_all_fields_toast),
                    Toast.LENGTH_SHORT
                )
                    .show()
                return@setOnClickListener
            }

            with(binding) {
                val person = TuringPerson(
                    identifyer = intent.getLongExtra(NEXT_PERSON_ID, -1L),
                    name = nameEditText.text.toString(),
                    surname = surnameEditText.text.toString(),
                    age = ageEditText.text.toString().toInt(),
                    isStudent = studentRadioButton.isChecked,
                    avatarUrl = avatarUrlEditText.text.toString()
                )

                if (person.id == -1L) {
                    setResult(RESULT_CANCELED)
                    finish()
                }

                setResult(RESULT_OK, Intent().also { it.putExtra(TURING_PERSON_RESULT, person) })
                finish()
            }
        }
    }

    private fun isFormInvalid(): Boolean {
        with(binding) {
            if (
                !studentRadioButton.isChecked && !curatorRadioButton.isChecked
                || ageEditText.text.isNullOrEmpty()
                || avatarUrlEditText.text.isNullOrEmpty()
                || nameEditText.text.isNullOrEmpty()
                || surnameEditText.text.isNullOrEmpty()
            ) {
                return true
            }
        }

        return false
    }

    companion object {
        const val TURING_PERSON_RESULT = "TURING_PERSON_RESULT"
    }
}
