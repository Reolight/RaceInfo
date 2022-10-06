package com.reolight.raceidentity.support

import android.content.Context
import android.util.Range
import com.reolight.raceidentity.support.enums.HairColor

class Charter {
    var kefalIndex: Float? = null
        private var _skullWidth: Float? = null
        private var _skullHeight: Float? = null
    var faceIndex: Float? = null
        private var _faceHeight1: Float? = null
        private var _faceWidth: Float? = null
    var noseIndex: Float? = null
        private var _noseLength: Float? = null
        private var _noseWidth: Float? = null
    var noseMorph: Int? = null
    var foreheadHeightIndex: Int? = null
        private var _foreheadHeight: Float? = null
        private var _faceHeight2: Float? = null
    var foreheadMorph: Int? = null
    var napeConvexity: Int? = null
    var eyeShape: Int? = null
    var cheekboneWidth: Int? = null
    var mandibleWidth: Int? = null
    var skinColor: Int? = null
    var eyeColor: Int? = null
    var hairColor: Int? = null
    var hairType: Int? = null

    companion object Result{
        lateinit var subracesSortedList: List<Subrace>
    }

    private fun calcIndexes(){
        kefalIndex = _skullWidth!! / _skullHeight!!
        faceIndex =  _faceWidth!! / _faceHeight1!!
        noseIndex =  _noseWidth!! / _noseLength!!
        val lower = _faceHeight2!! / 3 * 0.95
        val upper = _faceHeight2!! / 3 * 0.105
        val kore = _foreheadHeight!! / (_faceHeight2!! / 3)
        foreheadHeightIndex = when {
            kore < lower -> 0
            kore in lower..upper -> 1
            kore > upper -> 2
            else -> -1
        }
    }

    fun updateVals(skullW: Float, skullH: Float,
                   faceH1: Float, faceW: Float,
                   noseL: Float, noseW: Float,
                   foreH: Float, faceH2: Float){
        _skullWidth = skullW;    _skullHeight = skullH;
        _faceHeight1 = faceH1;   _faceWidth = faceW;
        _noseLength = noseL;     _noseWidth = noseW;
        _foreheadHeight = foreH; _faceHeight2 = faceH2;
        calcIndexes()
    }

    fun calculate(context: Context){
        var subracesInfo = Subraces.GetInstance(context)
        subracesInfo.subraces.forEach {
            it.ScoreSimilarity(this)
        }

        subracesSortedList = subracesInfo.subraces.sortedByDescending { it.Score }
    }
}