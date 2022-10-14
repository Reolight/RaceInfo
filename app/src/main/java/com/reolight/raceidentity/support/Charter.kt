package com.reolight.raceidentity.support

import android.content.Context

class Charter{

    var kefalIndex: Int? = null
        private var _skullWidth: Float? = null
        private var _skullHeight: Float? = null
    var faceIndex: Int? = null
        private var _faceHeight1: Float? = null
        private var _faceWidth: Float? = null
    var noseIndex: Int? = null
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
        fun calc(index1: Float, index2: Float, threshold1: Float, threshold2: Float): Int {
            val i = index1 / index2
            return when {
                i < threshold1 -> 0
                i in threshold1..threshold2 -> 1
                i > threshold2 -> 2
                else -> -1
            }
        }
        kefalIndex = calc(_skullWidth!!, _skullHeight!!, 0.77f, 0.81f)
        faceIndex =  calc(_faceWidth!!, _faceHeight1!!, 0.84f, 0.88f)
        noseIndex =  calc(_noseWidth!!, _noseLength!!, 0.7f, 0.85f)
        foreheadHeightIndex = calc(_foreheadHeight!!, _faceHeight2!! / 3,
            _faceHeight2!! / 3 * 0.95f, _faceHeight2!! / 3 * 0.105f)
    }

    fun updateVals(skullW: Float, skullH: Float,
                   faceH1: Float, faceW: Float,
                   noseL: Float, noseW: Float,
                   foreH: Float, faceH2: Float){
        _skullWidth = skullW;    _skullHeight = skullH
        _faceHeight1 = faceH1;   _faceWidth = faceW
        _noseLength = noseL;     _noseWidth = noseW
        _foreheadHeight = foreH; _faceHeight2 = faceH2
        calcIndexes()
    }

    fun updateVals(faceParams: FaceParams){
        _faceHeight1 = faceParams.height1
        _faceHeight2 = faceParams.height2
        _foreheadHeight = faceParams.foreheadHeight
        _faceWidth = faceParams.faceWidth
        _noseLength = faceParams.noseHeight
        _noseWidth = faceParams.noseWidth
    }

    fun calculate(context: Context){
        val subracesInfo = Subraces.GetInstance(context)
        subracesInfo.subraces.forEach {
            it.ScoreSimilarity(this)
        }

        subracesSortedList = subracesInfo.subraces.sortedByDescending { it.Score }
    }
}