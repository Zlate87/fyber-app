package com.zlate87.fyberapp.feature.offers.service;

import com.koushikdutta.async.http.Headers;
import com.koushikdutta.ion.HeadersResponse;
import com.koushikdutta.ion.Response;
import com.zlate87.fyberapp.BuildConfig;
import com.zlate87.fyberapp.feature.offers.TestHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for OffersSignatureService.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class OffersSignatureServiceTest {

	private TestHelper testHelper = new TestHelper();

	private OffersSignatureService offersSignatureService;
	private Response<String> response;
	private Headers headers;

	@Before
	public void setUp() {
		offersSignatureService = new OffersSignatureService();
		response = mock(Response.class);
		HeadersResponse headersResponse = mock(HeadersResponse.class);
		headers = mock(Headers.class);

		when(response.getHeaders()).thenReturn(headersResponse);
		when(headersResponse.getHeaders()).thenReturn(headers);
	}

	@Test
	public void shouldCheckIfSignatureIsValid() {
		// given
		String body = testHelper.getTextContentFromResourcesFile("signedJsonOffers.json");
		String signature = "04d47f86efd5a179312b050e5b4b49f41b5b55be";
		String apyKey = "1c915e3b5d42d05136185030892fbb846c278927";

		when(response.getResult()).thenReturn(body);
		when(headers.get("X-Sponsorpay-Response-Signature")).thenReturn(signature);

		// when
		boolean responseSignatureValid = offersSignatureService.isResponseSignatureValid(response, apyKey);

		// then
		assertThat(responseSignatureValid, is(true));
	}

	@Test
	public void shouldDetectInvalidSignature() {
		// given
		String body = testHelper.getTextContentFromResourcesFile("signedJsonOffers.json");
		String signature = "23a543g32b3435344ec5623424d342424";
		String apyKey = "12345678e7a65c4323456f7b8d98765432";

		when(response.getResult()).thenReturn(body);
		when(headers.get("X-Sponsorpay-Response-Signature")).thenReturn(signature);

		// when
		boolean responseSignatureValid = offersSignatureService.isResponseSignatureValid(response, apyKey);

		// then
		assertThat(responseSignatureValid, is(false));
	}

}