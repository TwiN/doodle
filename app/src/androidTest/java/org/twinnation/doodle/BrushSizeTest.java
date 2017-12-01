package org.twinnation.doodle;


import android.graphics.Color;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BrushSizeTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void maxBrushSizeTest() {
        CanvasView canvasView = mActivityTestRule.getActivity().getCanvasView();
        ViewInteraction appCompatButton = onView(allOf(withId(R.id.plusSize), withText("+"),
                        withParent(withId(R.id.bottomBar)), isDisplayed()));
        int x = 15;
        while (x --> 0) {
            appCompatButton.perform(click());
        }
        assertThat("The maximum brush size should be " + CanvasView.MAX_BRUSH_SIZE,
                canvasView.getCurrentSize(), is(CanvasView.MAX_BRUSH_SIZE));
    }


    @Test
    public void minBrushSizeTest() {
        CanvasView canvasView = mActivityTestRule.getActivity().getCanvasView();
        ViewInteraction appCompatButton = onView(allOf(withId(R.id.minusSize), withText("-"),
                withParent(withId(R.id.bottomBar)), isDisplayed()));
        int x = 15;
        while (x --> 0) {
            appCompatButton.perform(click());
        }
        assertThat("The maximum brush size should be " + CanvasView.MIN_BRUSH_SIZE,
                canvasView.getCurrentSize(), is(CanvasView.MIN_BRUSH_SIZE));
    }

}
