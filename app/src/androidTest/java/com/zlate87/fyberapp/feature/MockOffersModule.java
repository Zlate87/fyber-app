package com.zlate87.fyberapp.feature;

import com.zlate87.fyberapp.feature.offers.service.OffersService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

/**
 * OffersModule that provides mocked OffersService.
 */
@Module
public class MockOffersModule {

	@Provides
	@Singleton
	public OffersService provideOffersService() {
		return mock(OffersService.class);
	}
}
