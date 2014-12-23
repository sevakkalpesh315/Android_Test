package com.cameron.verticalphoto.modules;

import com.google.inject.AbstractModule;
import com.cameron.verticalphoto.controllers.Controller;
import com.cameron.verticalphoto.modules.providers.BusProvider;
import com.cameron.verticalphoto.modules.providers.ControllerProvider;
import com.squareup.otto.Bus;

/**
 * <p>This class provides injection for all the custom injections such as the Otto bus. </p>
 *
 * @author Cameron
 * @version 1.0
 */
public class GeneralModule extends AbstractModule{

    @Override
    protected void configure() {
        bind(Bus.class).toProvider(BusProvider.class);
        bind(Controller.class).toProvider(ControllerProvider.class);
    }
}
