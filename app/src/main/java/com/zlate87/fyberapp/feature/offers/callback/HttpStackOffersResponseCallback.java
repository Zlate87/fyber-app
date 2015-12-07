package com.zlate87.fyberapp.feature.offers.callback;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;
import com.zlate87.fyberapp.feature.offers.model.Offer;
import com.zlate87.fyberapp.feature.offers.service.OffersService;

import java.util.List;

/**
 * Callback class responsible for the offers result from the IonWrapper
 */
public class HttpStackOffersResponseCallback implements FutureCallback<Response<String>> {

	private final String apiKey;
	private FutureCallback<List<Offer>> futureCallback;
	private OffersService offersService;

	/**
	 * Constructor.
	 * @param offersService the offline service
	 * @param futureCallback the feature callback
	 * @param apiKey the api key
	 */
	public HttpStackOffersResponseCallback(OffersService offersService, FutureCallback<List<Offer>> futureCallback,
																				 String apiKey) {
		this.offersService = offersService;
		this.futureCallback = futureCallback;
		this.apiKey = apiKey;
	}

	@Override
	public void onCompleted(Exception exception, Response<String> result) {
		offersService.handleOffersResponse(exception, result, futureCallback, apiKey);
	}
}
