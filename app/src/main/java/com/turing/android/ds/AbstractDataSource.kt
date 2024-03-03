package com.turing.android.ds

import com.turing.android.ds.DataSourceAction.INSERT
import com.turing.android.ds.DataSourceAction.REMOVE

/**
 * Абстрактный класс датасорса
 *
 * @param T - класс объектов, которые содержатся в датасорсе
 *
 * @author Diagorn
 */
abstract class AbstractDataSource<T: HasId> {

    /**
     * Контейнер с объектами
     */
    protected val objects = mutableListOf<T>()

    /**
     * Слушатели действий, которые производятся с датасорсом
     */
    private val listeners = mutableListOf<DataSourceListener<T>>()

    /**
     * Получить список всех объектов в датасорсе
     */
    fun getAll(): List<T> = objects

    /**
     * Добавить новый объект в список
     *
     * @param obj - добавляемый объект
     */
    fun add(obj: T) {
        objects.add(obj)
        notifyListeners(obj, INSERT)
    }

    /**
     * Удалить элемент из списка, если он есть
     *
     * @param obj - элемент, который собираемся удалить
     */
    fun delete(obj: T) {
        val index = objects.indexOfFirst { it.id == obj.id }
        if (index != -1) { // Объект по айдишнику найден
            objects.removeAt(index)
            notifyListeners(obj, REMOVE)
        }
    }

    /**
     * Получить очередной незанятый идентификатор
     *
     * @return 1, если список объектов пуст, иначе максимальный идентификатор + 1
     */
    fun getNextId(): Long {
        val maxIdObject = objects.maxByOrNull { it.id }
        if (maxIdObject != null) {
            return maxIdObject.id + 1
        }
        return 1L
    }

    /**
     * Добавить новый слушатель
     *
     * @param listener - слушатель действия
     */
    fun addListeners(listeners: List<DataSourceListener<T>>) {
        listeners.forEach { this.listeners.add(it) }
    }

    /**
     * Переопределить, если требуется инициализировать objects
     * по умолчанию
     */
    open fun loadObjects() {}

    /**
     * Переопределить, если требуется инициализировать список
     * слушателей по умолчанию
     */
    protected open fun initializeListeners() {}

    private fun notifyListeners(obj: T, action: DataSourceAction) {
        listeners.filter { it.action == action }.forEach { it.perform(obj) }
    }
}