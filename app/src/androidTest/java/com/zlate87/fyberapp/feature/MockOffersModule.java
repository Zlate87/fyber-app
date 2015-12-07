package com.zlate87.fyberapp.feature;

import com.zlate87.fyberapp.feature.offers.service.OffersService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

/**
 * Created by Zlatko on 12/6/2015.
 */
@Module
public class MockOffersModule {

	@Provides
	@Singleton
	public OffersService provideOffersService() {
		return mock(OffersService.class);
	}
}
