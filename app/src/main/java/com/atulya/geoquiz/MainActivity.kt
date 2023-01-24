package com.atulya.geoquiz

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.atulya.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


private const val TAG = ">>> MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            quizViewModel.isCheater = it.data?.getBooleanExtra(
                EXTRA_ANSWER_IS_SHOWN,
                false
            ) ?: false
            Log.d(TAG, "quizViewModel.isCheater: ${quizViewModel.isCheater}")

            // If cheated then reduce cheatToken
            checkCheats()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        showQuestion()
        checkCheats()

        binding.buttonTrue.setOnClickListener {
            checkAnswer(true)
        }

        binding.buttonFalse.setOnClickListener {
            checkAnswer(false)
        }

        binding.cheatButton.setOnClickListener {
            val answer = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(applicationContext, answer)
            cheatActivityLauncher.launch(intent)
        }

        binding.buttonNext.setOnClickListener {
            quizViewModel.next()
            showQuestion()
        }

        binding.buttonPrevious.setOnClickListener {
            quizViewModel.previous()
            showQuestion()
        }

        Log.d(TAG, "onCreate: $quizViewModel")
        Log.d(TAG, "onCreate: " + Build.VERSION.SDK_INT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            blurCheatButton()
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun blurCheatButton(){
        val effect = RenderEffect.createBlurEffect(10f, 10f,Shader.TileMode.CLAMP)
        binding.cheatButton.setRenderEffect(effect)
    }

    private fun showQuestion() {
        enableButtons()

        val qid = quizViewModel.currentQuestionText
        binding.textViewQuestion.setText(qid)
    }

    private fun checkAnswer(answer: Boolean) {

        val correctAnswer = quizViewModel.currentQuestionAnswer



        val message: Int = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            answer == correctAnswer -> R.string.correct
            else -> R.string.incorrect
        }

        // Remove the cheater status for next question
        quizViewModel.isCheater = false

        if (answer == correctAnswer) {
            quizViewModel.point++
        }

        disableButtons()

        // Takes care of the ending of each round of quiz
        if (quizViewModel.currentQuestion + 1 == quizViewModel.questionCount) {
            Toast.makeText(
                applicationContext,
                "Accuracy: " + (quizViewModel.point * 100 / quizViewModel.questionCount),
                Toast.LENGTH_SHORT
            ).show()

            quizViewModel.point = 0
            quizViewModel.cheatToken = 3
        }

        Snackbar.make(binding.constraintLayout, message, Snackbar.LENGTH_SHORT).show()
        checkCheats()
    }

    private fun checkCheats(){
        if(quizViewModel.isCheater){
            quizViewModel.cheatToken--
        }

        "Cheats Left: ${quizViewModel.cheatToken}".also{binding.cheatTokenText.text = it}
        binding.cheatButton.isEnabled = quizViewModel.cheatToken > 0

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    private fun disableButtons() {
        binding.buttonTrue.isEnabled = false
        binding.buttonFalse.isEnabled = false
    }

    private fun enableButtons() {
        binding.buttonTrue.isEnabled = true
        binding.buttonFalse.isEnabled = true
    }
}