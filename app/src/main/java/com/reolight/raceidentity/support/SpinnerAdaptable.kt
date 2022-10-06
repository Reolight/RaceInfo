package com.reolight.raceidentity.support

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import kotlin.reflect.KMutableProperty0

class SpinnerAdaptable(spinner: Spinner, var value: KMutableProperty0<Int?>) : AdapterView.OnItemSelectedListener
{
    init {
        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        value.set(position)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}