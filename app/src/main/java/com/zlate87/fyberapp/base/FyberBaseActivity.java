package com.zlate87.fyberapp.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Base activity that contains common activity logic that should be reused in every activity.
 */
public abstract class FyberBaseActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResId());
		setupViews();
	}

	/**
	 * @return the layout id for the current activity.
	 */
	protected abstract int getLayoutResId();

	/**
	 * Method that is called in onCreate and it is intended to setup the views from the activity.
	 */
	protected abstract void setupViews();
}
