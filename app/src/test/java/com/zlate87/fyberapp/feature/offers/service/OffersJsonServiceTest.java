package com.zlate87.fyberapp.feature.offers.service;

import com.zlate87.fyberapp.BuildConfig;
import com.zlate87.fyberapp.feature.offers.TestHelper;
import com.zlate87.fyberapp.feature.offers.model.Offer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Test class for OffersJsonService.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class OffersJsonServiceTest {

	private TestHelper testHelper = new TestHelper();

	private OffersJsonService offersJsonService;

	@Before
	public void setUp() {
		offersJsonService = new OffersJsonService();
	}

	@Test
	public void shouldConvertValidJsonToListOfOffers() {
		// given
		String json = testHelper.getTextContentFromResourcesFile("validJsonOffers.json");

		// when
		List<Offer> offers = offersJsonService.convertJsonToOffers(json);

		// then
		assertThat(offers.size(), is(30));

		Offer offer0 = offers.get(0);
		assertThat(offer0.getTitle(), is("Filesfetcher.Upoad & display ebooks"));
		assertThat(offer0.getTeaser(), is("Register using valid personal data and credit card. Start your free 7 days"));
		assertThat(offer0.getThumbnail(), is("http://cdn2.sponsorpay.com/assets/58016/1444245252_book_square_175.png"));
		assertThat(offer0.getPayout(), is("696"));

		Offer offer1 = offers.get(1);
		assertThat(offer1.getTitle(), is("Epic Fruits - Erreiche Level 7"));
		assertThat(offer1.getTeaser(), is("Klicke auf \"Spielen\" und erreiche Level 7!"));
		assertThat(offer1.getThumbnail(), is("http://cdn1.sponsorpay.com/assets/59977/Epic_250x250_icon_square_175.jpg"));
		assertThat(offer1.getPayout(), is("17"));

		Offer offer29 = offers.get(29);
		assertThat(offer29.getTitle(), is("Dads Long Legs- Erreiche 27 Punkte!"));
		assertThat(offer29.getTeaser(), is("Klicke auf \"Spielen\" und erreiche 27 Punkte!"));
		assertThat(offer29.getThumbnail(), is("http://cdn4.sponsorpay.com/assets/59981/DLL_icons_250x0250_square_175.jpg"));
		assertThat(offer29.getPayout(), is("26"));
	}

	@Test(expected = RuntimeException.class)
	public void shouldThrowExceptionForInvalidJson() {
		// given
		String json = testHelper.getTextContentFromResourcesFile("invalidJsonOffers.json");

		// when
		offersJsonService.convertJsonToOffers(json);

		// then exception expected
	}

	@Test
	public void shouldHandleJsonWithNoOffers() {
		// given
		String json = testHelper.getTextContentFromResourcesFile("emptyJsonOffers.json");

		// when
		List<Offer> offers = offersJsonService.convertJsonToOffers(json);

		// then
		assertThat(offers.size(), is(0));
	}


}