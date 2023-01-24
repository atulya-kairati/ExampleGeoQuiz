package com.atulya.geoquiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = ">>> QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"
const val POINT_KEY = "POINT_KEY"
const val CHEAT_TOKEN_KEY = "CHEAT_TOKEN_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    init {
        Log.d(TAG, "QuizViewModel: Created")
    }

    private val questions = listOf(
        Question(R.string.question_asia, true),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
    )

    val questionCount = questions.size

    var currentQuestion: Int
        get() = savedStateHandle[CURRENT_INDEX_KEY] ?: 0
        private set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    var isCheater: Boolean
        get() = savedStateHandle[IS_CHEATER_KEY] ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    var point: Int
        get() = savedStateHandle[POINT_KEY] ?: 0
        set(value) = savedStateHandle.set(POINT_KEY, value)

    var cheatToken: Int
    get() = savedStateHandle[CHEAT_TOKEN_KEY] ?: 3
    set(value) = savedStateHandle.set(CHEAT_TOKEN_KEY, value)

    val currentQuestionText: Int
        get() = questions[currentQuestion].questionTextResId

    val currentQuestionAnswer: Boolean
        get() = questions[currentQuestion].answer


    fun next() {
//        Log.d(TAG, "next: ", Exception())
        currentQuestion = (currentQuestion + 1) % questions.size
    }

    fun previous() {
        currentQuestion = if (currentQuestion == 0) questions.size - 1
        else currentQuestion - 1
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: QuizViewModel: about to be Destroyed")
    }
}
