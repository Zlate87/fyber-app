package com.zlate87.fyberapp.feature.offers.service;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.zlate87.fyberapp.feature.offers.model.OfferParameters;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class responsible for preparing the URL for the call to the backend.
 */
public class OffersUrlService {

	private static final String BASE_URL = "http://api.fyber.com/feed/v1/offers.json?";

	/**
	 * Prepares the URL for given offer parameters.
	 *
	 * @param offerParameters the parameters
	 * @return the URL
	 */
	public String getOffersServiceUrl(OfferParameters offerParameters) {
		Map<String, String> parameters = new TreeMap<>();
		parameters.put("appid", offerParameters.getAppid());
		parameters.put("uid", offerParameters.getUid());
		parameters.put("format", offerParameters.getFormat());
		parameters.put("os_version", offerParameters.getOsVersion());
		parameters.put("timestamp", offerParameters.getTimestamp());
		parameters.put("appid", offerParameters.getAppid());
		parameters.put("google_ad_id", offerParameters.getGoogleAddId());
		parameters.put("google_ad_id_limited_tracking_enabled", offerParameters.getGoogleAdIdLimitedTrackingEnabled());
		parameters.put("locale", offerParameters.getLocale());
		parameters.put("ip", offerParameters.getIp());
		parameters.put("pub0", offerParameters.getPub0());

		String hashKey = generateHashKey(parameters, offerParameters.getApiKey());
		parameters.put("hashKey", hashKey);

		StringBuilder stringBuilder = addParametersToStringBuilder(parameters);

		String url = BASE_URL + stringBuilder.toString();
		return url;
	}

	private String generateHashKey(Map<String, String> parameters, String apiKey) {
		StringBuilder hashSourceBuilder = addParametersToStringBuilder(parameters);
		hashSourceBuilder.append(apiKey);

		String hashSource = hashSourceBuilder.toString();
		HashCode hashCode = Hashing.sha1().hashString(hashSource, Charset.defaultCharset());
		return hashCode.toString();
	}

	private StringBuilder addParametersToStringBuilder(Map<String, String> parameters) {
		StringBuilder hashSourceBuilder = new StringBuilder();
		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			hashSourceBuilder.append(entry.getKey());
			hashSourceBuilder.append("=");
			hashSourceBuilder.append(entry.getValue());
			hashSourceBuilder.append("&");
		}
		return hashSourceBuilder;
	}
}
