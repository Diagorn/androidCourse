package com.turing.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Вьюмодель фрагмента кликера
 *
 * @author Diagorn
 */
class ClickerViewModel : ViewModel() {

    private val _clickerData = MutableLiveData<ClickerData>(null)
    val clickerData: LiveData<ClickerData> = _clickerData

    /**
     * Обновить данные о предсказании
     */
    fun initData() {
        _clickerData.postValue(
            ClickerData(
                predictionText = PredictionRepository.getRandomPrediction(),
                predictionImageSrc = PredictionRepository.getRandomPredictionUrl()
            )
        )
    }

    /**
     * Предсказание
     *
     * @param predictionText - текст предсказания
     * @param predictionImageSrc - ссылка на картинку с предсказанием
     *
     * @author Diagorn
     */
    data class ClickerData(
        val predictionText: String,
        val predictionImageSrc: String
    )
}