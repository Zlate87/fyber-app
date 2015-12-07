package com.zlate87.fyberapp.base;

import com.zlate87.fyberapp.feature.offers.controller.OffersActivity;

/**
 * Dagger component interface.
 */
public interface Component {
	void inject(OffersActivity offersActivity);
}
