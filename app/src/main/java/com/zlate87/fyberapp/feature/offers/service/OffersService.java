package com.zlate87.fyberapp.feature.offers.service;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;
import com.zlate87.fyberapp.base.IonWrapper;
import com.zlate87.fyberapp.feature.offers.callback.HttpStackOffersResponseCallback;
import com.zlate87.fyberapp.feature.offers.exception.SignatureNotValidException;
import com.zlate87.fyberapp.feature.offers.model.Offer;
import com.zlate87.fyberapp.feature.offers.model.OfferParameters;

import java.util.List;

import javax.inject.Singleton;

/**
 * Service class responsible for providing the needed offer services.
 */
@Singleton
public class OffersService {

	private static final String LOG_TAG = OffersService.class.getSimpleName();

	private IonWrapper ionWrapper;
	private OffersUrlService offersUrlService;
	private OffersJsonService offersJsonService;
	private OffersSignatureService offersSignatureService;

	public OffersService() {
		ionWrapper = new IonWrapper();
		offersUrlService = new OffersUrlService();
		offersJsonService = new OffersJsonService();
		offersSignatureService = new OffersSignatureService();
	}

	/**
	 * Gets a list of offers for given parameters.
	 *
	 * @param offerParameters the parameters
	 * @param futureCallback  a callback that will get the list of offers if properly received from the server
	 */
	public void getOffersForParameters(OfferParameters offerParameters, FutureCallback<List<Offer>> futureCallback,
																		 Context context) {
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
		String offersServiceUrl = offersUrlService.getOffersServiceUrl(offerParameters);

		// send the request to the backend, once the request is it will be precessed by the callback
		String apiKey = offerParameters.getApiKey();
		HttpStackOffersResponseCallback callback = new HttpStackOffersResponseCallback(this, futureCallback, apiKey);
		ionWrapper.loadUrlWithStringResponse(offersServiceUrl, callback, context);
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
		boolean signatureValid = offersSignatureService.isResponseSignatureValid(result, apiKey);
		String body = result.getResult();
		if (!signatureValid) {
			Log.d(LOG_TAG, "handleOffersResponse called but the signature was invalid");
			SignatureNotValidException signatureNotValidException = new SignatureNotValidException(body, apiKey);
			futureCallback.onCompleted(signatureNotValidException, null);
			return;
		}

		// get the offers from the response and send them to the callback
		Log.d(LOG_TAG, String.format("handleOffersResponse called with valid signature and body [%s]", body));
		List<Offer> offers = offersJsonService.convertJsonToOffers(body);
		futureCallback.onCompleted(null, offers);
	}

	/**
	 * Loads an image from and URL and adds it ot a image view.
	 *
	 * @param imageView        the image view
	 * @param url              the URL
	 * @param placeholderResId placeholder resource for while the image is loading
	 * @param errorResId       resource to be used in case of an error
	 */
	public void loadImageFromUrlIntoImageView(ImageView imageView, String url, int placeholderResId, int errorResId) {
		Log.d(LOG_TAG, String.format("loadImageFromUrlIntoImageView called with URL [%s]", url));
		ionWrapper.loadImageFromUrlIntoImageView(imageView, url, placeholderResId, errorResId);
	}
}
