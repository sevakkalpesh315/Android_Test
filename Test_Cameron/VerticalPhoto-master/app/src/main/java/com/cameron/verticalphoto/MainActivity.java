package com.cameron.verticalphoto;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.inject.Inject;
import com.cameron.verticalphoto.controllers.Controller;
import com.cameron.verticalphoto.model.entities.Picture;
import com.cameron.verticalphoto.views.custom.VerticalViewPager;
import com.cameron.verticalphoto.views.fragments.PictureFragment;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;


public class MainActivity extends RoboActionBarActivity {
    @InjectView(R.id.pager)
    VerticalViewPager pager;
    @Inject
    Bus bus;
    @Inject
    Controller controller;

    private ArrayList<Picture> pictures = new ArrayList<Picture>();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("pictures", pictures);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pictures = (ArrayList<Picture>) savedInstanceState.get("pictures");
        pager.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bus.register(this);
        if (savedInstanceState == null)
            controller.downloadGallery(this);
        else
            pictures = (ArrayList<Picture>) savedInstanceState.get("pictures");
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                PictureFragment fragment = new PictureFragment();
                fragment.setPicture(pictures.get(position));
                return fragment;
            }

            @Override
            public int getCount() {
                return pictures.size();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        controller.deleteGallery();
        bus.unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onDataReceived(Boolean response)
    {
        if (response.booleanValue())
        {
            pictures = controller.requestPictures();
            pager.getAdapter().notifyDataSetChanged();
        }
    }
}
