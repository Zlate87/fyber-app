package com.zlate87.fyberapp.feature.form.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.zlate87.fyberapp.R;
import com.zlate87.fyberapp.base.FyberBaseActivity;
import com.zlate87.fyberapp.feature.offers.controller.OffersActivity;

/**
 * An activity that is responsible for showing a form where the user can enter the needed data for viewving the offers.
 */
// TODO add functional test for the activity
// TODO improve the UI design (change colors, ...)
public class FormActivity extends FyberBaseActivity {

	private static final String LOG_TAG = FormActivity.class.getSimpleName();

	private EditText uidEditText;
	private EditText apiKeyEditText;
	private EditText appidEditText;
	private EditText pub0EditText;

	private View.OnClickListener viewOffersOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {

			// TODO add validation for mandatory fileds

			String uid = uidEditText.getText().toString();
			String apiKey = apiKeyEditText.getText().toString();
			String appid = appidEditText.getText().toString();
			String pub0 = pub0EditText.getText().toString();

			Log.d(LOG_TAG, String.format("Button [viewOffers] clicked and the form data: uid=[%s], apiKey=[%s], appid=[%s] " +
							"and pub0=[%s]", uid, apiKey, appid, pub0));

			Intent navigateToOffersIntent = new Intent(FormActivity.this, OffersActivity.class);
			navigateToOffersIntent.putExtra(OffersActivity.UID_INTENT_EXTRA_KEY, uid);
			navigateToOffersIntent.putExtra(OffersActivity.API_KEY_INTENT_EXTRA_KEY, apiKey);
			navigateToOffersIntent.putExtra(OffersActivity.APPID_INTENT_EXTRA_KEY, appid);
			navigateToOffersIntent.putExtra(OffersActivity.PUB0_INTENT_EXTRA_KEY, pub0);
			startActivity(navigateToOffersIntent);
		}
	};

	private View.OnClickListener insertSampleDataOnClickListener = new View.OnClickListener() {
		@SuppressLint("SetTextI18n")
		@Override
		public void onClick(View v) {
			uidEditText.setText("spiderman");
			apiKeyEditText.setText("1c915e3b5d42d05136185030892fbb846c278927");
			appidEditText.setText("2070");
			pub0EditText.setText("test");
		}
	};

	@Override
	protected int getLayoutResId() {
		return R.layout.form_activity;
	}

	@Override
	protected void setupViews() {
		uidEditText = (EditText) findViewById(R.id.uid);
		apiKeyEditText = (EditText) findViewById(R.id.apiKey);
		appidEditText = (EditText) findViewById(R.id.appid);
		pub0EditText = (EditText) findViewById(R.id.pub0);

		findViewById(R.id.viewOffers).setOnClickListener(viewOffersOnClickListener);
		findViewById(R.id.insertSampleData).setOnClickListener(insertSampleDataOnClickListener);
	}
}
