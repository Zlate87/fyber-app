package com.zlate87.fyberapp.feature.form.controller;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.zlate87.fyberapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

/**
 * Test class for FormActivity.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class FormActivityTest {

	@Rule
	public ActivityTestRule<FormActivity> mActivityRule = new ActivityTestRule<FormActivity>(FormActivity.class) {
		@Override
		protected Intent getActivityIntent() {
			Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
			Intent result = new Intent(targetContext, FormActivity.class);
			return result;
		}
	};

	@Test
	public void formScreenShouldBeDisplayedProperly() {
		// step 1 - the screen should have empty text views and buttons
		onView(withId(R.id.appid)).check(matches(withText("")));
		onView(withId(R.id.uid)).check(matches(withText("")));
		onView(withId(R.id.apiKey)).check(matches(withText("")));
		onView(withId(R.id.pub0)).check(matches(withText("")));
		onView(withId(R.id.insertSampleData)).check(matches(withText("Insert Sample Data")));
		onView(withId(R.id.viewOffers)).check(matches(withText("View Offers")));
	}

	@Test
	public void clickingOnTheInsertSampleDateShouldFillTextViews() {
		// step 1 - user clicks on the "Insert Sample Data" button
		onView(withId(R.id.insertSampleData)).perform(click());

		// step 2 - the text views should be filled with data
		onView(withId(R.id.appid)).check(matches(withText("2070")));
		onView(withId(R.id.uid)).check(matches(withText("spiderman")));
		onView(withId(R.id.apiKey)).check(matches(withText("1c915e3b5d42d05136185030892fbb846c278927")));
		onView(withId(R.id.pub0)).check(matches(withText("test")));
	}

	@Test
	public void theUserWantsToNavigateToTheOffersViewScreen() {
		// step 1 - user clicks on the "View Offers" button
		onView(withId(R.id.viewOffers)).perform(click());

		// step 2 - the app navigates to the offers screen and "No Offers" label is displayed
		onView(withId(R.id.noOffersTextView)).check(matches(isDisplayed()));
		onView(withId(R.id.offersRecyclerView)).check(matches(not(isDisplayed())));
	}
}