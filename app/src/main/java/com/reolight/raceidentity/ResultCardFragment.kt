package com.reolight.raceidentity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Range
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.reolight.raceidentity.databinding.FragmentResultCardBinding
import com.reolight.raceidentity.support.Subrace
import com.reolight.raceidentity.support.Subraces

/**
 * A simple [Fragment] subclass.
 * Use the [ResultCardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultCardFragment : Fragment() {
    private var subrace: String? = null
    private var subraceObj : Subrace? = null
    private var score: Int? = null

    private lateinit var _binding: FragmentResultCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subrace = it.getString(Subrace::class.simpleName)
            score = it.getInt("score")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentResultCardBinding.inflate(layoutInflater)
        val perc = (score!!.toFloat() / Subrace.SCORE_MAX * 100 ).toInt()
        with(_binding){
            SubraceNameText.text = "$subrace ($perc%)" // FIXME: Здесь короче можно в коде субрасс добавлять не строку, а АйДи на неё и избавиться от "хард" кодед стринг. Ну не гений ли я?
            SubraceScoreBar.progress = perc

        }

        _binding.ResultCard.setOnClickListener() {
            with (_binding.AdditionalInfo) {
                if (this.visibility == View.GONE)
                    this.visibility = View.VISIBLE
                else
                    this.visibility = View.GONE
            }
        }

        subraceObj = Subraces.GetSubrace(subrace!!)
        setUpColors()
        setUpText()
        return _binding.root
    }

    private fun setUpText(){
        fun getSignedVal(id: Int, v: List<Int>): String {
            val arr = resources.getStringArray(id)
            val str = StringBuilder()
            v.forEach { str.append(if (str.isNotEmpty()) ", ${arr[it]}" else arr[it]) }
            return str.toString()
        }

        fun turnInRanges(id: Int, v: List<Int>): String{
            val arr = resources.getStringArray(id)
            val str = StringBuilder()
            var last = -1
            v.forEach {
                if (last == -1)
                    str.append(arr[it])
                else {
                    if (it - last > 1) {
                        str.append(" - ${arr[last]}, ${arr[it]}")
                    }
                }

                last = it
            }

            return str.append(" - ${arr[last]}").toString()
        }

        fun adaptRanges(id: Int, ranges: List<Range<Int>>, isTyped : Boolean = false): String {
            val arr = resources.getStringArray(id)
            val str = StringBuilder()
            ranges.forEach {
                str.append((
                        (if (str.isNotEmpty()) ", " else "") +
                                (if (isTyped) "${arr[it.lower]} - ${arr[it.upper]}"
                                else "${arr[it.lower]} - ${arr[it.upper]}")))
            }

            return str.toString()
        }

        with(_binding) {
            subraceObj?.let {
                KephalIndexInfo.text = getString(
                    R.string.kephal_index_valued,
                    getSignedVal(R.array.kephal_index_values, listOf(it.KefalIndex))
                )
                CheekboneWidthInfo.text = getString(
                    R.string.cheekbone_width_valued,
                    getSignedVal(R.array.cheekbone_width_array, it.CheekboneWidth)
                )
                EyeColorInfo.text = getString(
                    R.string.eye_color_valued,
                    turnInRanges(R.array.martin_schultz_array, it.EyeColors)
                )
                EyeShapeInfo.text = getString(
                    R.string.eye_cut_type_valued,
                    getSignedVal(R.array.eye_cut_type_array, it.EyeShape)
                )
                FacialIndexInfo.text = getString(
                    R.string.facial_index_valued,
                    getSignedVal(R.array.facial_index_values, listOf(it.FaceIndex))
                )
                ForeheadHeightIndexInfo.text = getString(
                    R.string.forehead_height_valued,
                    getSignedVal(R.array.forehead_height_array, it.ForeheadHeightIndex)
                )
                ForeheadMorphInfo.text = getString(
                    R.string.forehead_morph_valued,
                    getSignedVal(R.array.forehead_form_array, it.ForeheadMorph)
                )
                HairColorInfo.text = getString(
                    R.string.hair_color_valued,
                    adaptRanges(R.array.haarfarbentafel_FisherSaller_array, it.HairColors, true)
                )
                HairTypeInfo.text = getString(
                    R.string.hair_type_valued,
                    getSignedVal(R.array.hair_type_array, it.HairType)
                )
                MandibleWidthInfo.text = getString(
                    R.string.mandible_width_valued,
                    getSignedVal(R.array.mandible_width_array, it.MandibleWidth)
                )
                NapeConvInfo.text = getString(
                    R.string.nape_convexity_valued,
                    getSignedVal(R.array.nape_convexity_array, it.NapeConvexity)
                )
                NoseMorphInfo.text = getString(
                    R.string.nose_morph_valued,
                    getSignedVal(R.array.nose_back_form_array, it.NoseMorph)
                )
                NoseIndexInfo.text = getString(
                    R.string.nose_index_valued,
                    getSignedVal(R.array.nose_index_values, it.NoseIndex)
                )
                SkinColorInfo.text = getString(
                    R.string.skin_color_valued,
                    getSignedVal(R.array.skin_color_array, it.SkinColor)
                )
            }
        }
    }

    private fun setUpColors(){
        val matched = subraceObj!!.Matched.split('|')
        val GREEN = Color.argb(255,50,140,50)
        matched.forEach{
            with (_binding) {
                when (it) {
                    Subrace.CBW -> CheekboneWidthInfo.setTextColor(GREEN)
                    Subrace.EYC -> EyeColorInfo.setTextColor(GREEN)
                    Subrace.EYS -> EyeShapeInfo.setTextColor(GREEN)
                    Subrace.FAC -> FacialIndexInfo.setTextColor(GREEN)
                    Subrace.FHI -> ForeheadHeightIndexInfo.setTextColor(GREEN)
                    Subrace.FHM -> ForeheadMorphInfo.setTextColor(GREEN)
                    Subrace.HRC -> HairColorInfo.setTextColor(GREEN)
                    Subrace.HRT -> HairTypeInfo.setTextColor(GREEN)
                    Subrace.KEF -> KephalIndexInfo.setTextColor(GREEN)
                    Subrace.MDW -> MandibleWidthInfo.setTextColor(GREEN)
                    Subrace.NCV -> NapeConvInfo.setTextColor(GREEN)
                    Subrace.NSM -> NoseMorphInfo.setTextColor(GREEN)
                    Subrace.NOS -> NoseIndexInfo.setTextColor(GREEN)
                    Subrace.SKC -> SkinColorInfo.setTextColor(GREEN)
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 is subrace.
         * @param param2 is similarity score.
         * @return A new instance of fragment ResultCardFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: Int) =
            ResultCardFragment().apply {
                arguments = Bundle().apply {
                    putString(Subrace::class.simpleName, param1)
                    putInt("score", param2)
                }
            }
    }
}