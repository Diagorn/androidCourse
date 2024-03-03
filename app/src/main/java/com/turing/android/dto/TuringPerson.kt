package com.turing.android.dto

import android.os.Parcelable
import com.turing.android.ds.HasId
import kotlinx.parcelize.Parcelize
import java.io.Serializable

/**
 * Активист Тьюринга
 *
 * @param id - идентификатор
 * @param name - имя
 * @param surname - фамилия
 * @param avatarUrl - ссылка на аватар
 * @param age - возраст
 * @param isStudent - ялвяется ли сейчас студентом
 *
 * @author Diagorn
 */
@Parcelize
data class TuringPerson(
    var identifyer: Long,
    val name: String,
    val surname: String,
    val avatarUrl: String,
    val age: Int,
    val isStudent: Boolean
): Parcelable, HasId(identifyer)