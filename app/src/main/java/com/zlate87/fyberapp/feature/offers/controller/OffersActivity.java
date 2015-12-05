package com.zlate87.fyberapp.feature.offers.controller;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.koushikdutta.async.future.FutureCallback;
import com.zlate87.fyberapp.base.FyberBaseActivity;
import com.zlate87.fyberapp.R;
import com.zlate87.fyberapp.feature.offers.model.Offer;
import com.zlate87.fyberapp.feature.offers.model.OfferParameters;
import com.zlate87.fyberapp.feature.offers.service.OffersService;

import java.util.List;

/**
 * Activity that is responsible for showing offers for the parameters it receives via intent extras.
 */
// TODO add functional test for the activity
public class OffersActivity extends FyberBaseActivity {

	/** Key that is used when retrieving uid from the extras from the intent. */
	public static final String UID_INTENT_EXTRA_KEY = "UID_INTENT_EXTRA_KEY";
	/** Key that is used when retrieving appid from the extras from the intent. */
	public static final String APPID_INTENT_EXTRA_KEY = "UID_INTENT_EXTRA_KEY";
	/** Key that is used when retrieving apyKey from the extras from the intent. */
	public static final String API_KEY_INTENT_EXTRA_KEY = "UID_INTENT_EXTRA_KEY";
	/** Key that is used when retrieving pud0 from the extras from the intent. */
	public static final String PUB0_INTENT_EXTRA_KEY = "UID_INTENT_EXTRA_KEY";

	private OffersService offerService = new OffersService(this);

	private RecyclerView offersRecyclerView;

	FutureCallback<List<Offer>> offersCallback = new FutureCallback<List<Offer>>() {

		@Override
		public void onCompleted(Exception e, List<Offer> offers) {
			// TODO hide progress bar
			if (e != null) {
				// TODO show error message and log the exception
				return;
			}

			OffersAdapter offersAdapter = new OffersAdapter(offers);
			offersRecyclerView.setAdapter(offersAdapter);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		enableUpButton();

		final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		offersRecyclerView.setLayoutManager(layoutManager);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// the offers are retrieved asynchronously
		getOffers(offersCallback);
	}

	private void getOffers(FutureCallback<List<Offer>> offersCallback) {
		// TODO show progress bar
		OfferParameters offerParameters = new OfferParameters();
		offerParameters.setUid(getIntent().getExtras().getString(UID_INTENT_EXTRA_KEY));
		offerParameters.setAppid(getIntent().getExtras().getString(APPID_INTENT_EXTRA_KEY));
		offerParameters.setApiKey(getIntent().getExtras().getString(API_KEY_INTENT_EXTRA_KEY));
		offerParameters.setPub0(getIntent().getExtras().getString(PUB0_INTENT_EXTRA_KEY));
		offerService.getOffersForParameters(offerParameters, offersCallback);
	}

	private void enableUpButton() {
		ActionBar supportActionBar = getSupportActionBar();
		if (supportActionBar == null) {
			throw new RuntimeException("Support action bar is not found. Check if the activity is configured to have it");
		}
		supportActionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.offers_activity;
	}

	@Override
	protected void setupViews() {
		offersRecyclerView = (RecyclerView) findViewById(R.id.offersRecyclerView);
	}

}
