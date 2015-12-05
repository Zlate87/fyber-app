package com.zlate87.fyberapp.feature.offers.callback;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;
import com.zlate87.fyberapp.feature.offers.model.Offer;
import com.zlate87.fyberapp.feature.offers.service.OffersService;

import java.util.List;

/**
 * Callback class responsible for the offers result from the HttpStack
 */
public class HttpStackOffersResponseCallback implements FutureCallback<Response<String>> {

	private final String apiKey;
	private FutureCallback<List<Offer>> futureCallback;

	public HttpStackOffersResponseCallback(FutureCallback<List<Offer>> futureCallback, String apiKey) {
		this.futureCallback = futureCallback;
		this.apiKey = apiKey;
	}

	@Override
	public void onCompleted(Exception e, Response<String> result) {
		OffersService offersService = new OffersService(null);
		offersService.handleOffersResponse(e, result, futureCallback, apiKey);
	}
}
