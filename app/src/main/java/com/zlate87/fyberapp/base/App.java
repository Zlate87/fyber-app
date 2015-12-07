package com.zlate87.fyberapp.base;

import android.app.Application;

import com.zlate87.fyberapp.feature.offers.service.OffersModule;

/**
 * The Application class.
 */
public class App extends Application {

	private Component component;

	public Component getComponent() {
		if (component == null) {
			component = DaggerFyberComponent.builder().offersModule(new OffersModule()).build();
		}
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}
}
