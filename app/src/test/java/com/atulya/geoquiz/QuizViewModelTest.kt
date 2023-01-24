package com.atulya.geoquiz

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.*
import org.junit.Test

class QuizViewModelTest{

    @Test
    fun providesExpectedQuestionText(){
        val ssh = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle = ssh)
        assertEquals(R.string.question_asia, quizViewModel.currentQuestionText)

        quizViewModel.previous()
        assertEquals(R.string.question_oceans, quizViewModel.currentQuestionText)
        assertTrue(quizViewModel.currentQuestionAnswer)
    }

    @Test
    fun wrapsAroundQuestionBank(){
        val ssh = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 5));
        val quizViewModel = QuizViewModel(ssh)
        assertEquals(R.string.question_oceans, quizViewModel.currentQuestionText)

        quizViewModel.next()
        assertEquals(R.string.question_asia, quizViewModel.currentQuestionText)
    }
}