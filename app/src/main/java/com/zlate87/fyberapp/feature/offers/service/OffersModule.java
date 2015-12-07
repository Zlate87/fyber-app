package com.zlate87.fyberapp.feature.offers.service;

import android.content.Context;

import com.zlate87.fyberapp.feature.offers.service.OffersService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zlatko on 12/6/2015.
 */
@Module
public class OffersModule {

	@Provides
	@Singleton
	public OffersService provideOffersService() {
		return new OffersService();
	}
}
