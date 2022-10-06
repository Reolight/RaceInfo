package com.reolight.raceidentity.forms

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.marginStart
import com.reolight.raceidentity.support.Subrace

@SuppressLint("SetTextI18n")
class ResultCardView(context: Context, val subrace: Subrace) : CardView(context) {
    val textView = TextView(context)
    val progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)

    init {
        val linLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        linLayoutParams.setMargins(16,16,16,16)
        layoutParams = linLayoutParams
        setPadding(16,16,16,16)

        val linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        textView.text = """${subrace.Name} (${(subrace.Score.toFloat() / 13 * 100)}%)"""
        textView.textSize = 24f
        linearLayout.addView(textView)
        cardElevation = 16f
        maxCardElevation = 32f

        progressBar.progress = (subrace.Score.toFloat() / 13f * 100).toInt()
        linearLayout.addView(progressBar)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        radius = 15f
        addView(linearLayout)
    }
}