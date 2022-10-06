package com.reolight.raceidentity.support.spinners;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.Spinner;

public class ColorSpinner extends androidx.appcompat.widget.AppCompatSpinner {
    public ColorSpinner(Context context) {
        super(context);
    }

    public ColorSpinner(Context context, int mode) {
        super(context, mode);
    }

    public ColorSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ColorSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public interface OnSpinnerEventsListener{
        void onSpinnerOpened(Spinner spinner);
        void onSpinnerClosed(Spinner spinner);
    }

    private OnSpinnerEventsListener mListener;
    private boolean mOpenInitiated = false;

    @Override public boolean performClick(){
        mOpenInitiated = true;
        if (mListener != null){
            mListener.onSpinnerOpened(this);
        }

        return super.performClick();
    }

    public void setSpinnerEventsListener(OnSpinnerEventsListener onSpinnerEventsListener){
        mListener = onSpinnerEventsListener;
    }

    public void performCloseEvent(){
        mOpenInitiated = false;
        if (mListener != null){
            mListener.onSpinnerClosed(this);
        }
    }

    public boolean hasBeenOpened() {
        return mOpenInitiated;
    }

    @Override public void onWindowFocusChanged(boolean hasWindowFocus){
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasBeenOpened() && hasWindowFocus){
            performCloseEvent();
        }
    }
}
