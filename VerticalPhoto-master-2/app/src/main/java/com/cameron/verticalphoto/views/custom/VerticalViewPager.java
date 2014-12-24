package com.cameron.verticalphoto.views.custom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * <p>This is a view pager that scrolls vertically instead of horizontally.</p>
 *
 * @author Pablo Sánchez Alonso
 * @version  1.0
 */
public class VerticalViewPager extends ViewPager {
    public VerticalViewPager(Context context) {
        super(context);
        init();
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE)
            return true;
        else
            return super.onInterceptTouchEvent(ev);
    }

    private void init()
    {
        setPageTransformer(true, new VerticalPageTransformer());
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        ev.setLocation(ev.getY(), ev.getY());
        return super.onTouchEvent(ev);
    }

    private class VerticalPageTransformer implements PageTransformer
    {

        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();
            int pageHeight = page.getHeight();

            if (position < -1)
                page.setAlpha(0);
            else if (position <= 1)
            {
                page.setAlpha(1);
                page.setTranslationX(pageWidth * -position);
                page.setTranslationY(pageHeight * position);
            }
            else
                page.setAlpha(0);
        }
    }
}
