package com.zlate87.fyberapp.feature.offers.callback;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;
import com.zlate87.fyberapp.feature.offers.model.Offer;
import com.zlate87.fyberapp.feature.offers.service.OffersService;

import java.util.List;

/**
 * Created by Zlatko on 12/5/2015.
 */
public class JsonObjectWithResponseCallback implements FutureCallback<Response<String>> {

	private final String apiKey;
	private FutureCallback<List<Offer>> futureCallback;

	public JsonObjectWithResponseCallback(FutureCallback<List<Offer>> futureCallback, String apiKey) {
		this.futureCallback = futureCallback;
		this.apiKey = apiKey;
	}

	@Override
	public void onCompleted(Exception e, Response<String> result) {
		OffersService offersService = new OffersService(null);
		 offersService.handleOffersResponse(e, result, futureCallback, apiKey);
	}
}
