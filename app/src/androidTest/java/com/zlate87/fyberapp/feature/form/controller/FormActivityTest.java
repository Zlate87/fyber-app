package com.zlate87.fyberapp.feature.form.controller;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.zlate87.fyberapp.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Test class for FormActivity.
 */
@RunWith(AndroidJUnit4.class)
public class FormActivityTest extends ActivityInstrumentationTestCase2<FormActivity> {

	private FormActivity formActivity;

	public FormActivityTest() {
		super(FormActivity.class);
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		injectInstrumentation(InstrumentationRegistry.getInstrumentation());
		formActivity = getActivity();
	}

	@Test
	public void shouldSetupTheActivityViews() {
		// given

		// when

		// then
		onView(withId(R.id.appid)).check(matches(withText("")));
		onView(withId(R.id.uid)).check(matches(withText("")));
		onView(withId(R.id.apiKey)).check(matches(withText("")));
		onView(withId(R.id.pub0)).check(matches(withText("")));
		onView(withId(R.id.insertSampleData)).check(matches(withText("Insert Sample Data")));
		onView(withId(R.id.viewOffers)).check(matches(withText("View Offers")));
	}

	@Test
	public void shouldInsertSampleData() {
		// given

		// when
		onView(withId(R.id.insertSampleData)).perform(click());

		// then
		onView(withId(R.id.appid)).check(matches(withText("2070")));
		onView(withId(R.id.uid)).check(matches(withText("spiderman")));
		onView(withId(R.id.apiKey)).check(matches(withText("1c915e3b5d42d05136185030892fbb846c278927")));
		onView(withId(R.id.pub0)).check(matches(withText("test")));
	}

	@Test
	public void shouldNavigateToOffersActivity() {
		// given
		onView(withId(R.id.uid)).perform(typeText("spiderman"));
		onView(withId(R.id.apiKey)).perform(typeText("1c915e3b5d42d05136185030892fbb846c278927"));
		onView(withId(R.id.appid)).perform(typeText("2070"));
		onView(withId(R.id.pub0)).perform(typeText("test"));

		// when
		onView(withId(R.id.viewOffers)).perform(click());

		// then
		// navigate to the offers screen where the no offers label is there regardless to if it is visible
		onView(withId(R.id.noOffersTextView)).check(matches(withText("No Offers")));
	}
}