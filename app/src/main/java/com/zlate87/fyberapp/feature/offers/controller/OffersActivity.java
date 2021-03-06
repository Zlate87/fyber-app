package com.zlate87.fyberapp.feature.offers.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.koushikdutta.async.future.FutureCallback;
import com.zlate87.fyberapp.base.App;
import com.zlate87.fyberapp.base.FyberBaseActivity;
import com.zlate87.fyberapp.R;
import com.zlate87.fyberapp.feature.offers.model.Offer;
import com.zlate87.fyberapp.feature.offers.model.OfferParameters;
import com.zlate87.fyberapp.feature.offers.service.OffersService;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 * Activity that is responsible for showing offers for the parameters it receives via intent extras.
 */
public class OffersActivity extends FyberBaseActivity {

	private static final String LOG_TAG = OffersActivity.class.getSimpleName();

	/** Key that is used when retrieving uid from the extras from the intent. */
	public static final String UID_INTENT_EXTRA_KEY = "UID_INTENT_EXTRA_KEY";
	/** Key that is used when retrieving appid from the extras from the intent. */
	public static final String APPID_INTENT_EXTRA_KEY = "APPID_INTENT_EXTRA_KEY";
	/** Key that is used when retrieving apyKey from the extras from the intent. */
	public static final String API_KEY_INTENT_EXTRA_KEY = "API_KEY_INTENT_EXTRA_KEY";
	/** Key that is used when retrieving pud0 from the extras from the intent. */
	public static final String PUB0_INTENT_EXTRA_KEY = "PUB0_INTENT_EXTRA_KEY";

	// fyber parameters
	private static final String FORMAT = "json";
	private static final String LOCALE = "de";
	public static final String IP = "109.235.143.113";
	public static final String OFFER_TYPES = "112";

	@Inject
	protected OffersService offersService;

	private RecyclerView offersRecyclerView;
	private TextView noOffersTextView;

	private ProgressDialog progress;

	private FutureCallback<List<Offer>> offersCallback = new FutureCallback<List<Offer>>() {

		@Override
		public void onCompleted(Exception exception, List<Offer> offers) {
			progress.hide();

			boolean availableOffers = exception == null && offers != null && !offers.isEmpty();
			offersRecyclerView.setVisibility(availableOffers ? View.VISIBLE: View.GONE);
			noOffersTextView.setVisibility(availableOffers ?  View.GONE : View.VISIBLE);

			if (exception != null) {
				Log.d(LOG_TAG, String.format("handleOffersResponse called with exception of type [%s]", exception.getClass()));
				Toast.makeText(OffersActivity.this, R.string.problemWhileLoadingDataFromServer, Toast.LENGTH_LONG).show();
				return;
			}

			OffersAdapter offersAdapter = new OffersAdapter(offers, OffersActivity.this, offersService);
			offersRecyclerView.setAdapter(offersAdapter);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((App) getApplication()).getComponent().inject(this);
		enableUpButton();

		final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		offersRecyclerView.setLayoutManager(layoutManager);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getOffers();
	}

	private void getOffers() {
		String message = getString(R.string.progress_message);
		progress = new ProgressDialog(this);
		progress.setMessage(message);
		progress.show();
		getOffersAsync getOffersAsync = new getOffersAsync();
		getOffersAsync.execute();
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
		noOffersTextView = (TextView) findViewById(R.id.noOffersTextView);
	}

	public class getOffersAsync extends AsyncTask<Void, Void, OfferParameters> {

		@Override
		protected OfferParameters doInBackground(Void... params) {
			OfferParameters offerParameters = new OfferParameters();
			if (getIntent().getExtras() != null) {
				offerParameters.setUid(getIntent().getExtras().getString(UID_INTENT_EXTRA_KEY));
				offerParameters.setAppid(getIntent().getExtras().getString(APPID_INTENT_EXTRA_KEY));
				offerParameters.setApiKey(getIntent().getExtras().getString(API_KEY_INTENT_EXTRA_KEY));
				offerParameters.setPub0(getIntent().getExtras().getString(PUB0_INTENT_EXTRA_KEY));
			}
			offerParameters.setFormat(FORMAT);
			offerParameters.setLocale(LOCALE);
			offerParameters.setOsVersion(android.os.Build.VERSION.RELEASE);
			offerParameters.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000L));
			try {
				AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(OffersActivity.this);
				offerParameters.setGoogleAddId(advertisingIdInfo.getId());
				boolean limitAdTrackingEnabled = advertisingIdInfo.isLimitAdTrackingEnabled();
				offerParameters.setGoogleAdIdLimitedTrackingEnabled(String.valueOf(limitAdTrackingEnabled));
			} catch (IOException | GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException e) {
				Log.w(LOG_TAG, "There was a problem while retrieving data from Google Play Services", e);
				offerParameters.setGoogleAddId("");
				offerParameters.setGoogleAdIdLimitedTrackingEnabled("false");
			}
			offerParameters.setIp(IP);
			offerParameters.setOfferTypes(OFFER_TYPES);
			return offerParameters;
		}

		@Override
		protected void onPostExecute(OfferParameters offerParameters) {
			offersService.getOffersForParameters(offerParameters, offersCallback, OffersActivity.this);
		}
	}

}
