package com.zlate87.fyberapp.feature.offers.exception;

/**
 * A custom exception that indicates that the signature is not valid.
 */
public class SignatureNotValidException extends RuntimeException {

	private final String body;
	private final String apiKey;

	/**
	 * Constructor.
	 * @param body the body of the response
	 * @param apiKey the apiKey
	 */
	public SignatureNotValidException(String body, String apiKey) {
		super();
		this.body = body;
		this.apiKey = apiKey;
	}

	public String getBody() {
		return body;
	}

	public String getApiKey() {
		return apiKey;
	}
}
