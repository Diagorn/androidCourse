package com.turing.android

import android.app.Application
import com.turing.android.ds.TuringPersonDs

/**
 * Класс приложения, в котором хранится глобальный стейт
 *
 * @param turingPersonDs - датасорс активистов тьюринга
 *
 * @author Diagorn
 */
class PersonsApp(val turingPersonDs: TuringPersonDs = TuringPersonDs()): Application()