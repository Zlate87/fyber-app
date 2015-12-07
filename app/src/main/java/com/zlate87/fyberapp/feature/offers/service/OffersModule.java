package com.zlate87.fyberapp.feature.offers.service;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for the OffersService.
 */
@Module
public class OffersModule {

	/**
	 * @return the provided OfferService
	 */
	@Provides
	@Singleton
	public OffersService provideOffersService() {
		return new OffersService();
	}
}
