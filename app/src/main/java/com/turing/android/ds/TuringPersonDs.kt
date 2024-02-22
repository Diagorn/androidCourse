package com.turing.android.ds

import com.turing.android.dto.TuringPerson
import com.turing.android.utils.getTestTuringPersons


/**
 * Датасорс активистов Тьюринга
 *
 * @author Diagorn
 */
class TuringPersonDs: AbstractDataSource<TuringPerson>() {

    override fun loadObjects() {
        if (this.objects.isEmpty()) {
            getTestTuringPersons().forEach { this.objects.add(it) }
        }
    }
}