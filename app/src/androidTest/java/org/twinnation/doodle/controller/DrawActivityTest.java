package org.twinnation.doodle.controller;


import android.graphics.Color;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.twinnation.doodle.R;
import org.twinnation.doodle.view.CanvasView;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DrawActivityTest {

    @Rule
    public ActivityTestRule<DrawActivity> mActivityTestRule = new ActivityTestRule<>(DrawActivity.class);


    @Test
    public void colorPickerTest() {
        CanvasView canvasView = mActivityTestRule.getActivity().getCanvasView();
        assertThat("By default, the brush color should be black.",
                canvasView.getCurrentColor(), is(Color.BLACK));


        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.setColor), withContentDescription("Pick a color"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.color_green), isDisplayed()));
        appCompatImageView.perform(click());

        assertThat("After picking the color green, the color of the brush should be green",
                canvasView.getCurrentColor(), is(Color.GREEN));
    }


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
