package com.reolight.raceidentity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.reolight.raceidentity.databinding.FragmentResultCardBinding
import com.reolight.raceidentity.support.Subrace

/**
 * A simple [Fragment] subclass.
 * Use the [ResultCardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultCardFragment : Fragment() {
    private var subrace: String? = null
    private var score: Int? = null

    private lateinit var _binding: FragmentResultCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subrace = it.getString(Subrace::class.simpleName)
            score = it.getInt("score")
        }

        _binding = FragmentResultCardBinding.inflate(layoutInflater)
        val perc = (score!!.toFloat() / 13 * 100 ).toInt()
        with(_binding){
            SubraceNameText.text = "$subrace ($perc%)"
            SubraceScoreBar.progress = perc
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return _binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param subrace is subrace.
         * @param score is similarity score.
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