package com.reolight.raceidentity.support.spinners

import android.graphics.Color
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import kotlin.reflect.KMutableProperty0

class SpinnerAdaptable(val spinner: Spinner, var value: KMutableProperty0<Int?>) : AdapterView.OnItemSelectedListener
{

    init {
        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        value.set(position)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}