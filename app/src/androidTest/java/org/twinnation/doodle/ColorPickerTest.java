package org.twinnation.doodle;


import android.graphics.Color;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.twinnation.doodle.view.CanvasView;
import org.twinnation.doodle.activity.DrawActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ColorPickerTest {

    @Rule
    public ActivityTestRule<DrawActivity> mActivityTestRule = new ActivityTestRule<>(DrawActivity.class);


    @Test
    public void colorPickerTest() {
        CanvasView canvasView = mActivityTestRule.getActivity().getCanvasView();
        assertThat("By default, the brush color should be black.",
                canvasView.getCurrentColor(), is(Color.BLACK));

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        ViewInteraction appCompatTextView = onView(allOf(withId(R.id.title), withText("Pick a color"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatImageView = onView(allOf(withId(R.id.color_green), isDisplayed()));
        appCompatImageView.perform(click());

        assertThat("After picking the color green, the color of the brush should be green",
                canvasView.getCurrentColor(), is(Color.GREEN));
    }

}
