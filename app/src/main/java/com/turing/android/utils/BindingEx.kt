package com.turing.android.utils

import com.turing.android.databinding.ActivityAddPersonBinding

fun ActivityAddPersonBinding.isInvalid() =
    !this.studentRadioButton.isChecked && !this.curatorRadioButton.isChecked
            || this.ageEditText.text.isNullOrEmpty()
            || this.avatarUrlEditText.text.isNullOrEmpty()
            || this.nameEditText.text.isNullOrEmpty()
            || this.surnameEditText.text.isNullOrEmpty()