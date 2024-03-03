package com.turing.android.ds

import android.util.Log
import java.lang.RuntimeException

/**
 * Лисенер действий над датасорсом
 *
 * @param T - тип объектов в датасорсе
 * @param action - действие, которое мониторит слушатель
 *
 * @author Diagorn
 */
abstract class DataSourceListener<T: HasId>(val action: DataSourceAction) {
    /**
     * Произвести необходимое действие
     *
     * @param obj - объект, с которым необходимо провести манипуляции с датасорсом
     */
    fun perform(obj: T) {
        Log.d(LOG_TAG, "performing action $action on object $obj")
        try {
            doPerform(obj)
        } catch (e: Throwable) {
            Log.e(LOG_TAG, "failed performing action $action on object $obj")
            throw RuntimeException(e)
        }
    }

    /**
     * Реальная логика произведения манипуляций с датасорсом
     *
     * @param obj - объект, с которым необходимо провести манипуляции с датасорсом
     */
    protected open abstract fun doPerform(obj: T)

    companion object {
        const val LOG_TAG = "DsListener"
    }
}

/**
 * Действия, которые можно проводить с датасорсом
 *
 * @author Diagorn
 */
enum class DataSourceAction {
    /**
     * Добавить новый объект
     */
    INSERT,

    /**
     * Редактировать существующий объект
     */
    EDIT,

    /**
     * Удалить старый объект
     */
    REMOVE
}