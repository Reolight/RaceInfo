package com.reolight.raceidentity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.reolight.raceidentity.support.Charter
import com.reolight.raceidentity.support.Subrace

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val subraces = Charter.subracesSortedList.take(5)
        if (savedInstanceState == null){
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                subraces.forEach {
                    val bundle = bundleOf(
                        Subrace::class.simpleName!! to it.Name,
                        "score" to it.Score
                    )
                    add<ResultCardFragment>(R.id.SupremeContainer, args = bundle)
                }
            }
        }
    }
}