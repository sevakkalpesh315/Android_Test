package com.cameron.verticalphoto.services;

import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.cameron.verticalphoto.model.entities.Picture;
import com.squareup.otto.Bus;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.inject.Inject;

import roboguice.service.RoboIntentService;

/**
 * <p>This class request the information about the pictures to the restful API. An event is sent to
 * the bus in order to let the controller know that the information is ready.</p>
 *
 * @author Cameron
 * @version  1.0
 */
public class ChallengeService extends RoboIntentService {

    public static String CHALLENGE_URL = "http://challenge.superfling.com/";
    @Inject
    Bus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        bus.register(this);
    }

    public ChallengeService() {
        super("challenge service");
    }

    @Override
    public void onDestroy() {
        bus.unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        JsonArrayRequest request = new JsonArrayRequest(CHALLENGE_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                bus.register(this);
                Gson gson = new Gson();
                Type collectionType = new TypeToken<ArrayList<Picture>>(){}.getType();
                ArrayList<Picture> pictures = gson.fromJson(response.toString(), collectionType);
                bus.post(pictures);
                bus.unregister(this);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                bus.post(error);
            }
        });
        Volley.newRequestQueue(this).add(request);
    }
}
