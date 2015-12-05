package com.zlate87.fyberapp.feature.offers.service;

import android.content.Context;
import android.util.Log;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;
import com.zlate87.fyberapp.base.HttpStack;
import com.zlate87.fyberapp.feature.offers.callback.HttpStackOffersResponseCallback;
import com.zlate87.fyberapp.feature.offers.exception.SignatureNotValidException;
import com.zlate87.fyberapp.feature.offers.model.Offer;
import com.zlate87.fyberapp.feature.offers.model.OfferParameters;

import java.util.List;

/**
 * Service class responsible for providing the needed offer services.
 */
// TODO add JUnit tests
public class OffersService {

	private static final String LOG_TAG = OffersService.class.getSimpleName();

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
		Log.d(LOG_TAG, String.format("getOffersForParameters called with parameters: apiKey=[%s], appid=[%s], " +
										"format=[%s], googleAddId=[%s], googleAdIdLimitTrackingEnabled=[%s], ip=[%s], locale=[%s], " +
										"osVersion=[%s], pub0=[%s], timeStamp=[%s], uid=[%s], ip=[%s]",
						offerParameters.getApiKey(),
						offerParameters.getAppid(),
						offerParameters.getFormat(),
						offerParameters.getGoogleAddId(),
						offerParameters.getGoogleAdIdLimitedTrackingEnabled(),
						offerParameters.getIp(),
						offerParameters.getLocale(),
						offerParameters.getOsVersion(),
						offerParameters.getPub0(),
						offerParameters.getTimestamp(),
						offerParameters.getUid(),
						offerParameters.getIp()));

		// prepare the url
		OffersUrlService offersUrlService = new OffersUrlService();
		String offersServiceUrl = offersUrlService.getOffersServiceUrl(offerParameters);

		// send the request to the backend, once the request is it will be precessed by the callback
		String apiKey = offerParameters.getApiKey();
		HttpStackOffersResponseCallback callback = new HttpStackOffersResponseCallback(futureCallback, apiKey);
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
			Log.d(LOG_TAG, String.format("handleOffersResponse called with exception of type [%s]", exception.getClass()));
			futureCallback.onCompleted(exception, null);
			return;
		}

		// check if the signature is valid
		OffersSignatureService offersSignatureService = new OffersSignatureService();
		boolean signatureValid = offersSignatureService.isResponseSignatureValid(result, apiKey);
		if (!signatureValid) {
			Log.d(LOG_TAG, "handleOffersResponse called but the signature was invalid");
			SignatureNotValidException signatureNotValidException = new SignatureNotValidException();
			futureCallback.onCompleted(signatureNotValidException, null);
			return;
		}

		// get the offers from the response and send them to the callback
		OffersJsonService offersJsonService = new OffersJsonService();
		String body = result.getResult();
		Log.d(LOG_TAG, String.format("handleOffersResponse called with valid signature and body [%s]", body));
		List<Offer> offers = offersJsonService.convertJsonToObjects(body);
		futureCallback.onCompleted(null, offers);
	}
}