package com.cameron.verticalphoto.views.fragments;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cameron.verticalphoto.R;
import com.cameron.verticalphoto.model.entities.Picture;
import com.cameron.verticalphoto.services.ChallengeService;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

/**
 * <p>This fragment will display a picture. The fragment is provided with a picture entity object
 * that will give it the data to display the picture. </p>
 * <p>The text is added from the entity object. The picture is downloaded using Picasso.</p>
 *
 * @author Pablo Sánchez Alonso
 * @version 1.0
 */
public class PictureFragment extends RoboFragment {
    private Picture picture;
    @InjectView(R.id.text)
    TextView text;
    @InjectView(R.id.image)
    ImageView image;

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
            image.setTag(new Target(){
                @Override
                public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                    //In case is already on memory.
                    //image.setVisibility(View.GONE);
                    image.setAlpha(0.5f);
                    image.setScaleX(0.5f);
                    image.setScaleY(0.5f);
                    image.animate().alpha(1f).scaleX(1f).scaleY(1f)
                            .setDuration(500)
                            .setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    image.setImageBitmap(bitmap);

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {

                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            }).start();
                }

                @Override
                public void onBitmapFailed(Drawable drawable) {

                }

                @Override
                public void onPrepareLoad(Drawable drawable) {

                }
            });
            Picasso.with(getActivity())
                    .load(ChallengeService.CHALLENGE_URL + "/photos/" + picture.getID())
                    .into((Target) image.getTag());

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    v.animate().rotationX(360).setDuration(500).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            v.animate().rotationX(0).setDuration(500).start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).start();
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
