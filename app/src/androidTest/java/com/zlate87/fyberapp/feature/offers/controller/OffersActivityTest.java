package com.zlate87.fyberapp.feature.offers.controller;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.ImageView;

import com.koushikdutta.async.future.FutureCallback;
import com.zlate87.fyberapp.R;
import com.zlate87.fyberapp.base.App;
import com.zlate87.fyberapp.feature.DaggerTestComponent;
import com.zlate87.fyberapp.feature.MockOffersModule;
import com.zlate87.fyberapp.feature.TestComponent;
import com.zlate87.fyberapp.feature.offers.model.Offer;
import com.zlate87.fyberapp.feature.offers.model.OfferParameters;
import com.zlate87.fyberapp.feature.offers.service.OffersService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.zlate87.fyberapp.feature.CustomMatchers.viewAtPositionInRecyclerView;
import static com.zlate87.fyberapp.feature.CustomMatchers.withDrawable;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doAnswer;

/**
 * Test class for OffersActivity.
 */
@RunWith(AndroidJUnit4.class)

@LargeTest
public class OffersActivityTest {

	@Inject
	protected OffersService offersService;

	@Rule
	public ActivityTestRule<OffersActivity> rule = new ActivityTestRule<OffersActivity>(OffersActivity.class,
					true, false) {
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

	@Before
	public void setUp() {
		Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
		App app = (App) instrumentation.getTargetContext().getApplicationContext();
		MockOffersModule mockOffersModule = new MockOffersModule();
		TestComponent component = DaggerTestComponent.builder().mockOffersModule(mockOffersModule).build();
		app.setComponent(component);
		component.inject(this);
	}

	@Test
	public void userWantsToSeeEmptyList() {
		final ArrayList<Offer> offers = new ArrayList<>();
		mockServiceToReturnOffers(offers);
		rule.launchActivity(new Intent());

		// step 1 - check that the no offers label is displayed
		onView(withId(R.id.noOffersTextView)).check(matches(isDisplayed()));
	}

	@Test
	public void userWantsToSeeTheListOfOffers() {
		final ArrayList<Offer> offers = prepareOffers();
		mockServiceToReturnOffers(offers);
		mockServiceToSetImageInImageView();
		rule.launchActivity(new Intent());

		// step 1 - check that the list of elements is displayed
		onView(withId(R.id.offersRecyclerView)).check(matches(isDisplayed()));

		// step 2 - check few items and confirm that the thumbnail, title. teaser and payout are correct
		assertRecyclerViewElement(0, "Title 0", "Teaser 0", "Payout: 0");
		assertRecyclerViewElement(1, "Title 1", "Teaser 1", "Payout: 1");
	}

	private void assertRecyclerViewElement(int position, String title, String teaser, String payout) {
		onView(viewAtPositionInRecyclerView(R.id.offersRecyclerView, position, R.id.title))
						.check(matches(withText(title)));
		onView(viewAtPositionInRecyclerView(R.id.offersRecyclerView, position, R.id.teaser))
						.check(matches(withText(teaser)));
		onView(viewAtPositionInRecyclerView(R.id.offersRecyclerView, position, R.id.payout))
						.check(matches(withText(payout)));
		onView(viewAtPositionInRecyclerView(R.id.offersRecyclerView, position, R.id.thumbnail))
						.check(matches(withDrawable(R.mipmap.ic_launcher)));
	}

	@NonNull
	private ArrayList<Offer> prepareOffers() {
		final ArrayList<Offer> offers = new ArrayList<>();
		for (int i = 0; i < 30; i++) {
			Offer offer = new Offer();
			offer.setTitle("Title " + i);
			offer.setPayout(String.valueOf(i));
			offer.setTeaser("Teaser " + i);
			offer.setThumbnail("http://someUrl.com/image.png");
			offers.add(offer);
		}
		return offers;
	}

	private void mockServiceToReturnOffers(final ArrayList<Offer> offers) {
		Answer<Void> voidAnswer = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				FutureCallback<List<Offer>> futureCallback = (FutureCallback<List<Offer>>) args[1];
				futureCallback.onCompleted(null, offers);
				return null;
			}
		};
		doAnswer(voidAnswer).when(offersService).getOffersForParameters(any(OfferParameters.class),
						any(FutureCallback.class), any(Context.class));
	}

	private void mockServiceToSetImageInImageView() {
		Answer<Void> voidAnswer = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				ImageView imageView = (ImageView) args[0];
				imageView.setImageResource(R.mipmap.ic_launcher);
				return null;
			}
		};
		doAnswer(voidAnswer).when(offersService).loadImageFromUrlIntoImageView(any(ImageView.class), any(String.class),
						anyInt(), anyInt());
	}


}