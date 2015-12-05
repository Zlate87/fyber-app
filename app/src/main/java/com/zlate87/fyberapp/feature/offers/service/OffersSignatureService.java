package com.zlate87.fyberapp.feature.offers.service;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.koushikdutta.ion.Response;

import java.nio.charset.Charset;

/**
 * Class responsible for the signature validation.
 */
public class OffersSignatureService {

	private static final String SIGNATURE_HEADER_KEY = "X-Sponsorpay-Response-Signature";

	/**
	 * Checks if the response is valid.
	 *
	 * @param response the response
	 * @param apiKey   the API key
	 * @return {@code true} if the body is valid, {@code false} if not
	 */
	public boolean isResponseSignatureValid(Response<String> response, String apiKey) {
		String signature = response.getHeaders().getHeaders().get(SIGNATURE_HEADER_KEY);
		String responseBody = response.getResult();
		String hashSource = responseBody + apiKey;
		HashCode hashCode = Hashing.sha1().hashString(hashSource, Charset.defaultCharset());
		String hash = hashCode.toString();
		return signature.equals(hash);
	}

}
