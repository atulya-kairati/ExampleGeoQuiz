package com.atulya.geoquiz

import androidx.annotation.StringRes

data class Question(@StringRes val questionTextResId: Int, val answer: Boolean)
