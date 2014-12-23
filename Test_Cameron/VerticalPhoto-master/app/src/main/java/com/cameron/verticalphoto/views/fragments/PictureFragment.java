package com.cameron.verticalphoto.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cameron.verticalphoto.R;
import com.cameron.verticalphoto.model.entities.Picture;
import com.cameron.verticalphoto.services.ChallengeService;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

/**
 * <p>This fragment will display a picture. The fragment is provided with a picture entity object
 * that will give it the data to display the picture. </p>
 * <p>The text is added from the entity object. The picture is downloaded using Picasso.</p>
 *
 * @author Cameron
 * @version 1.0
 */
public class PictureFragment extends RoboFragment {
    private Picture picture;
    @InjectView(R.id.text)
    TextView text;
    @InjectView(R.id.image)
    ImageView image;
    @InjectView(R.id.progressBar) private ProgressBar progressBar;

    ProgressBar progressBar1;

    /**
     * <p>Sets the picture to be displayed. This method should be called before placing the fragment.</p>
     * @param picture picture entity object to set.
     */
    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (picture != null) {
            text.setText(picture.getTitle());
            Picasso.with(getActivity())
                    .load(ChallengeService.CHALLENGE_URL + "/photos/" + picture.getID())
                    .into(image,new Callback() {
                        @Override
                        public void onSuccess() {
                    progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError() {
                     progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null)
            picture = (Picture) savedInstanceState.get("picture");
        return inflater.inflate(R.layout.fragment_picture, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("picture", picture);
    }
}
