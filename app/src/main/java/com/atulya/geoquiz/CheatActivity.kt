package com.atulya.geoquiz

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.atulya.geoquiz.databinding.ActivityCheatBinding


const val EXTRA_ANSWER = "com.atulya.geoquiz.answer"
const val EXTRA_ANSWER_IS_SHOWN = "com.atulya.geoquiz.answer_is_shown"

class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding

    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        "API Level ${Build.VERSION.SDK_INT}".also { binding.apiLevelText.text = it }

        val answer = intent.getBooleanExtra(EXTRA_ANSWER, false)
        val answerTextId = when (answer) {
            true -> R.string.true_label
            false -> R.string.false_label
        }


        if(quizViewModel.isCheater){
            binding.answerTextView.setText(answerTextId)
            setAnswerShown()
        }

        binding.showAnswerButton.setOnClickListener {
            binding.answerTextView.setText(answerTextId)
            setAnswerShown()
        }

    }

    companion object {
        fun newIntent(packageContext: Context, answer: Boolean): Intent = Intent(
            packageContext,
            CheatActivity::class.java
        ).apply {
            putExtra(EXTRA_ANSWER, answer)
        }
    }

    private fun setAnswerShown(){
        quizViewModel.isCheater = true
        val dataIntent = Intent().apply {
            putExtra(EXTRA_ANSWER_IS_SHOWN, true)
        }

        setResult(RESULT_OK, dataIntent)
    }
}