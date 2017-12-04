package org.twinnation.doodle.controller;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.twinnation.doodle.R;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SplashActivityTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);


    @Test
    public void splashActivityTest() {
        assertThat("The current window should be SplashActivity", mActivityTestRule.getActivity().hasWindowFocus(), is(true));

        ViewInteraction appCompatTextView = onView(allOf(withId(R.id.drawBtn), withText("Draw"), isDisplayed()));
        appCompatTextView.perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat("The current window should be DrawActivity", mActivityTestRule.getActivity().hasWindowFocus(), is(false));
    }

}
