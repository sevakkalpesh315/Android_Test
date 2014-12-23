package com.cameron.verticalphoto.modules.providers;

import android.content.Context;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.cameron.verticalphoto.controllers.Controller;

/**
 * <p>Provides a controller.</p>
 *
 * @author Pablo Sánchez Alonso
 * @version 1.0
 */
@Singleton
public class ControllerProvider implements Provider<Controller> {

    private Context context;

    @Inject public ControllerProvider(Context context) {
        this.context = context;
    }

    @Override
    public Controller get() {
        return new Controller(context.getApplicationContext());
    }
}
