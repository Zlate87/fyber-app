package com.zlate87.fyberapp.feature.offers.controller;

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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Test class for OffersActivity.
 */
@RunWith(AndroidJUnit4.class)

@LargeTest
public class OffersActivityTest {

	@Rule
	public ActivityTestRule<OffersActivity> rule = new ActivityTestRule<OffersActivity>(OffersActivity.class) {
		@Override
		protected Intent getActivityIntent() {
			Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
			Intent result = new Intent(targetContext, OffersActivity.class);
			result.putExtra(OffersActivity.UID_INTENT_EXTRA_KEY, "spiderman");
			result.putExtra(OffersActivity.API_KEY_INTENT_EXTRA_KEY, "1c915e3b5d42d05136185030892fbb846c278927");
			result.putExtra(OffersActivity.APPID_INTENT_EXTRA_KEY, "2070");
			result.putExtra(OffersActivity.PUB0_INTENT_EXTRA_KEY, "test");
			return result;
		}
	};

	@Test
	public void userWantsToSellTheListOfOffers() {
		// step 1 - check that the list of elements is displayed
		onView(withId(R.id.offersRecyclerView)).check(matches(isDisplayed()));
	}

}