package com.zlate87.fyberapp.feature.offers.service;

import com.zlate87.fyberapp.feature.offers.model.Offer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for converting offers json into objects.
 */
public class OffersJsonService {

	/**
	 * Converts valid json offers string into a list of offers.
	 *
	 * @param jsonString the offers json
	 * @return the list of offers
	 * @throws RuntimeException if the jsonString is not valid
	 */
	public List<Offer> convertJsonToOffers(String jsonString) {
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray offersJsonArray = jsonObject.getJSONArray("offers");
			List<Offer> offers = new ArrayList<>();
			for (int i = 0; i < offersJsonArray.length(); i++) {
				JSONObject offerJsonObject = offersJsonArray.getJSONObject(i);
				Offer offer = new Offer();
				offer.setTitle(offerJsonObject.optString("title"));
				offer.setPayout(offerJsonObject.optString("payout"));
				offer.setTeaser(offerJsonObject.optString("teaser"));
				JSONObject thumbnailJsonObject = offerJsonObject.optJSONObject("thumbnail");
				if (thumbnailJsonObject != null) {
					offer.setThumbnail(thumbnailJsonObject.optString("hires"));
				}
				offers.add(offer);
			}
			return offers;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}

	}
}
