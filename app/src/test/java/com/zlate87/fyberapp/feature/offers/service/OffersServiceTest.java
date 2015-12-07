package com.zlate87.fyberapp.feature.offers.service;

import android.content.Context;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;
import com.zlate87.fyberapp.BuildConfig;
import com.zlate87.fyberapp.base.IonWrapper;
import com.zlate87.fyberapp.feature.offers.callback.HttpStackOffersResponseCallback;
import com.zlate87.fyberapp.feature.offers.exception.SignatureNotValidException;
import com.zlate87.fyberapp.feature.offers.model.Offer;
import com.zlate87.fyberapp.feature.offers.model.OfferParameters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for OffersService.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class OffersServiceTest {

	private OffersService offersService;
	private IonWrapper ionWrapper;
	private OffersUrlService offersUrlService;
	private OffersJsonService offersJsonService;
	private OffersSignatureService offersSignatureService;
	private Context context;

	@Before
	public void setUp() {
		offersService = new OffersService();

		// mock the services
		ionWrapper = mock(IonWrapper.class);
		offersUrlService = mock(OffersUrlService.class);
		offersJsonService = mock(OffersJsonService.class);
		offersSignatureService = mock(OffersSignatureService.class);
		context = mock(Context.class);
		ReflectionTestUtils.setField(offersService, "ionWrapper", ionWrapper);
		ReflectionTestUtils.setField(offersService, "offersUrlService", offersUrlService);
		ReflectionTestUtils.setField(offersService, "offersJsonService", offersJsonService);
		ReflectionTestUtils.setField(offersService, "offersSignatureService", offersSignatureService);
	}

	@Test
	public void shouldGetOffersForParameters() {
		// given
		OfferParameters parameters = new OfferParameters();
		FutureCallback<List<Offer>> callback = mock(FutureCallback.class);
		String url = "http://www.someUrl.com";
		when(offersUrlService.getOffersServiceUrl(parameters)).thenReturn(url);

		// when
		offersService.getOffersForParameters(parameters, callback, context);

		// then
		verify(offersUrlService, times(1)).getOffersServiceUrl(parameters);
		verify(ionWrapper, times(1)).getStringObjectWithResponse(eq(url), any(HttpStackOffersResponseCallback.class),
						eq(context));
	}

	@Test
	public void shouldHandleOffersResponseForValidResponse() {
		// given
		Response<String> response = mock(Response.class);
		FutureCallback<List<Offer>> futureCallback = mock(FutureCallback.class);
		String apikey = "789632478632125874";
		when(offersSignatureService.isResponseSignatureValid(response, apikey)).thenReturn(true);
		String result = "some json result";
		when(response.getResult()).thenReturn(result);
		List<Offer> offers = new ArrayList<>();
		when(offersJsonService.convertJsonToOffers(result)).thenReturn(offers);

		// when
		offersService.handleOffersResponse(null, response, futureCallback, apikey);

		// then
		verify(futureCallback, times(1)).onCompleted(null, offers);
	}

	@Test
	public void shouldHandleOffersResponseFoResponseWithInvalidKey() {
		// given
		Response<String> response = mock(Response.class);
		FutureCallback<List<Offer>> futureCallback = mock(FutureCallback.class);
		String apikey = "789632478632125874";
		when(offersSignatureService.isResponseSignatureValid(response, apikey)).thenReturn(false);
		String result = "some json result";
		when(response.getResult()).thenReturn(result);

		// when
		offersService.handleOffersResponse(null, response, futureCallback, apikey);

		// then
		verify(futureCallback, times(1)).onCompleted(any(SignatureNotValidException.class), isNull(List.class));
	}

	@Test
	public void shouldHandleOffersResponseForExceptions() {
		// given
		FutureCallback<List<Offer>> futureCallback = mock(FutureCallback.class);
		String apikey = "789632478632125874";
		Exception exception = new Exception();

		// when
		offersService.handleOffersResponse(exception, null, futureCallback, apikey);

		// then
		verify(futureCallback, times(1)).onCompleted(exception, null);
	}
}