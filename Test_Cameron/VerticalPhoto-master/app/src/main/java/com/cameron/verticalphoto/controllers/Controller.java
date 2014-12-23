package com.cameron.verticalphoto.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.cameron.verticalphoto.model.PicturesModel;
import com.cameron.verticalphoto.model.entities.Picture;
import com.cameron.verticalphoto.services.ChallengeService;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Iterator;

import javax.inject.Inject;

import roboguice.RoboGuice;

/**
 * <p>This class interacts with the views and ask the relevant model to perform the actions.
 * The result of the actions are processed and, eventually, returned to the view so that they
 * can be displayed.</p>
 *
 * @author Cameron
 * @version 1.0
 */
public class Controller {
    @Inject
    Bus bus;

    private Context context;

    /**
     * <p>Instantiates the controller. Upon instantiation, the controller registers the Otto bus.</p>
     * @param context a context is necessary for dependency injection.
     */
    public Controller(Context context) {
        RoboGuice.getInjector(context).injectMembers(this);
        this.context = context;
        bus.register(this);
    }

    @Override
    protected void finalize() throws Throwable {
        bus.unregister(this);
        super.finalize();
    }

    /**
     * <p>Start the downloading of the pictures from the REST API.</p>
     * @param activity is the activity that triggers the download.
     */
    public void downloadGallery(Activity activity)
    {
        Intent intent = new Intent(activity, ChallengeService.class);
        activity.startService(intent);
    }

    /**
     * <p>Insert the retrieved images in the database. When the service finishes, an Otto event is
     * posted. This method subscribes the event and insert the images on the database.</p>
     * @param pictures a collection containing all the images that the server returned.
     */
    @Subscribe
    public void onGalleryDownloaded(ArrayList<Picture> pictures)
    {
        PicturesModel model = new PicturesModel(context);
        Iterator<Picture> itrPictures = pictures.iterator();
        while (itrPictures.hasNext())
            model.insert(itrPictures.next());
        model.close();
        bus.post(new Boolean(true)); //Notifies the view that pictures are ready.
    }

    /**
     * <p>Returns all the available pictures in the database.</p>
     * @return an array list of pictures contained in the database.
     */
    public ArrayList<Picture> requestPictures()
    {
        PicturesModel model = new PicturesModel(context);
        ArrayList<Picture> pictures = model.findAll();
        model.close();
        return pictures;
    }

    /**
     * <p>Deletes all the pictures in the gallery.</p>
     */
    public void deleteGallery()
    {
        PicturesModel model = new PicturesModel(context);
        model.delete();
        model.close();
    }
}
