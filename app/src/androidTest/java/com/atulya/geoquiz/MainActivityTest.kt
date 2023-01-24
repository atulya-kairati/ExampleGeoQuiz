package com.atulya.geoquiz

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }


    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun showsFirstQuestionOnLaunch() {
        Espresso.onView(
            withId(R.id.textViewQuestion)
        ).check(
            matches(
                withText(R.string.question_asia)
            )
        )
    }

    @Test
    fun showsNextQuestionAfterNextIsPressed() {
        Espresso.onView(withId(R.id.buttonNext)).perform(click())

        Espresso.onView(withId(R.id.textViewQuestion))
            .check(matches(withText(R.string.question_africa)))
    }

    @Test
    fun isAbleToHandleActivityRecreation() {
        Espresso.onView(withId(R.id.buttonNext)).perform(click())

        scenario.recreate()

        Espresso.onView(withId(R.id.textViewQuestion))
            .check(matches(withText(R.string.question_africa)))
    }

}
