package com.reolight.raceidentity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.reolight.raceidentity.support.Charter
import com.reolight.raceidentity.support.FaceParams
import com.reolight.raceidentity.support.spinners.ColorSpinner
import com.reolight.raceidentity.support.spinners.SpinnerAdaptable
import com.reolight.raceidentity.support.spinners.SpinnerColorListener
import kotlin.reflect.KMutableProperty0

class ChartActivity : AppCompatActivity() {
    private lateinit var charter: Charter
    private var spinners: MutableList<ColorSpinner> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        charter = Charter()
        setSpinners()
        findViewById<Button>(R.id.ChartDoneButton).setOnClickListener(::onDone)
    }

    override fun onResume() {
        super.onResume()
        val data: FaceParams?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            data = intent.getSerializableExtra(FaceParams::class.simpleName, FaceParams::class.java)
        else
            data = intent.getSerializableExtra(FaceParams::class.simpleName) as FaceParams?
        data?.let {
            charter.updateVals(data)
        }
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

    private fun spinnerSetUp(
        SpinnerId: Int,
        TextArrayResId: Int,
        value: KMutableProperty0<Int?>,
        rowId: Int
    ) : ColorSpinner
    {
        val spinner : ColorSpinner = findViewById(SpinnerId)
        ArrayAdapter.createFromResource(this,
            TextArrayResId,
            android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }

        SpinnerAdaptable(spinner, value)
        SpinnerColorListener(spinner, findViewById(rowId))
        spinners.add(spinner)
        return spinner
    }

    private fun setSpinners(){
        spinnerSetUp(
            R.id.NoseMorphSpinner,
            R.array.nose_back_form_array,
            charter::noseMorph,
            R.id.NoseRow)
        spinnerSetUp(
            R.id.ForeheadMorphSpinner,
            R.array.forehead_form_array,
            charter::foreheadMorph,
            R.id.ForeheadMorphRow
        )
        spinnerSetUp(
            R.id.NapeConvexitySpinner,
            R.array.nape_convexity_array,
            charter::napeConvexity,
            R.id.NapeConvexityRow
        )
        spinnerSetUp(R.id.EyeCutTypeSpinner, R.array.eye_cut_type_array, charter::eyeShape, R.id.EyeShapeRow)
        spinnerSetUp(
            R.id.CheekboneWidthSpinner,
            R.array.cheekbone_width_array,
            charter::cheekboneWidth,
            R.id.CheekboneWidthRow
        )
        spinnerSetUp(
            R.id.MandibleWidthSpinner,
            R.array.mandible_width_array,
            charter::mandibleWidth,
            R.id.MandibleWidthRow
        )
        spinnerSetUp(R.id.SkinColorSpinner, R.array.skin_color_array, charter::skinColor,R.id.SkinColorRow)
        spinnerSetUp(R.id.HairTypeSpinner, R.array.hair_type_array, charter::hairType,R.id.HairTypeRow)
        spinnerSetUp(
            R.id.HairColorSpinner,
            R.array.haarfarbentafel_FisherSaller_array,
            charter::hairColor,
            R.id.HairColorRow
        )
        spinnerSetUp(R.id.EyeColorSpinner, R.array.martin_schultz_array, charter::eyeColor,R.id.EyeColorRow)
    }
}