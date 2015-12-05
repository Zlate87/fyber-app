package com.zlate87.fyberapp.feature.offers.service;

import android.content.Context;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;
import com.zlate87.fyberapp.base.HttpStack;
import com.zlate87.fyberapp.feature.offers.callback.JsonObjectWithResponseCallback;
import com.zlate87.fyberapp.feature.offers.exception.SignatureNotValidException;
import com.zlate87.fyberapp.feature.offers.model.Offer;
import com.zlate87.fyberapp.feature.offers.model.OfferParameters;

import java.util.List;

/**
 * Service class responsible for providing the needed offer services.
 */
// TODO add JUnit tests
public class OffersService {

	private HttpStack httpStack;

	public OffersService(Context context) {
		httpStack = new HttpStack(context);
	}

	/**
	 * Gets a list of offers for given parameters.
	 *
	 * @param offerParameters the parameters
	 * @param futureCallback  a callback that will get the list of offers if properly received from the server
	 */
	public void getOffersForParameters(OfferParameters offerParameters, FutureCallback<List<Offer>> futureCallback) {
		// prepare the url
		OffersUrlService offersUrlService = new OffersUrlService();
		String offersServiceUrl = offersUrlService.getOffersServiceUrl(offerParameters);

		// send the request to the backend, once the request is it will be precessed by the callback
		String apiKey = offerParameters.getApiKey();
		JsonObjectWithResponseCallback callback = new JsonObjectWithResponseCallback(futureCallback, apiKey);
		httpStack.getStringObjectWithResponse(offersServiceUrl, callback);
	}

	/**
	 * Handles an offers response that contains the data is in JSON format.
	 * The data is converted into objects, signature check is performed and if all is fine,
	 * the date is send to the callback.
	 *
	 * @param exception      exception that occurred in the communication or null
	 * @param result         the result containing the json data
	 * @param futureCallback the callback
	 */
	public void handleOffersResponse(Exception exception, Response<String> result,
																	 FutureCallback<List<Offer>> futureCallback, String apiKey) {
		// check if there is an exception
		if (exception != null) {
			futureCallback.onCompleted(exception, null);
			return;
		}

		// check if the signature is valid
		OffersSignatureService offersSignatureService = new OffersSignatureService();
		boolean signatureValid = offersSignatureService.isResponseSignatureValid(result, apiKey);
		if (!signatureValid) {
			SignatureNotValidException signatureNotValidException = new SignatureNotValidException();
			futureCallback.onCompleted(signatureNotValidException, null);
			return;
		}

		// get the offers from the response and send them to the callback
		OffersJsonService offersJsonService = new OffersJsonService();
		String body = result.getResult();
		List<Offer> offers = offersJsonService.convertJsonToObjects(body);
		futureCallback.onCompleted(null, offers);
	}
}
