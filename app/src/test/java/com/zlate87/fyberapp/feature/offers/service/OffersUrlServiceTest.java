package com.zlate87.fyberapp.feature.offers.service;

import com.zlate87.fyberapp.BuildConfig;
import com.zlate87.fyberapp.feature.offers.model.OfferParameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Test class for OffersUrlService.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class OffersUrlServiceTest {

	@Test
	public void shouldGetOffersServiceUrl() {
		// given
		String validUrl = "http://api.fyber.com/feed/v1/offers.json?appid=2070&format=json&google_ad_id=" +
						"&google_ad_id_limited_tracking_enabled=false&ip=109.235.143.113&locale=de&offer_types=112" +
						"&os_version=5.1&pub0=test&timestamp=1449398607&uid=spiderman" +
						"&hashkey=85ef6429ec2d661f8cd496b3af71357713700e71";
		OfferParameters offerParameters = new OfferParameters();
		offerParameters.setUid("spiderman");
		offerParameters.setAppid("2070");
		offerParameters.setApiKey("1c915e3b5d42d05136185030892fbb846c278927");
		offerParameters.setPub0("test");
		offerParameters.setFormat("json");
		offerParameters.setLocale("de");
		offerParameters.setOsVersion("5.1");
		offerParameters.setTimestamp("1449398607");
		offerParameters.setGoogleAddId("");
		offerParameters.setGoogleAdIdLimitedTrackingEnabled("false");
		offerParameters.setIp("109.235.143.113");
		offerParameters.setOfferTypes("112");
		OffersUrlService offersUrlService = new OffersUrlService();

		// when
		String offersServiceUrl = offersUrlService.getOffersServiceUrl(offerParameters);

		// then
		assertThat(offersServiceUrl, is(validUrl));
	}

}