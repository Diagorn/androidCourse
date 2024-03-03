package com.turing.android.utils

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

inline fun <reified T: Parcelable> Bundle?.getParcelableObj(key: String): T? = this?.run {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getParcelable(key) as? T
    }
}