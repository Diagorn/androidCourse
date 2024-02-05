package com.turing.androidcourse

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.turing.androidcourse.button_handlers.AbstractButtonHandler
import com.turing.androidcourse.button_handlers.DivideButtonHandler
import com.turing.androidcourse.button_handlers.MinusButtonHandler
import com.turing.androidcourse.button_handlers.MultiplyButtonHandler
import com.turing.androidcourse.button_handlers.PlusButtonHandler
import com.turing.androidcourse.databinding.ActivityMainBinding

private const val FIRST_EDIT_TEXT_ID = "firstEdit"
private const val SECOND_EDIT_TEXT_ID = "secondEdit"
private const val RESULT_TEXT_ID = "resultText"

/**
 * Главная активити с калькулятором
 *
 * @author Diagorn
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * Мапа обработчиков событий кликов
     *
     * Ключ - айдишник кнопки
     * Значение - объект обработчика
     */
    private lateinit var handlersMap: Map<Int, AbstractButtonHandler>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

            val editTextList = listOf(
                editTextArgument1, editTextArgument2
            )

            handlersMap = mapOf(
                buttonPlus.id to PlusButtonHandler(
                    editTextList,
                    binding.resultTextView,
                    this@MainActivity
                ),
                buttonMinus.id to MinusButtonHandler(
                    editTextList,
                    binding.resultTextView,
                    this@MainActivity
                ),
                buttonMultiply.id to MultiplyButtonHandler(
                    editTextList,
                    binding.resultTextView,
                    this@MainActivity
                ),
                buttonDivide.id to DivideButtonHandler(
                    editTextList,
                    binding.resultTextView,
                    this@MainActivity
                ),
            )

        }

        setOnClickListeners()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        with(binding) {
            outState.putString(FIRST_EDIT_TEXT_ID, editTextArgument1.text.toString())
            outState.putString(SECOND_EDIT_TEXT_ID, editTextArgument2.text.toString())
            outState.putString(RESULT_TEXT_ID, resultTextView.text.toString())
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        with(binding) {
            editTextArgument1.setText(savedInstanceState.getString(FIRST_EDIT_TEXT_ID))
            editTextArgument2.setText(savedInstanceState.getString(SECOND_EDIT_TEXT_ID))
            resultTextView.text = savedInstanceState.getString(RESULT_TEXT_ID)
        }
    }

    private fun setOnClickListeners() {
        with(binding) {
            buttonPlus.setOnClickListener {
                handlersMap[buttonPlus.id]?.handle()
            }

            buttonMinus.setOnClickListener {
                handlersMap[buttonMinus.id]?.handle()
            }

            buttonMultiply.setOnClickListener {
                handlersMap[buttonMultiply.id]?.handle()
            }

            buttonDivide.setOnClickListener {
                handlersMap[buttonDivide.id]?.handle()
            }

            aboutButton.setOnClickListener {
                val intent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(intent)
            }
        }
    }
}