package com.zlate87.fyberapp.feature.offers.callback;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;
import com.zlate87.fyberapp.BuildConfig;
import com.zlate87.fyberapp.feature.offers.model.Offer;
import com.zlate87.fyberapp.feature.offers.service.OffersService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test class for HttpStackOffersResponseCallback
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class IonWrapperOffersResponseCallbackTest {

	private HttpStackOffersResponseCallback httpStackOffersResponseCallback;
	private OffersService offersService;

	@Before
	public void setUp() {
		offersService = mock(OffersService.class);
	}

	@Test
	public void shouldCallHandleOffersResponseWhenOnCompleteIsCalled() {
		// given
		FutureCallback<List<Offer>> futureCallback= mock(FutureCallback.class);
		String apiKey = "565269899565698952659899959";
		httpStackOffersResponseCallback = new HttpStackOffersResponseCallback(offersService, futureCallback, apiKey);
		Exception exception = mock(Exception.class);
		Response<String> result = mock(Response.class);

		// when
		httpStackOffersResponseCallback.onCompleted(exception, result);

		// then
		verify(offersService, times(1)).handleOffersResponse(exception, result, futureCallback, apiKey);
	}

}