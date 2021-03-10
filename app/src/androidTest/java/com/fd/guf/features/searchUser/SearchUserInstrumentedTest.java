package com.fd.guf.features.searchUser;

import android.os.Handler;
import android.os.Looper;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.fd.guf.BaseInstrumentedTest;
import com.fd.guf.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.fd.guf.ExtraAssertions.isGone;
import static com.fd.guf.ExtraAssertions.isVisible;


public class SearchUserInstrumentedTest extends BaseInstrumentedTest {

    @Rule
    public ActivityScenarioRule<SearchUsersActivity> activityRule
            = new ActivityScenarioRule<>(SearchUsersActivity.class);

    @Test
    public void titleTextTest() {
        onView(withId(R.id.tvTitle)).check(matches(withText(R.string.app_name)));
    }

    @Test
    public void listTest() {
        onView(withId(R.id.etSearch))
                .perform(typeText("firdaus"), closeSoftKeyboard());
        sleep();
        onView(withId(R.id.rvUser)).check(isVisible());
        onView(withId(R.id.lytError)).check(isGone());
    }

    @Test
    public void emptyTest() {
        onView(withId(R.id.etSearch))
                .perform(typeText("impossible user 123"), closeSoftKeyboard());
        sleep();
        onView(withId(R.id.rvUser)).check(isGone());
        onView(withId(R.id.lytError)).check(isVisible());
    }
}