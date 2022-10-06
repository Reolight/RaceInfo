package com.reolight.raceidentity.support.spinners

import android.graphics.Color.argb
import android.view.View
import android.widget.Spinner

class SpinnerColorListener(spinner: ColorSpinner, val view: View) : ColorSpinner.OnSpinnerEventsListener {
    companion object Colours {
        private val UNCHANGED = argb(40, 255, 0, 0)
        private val CHANGED = argb(40, 0, 255, 0)
    }

    private var changed = false

    init {
        spinner.setSpinnerEventsListener(this)
        view.setBackgroundColor(UNCHANGED)
    }

    fun resetStatus(){
        changed = false
        view.setBackgroundColor(UNCHANGED)
    }

    override fun onSpinnerOpened(spinner: Spinner?) { }

    override fun onSpinnerClosed(spinner: Spinner?) {
        if (!changed){
            changed = true;
            view.setBackgroundColor(CHANGED)
        }
    }
}