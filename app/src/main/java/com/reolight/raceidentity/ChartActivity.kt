package com.reolight.raceidentity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.reolight.raceidentity.support.Charter
import com.reolight.raceidentity.support.SpinnerAdaptable
import java.lang.NumberFormatException

class ChartActivity : AppCompatActivity() {
    private lateinit var charter: Charter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        charter = Charter()
        setSpinners()
        findViewById<Button>(R.id.ChartDoneButton).setOnClickListener(::onDone)
    }

    private fun onDone(view: View) {
        val resultActivity = Intent(this, ResultActivity::class.java)
        try {
            charter.updateVals(getFloat(R.id.SkullWidthVal), getFloat(R.id.SkullHeightVal),
                getFloat(R.id.FaceHeight1Val), getFloat(R.id.FaceWidthVal),
                getFloat(R.id.NoseHeightVal), getFloat(R.id.NoseWidthVal),
                getFloat(R.id.ForeheadHeightVal), getFloat(R.id.FaceHeight2Val))
        } catch (ex : NumberFormatException){
            Toast.makeText(this, getString(R.string.fill_all_fields_please), Toast.LENGTH_SHORT).show()
            return
        }

        charter.calculate(this)
        startActivity(resultActivity)
    }

    private fun getFloat(ID: Int): Float = findViewById<EditText>(ID).text.toString().toFloat()

    private fun spinnerSetUp(SpinnerId : Int, TextArrayResId: Int) : Spinner
    {
        val spinner : Spinner = findViewById(SpinnerId)
        ArrayAdapter.createFromResource(this,
            TextArrayResId,
            android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }

        return spinner
    }

    private fun setSpinners(){
        val noseMorph = SpinnerAdaptable(spinnerSetUp(R.id.NoseMorphSpinner, R.array.nose_back_form_array),
            charter::noseMorph)
        val foreheadMorph = SpinnerAdaptable(spinnerSetUp(R.id.ForeheadMorphSpinner, R.array.forehead_form_array),
            charter::foreheadMorph)
        val napeConvexity = SpinnerAdaptable(spinnerSetUp(R.id.NapeConvexitySpinner, R.array.nape_convexity_array),
            charter::napeConvexity)
        val eyeCutType = SpinnerAdaptable(spinnerSetUp(R.id.EyeCutTypeSpinner, R.array.eye_cut_type_array),
            charter::eyeShape)
        val cheekboneWidth = SpinnerAdaptable(spinnerSetUp(R.id.CheekboneWidthSpinner, R.array.cheekbone_width_array),
            charter::cheekboneWidth)
        val mandibleWidth = SpinnerAdaptable(spinnerSetUp(R.id.MandibleWidthSpinner, R.array.mandible_width_array),
            charter::mandibleWidth)
        val skinColor = SpinnerAdaptable(spinnerSetUp(R.id.SkinColorSpinner, R.array.skin_color_array),
            charter::skinColor)
        val hairType = SpinnerAdaptable(spinnerSetUp(R.id.HairTypeSpinner, R.array.hair_type_array),
            charter::hairType)
        val hairColor = SpinnerAdaptable(spinnerSetUp(R.id.HairColorSpinner, R.array.haarfarbentafel_FisherSaller_array),
            charter::hairColor)
        val eyeColor = SpinnerAdaptable(spinnerSetUp(R.id.EyeColorSpinner, R.array.martin_schultz_array),
            charter::eyeColor)
    }
}