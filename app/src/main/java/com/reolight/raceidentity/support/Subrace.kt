package com.reolight.raceidentity.support

import android.util.Range
import com.reolight.raceidentity.support.enums.HairColor

data class Subrace(
    val Name: String,
    var KefalIndex: Int,
    var FaceIndex: Int,
    var NoseIndex: List<Int>,

    var NoseMorph: List<Int>,
    var ForeheadHeightIndex: List<Int>,
    var ForeheadMorph: List<Int>,
    var NapeConvexity: List<Int>,
    var EyeShape: List<Int>,
    var CheekboneWidth: List<Int>,
    var MandibleWidth: List<Int>,
    var SkinColor: List<Int>,
    var EyeColors: List<Int>,
    var HairColors: List<Range<Int>>,
    var HairType: List<Int>
) {
    companion object Constants{
        val SCORE_MAX = 14
        val KEF = "kef"
        val FAC = "fac"
        val NOS = "nos"
        val NSM = "nsm"
        val FHI = "fhi"
        val FHM = "fhm"
        val NCV = "ncv"
        val EYS = "eys"
        val EYC = "eyc"
        val CBW = "cbw"
        val MDW = "mdw"
        val SKC = "skc"
        val HRC = "hrc"
        val HRT = "hrt"
    }

    var Score = 0 //max 14!
    var Matched = ""

    fun ScoreSimilarity(other: Charter) {
        Score = 0; Matched = ""
        val coincident = mutableListOf<String>()

        fun Yes(string: String){
            Score++
            coincident.add(string)
        }

        if (KefalIndex.toString().indexOf(other.kefalIndex.toString()) >= 0) Yes(KEF)
        if (FaceIndex.toString().indexOf(other.faceIndex.toString()) >= 0) Yes(FAC)
        if (NoseIndex.toString().indexOf(other.noseIndex.toString()) >= 0) Yes(NOS)

        if (NoseMorph.contains(other.noseMorph)) Yes(NSM)
        if (ForeheadHeightIndex.contains(other.foreheadHeightIndex)) Yes(FHI)
        if (ForeheadMorph.contains(other.foreheadMorph)) Yes(FHM)
        if (NapeConvexity.contains(other.napeConvexity)) Yes(NCV)
        if (EyeShape.contains(other.eyeShape)) Yes(EYS)
        if (CheekboneWidth.contains(other.cheekboneWidth)) Yes(CBW)
        if (MandibleWidth.contains(other.mandibleWidth)) Yes(MDW)
        if (SkinColor.contains(other.skinColor)) Yes(SKC)
        if (HairType.contains(other.hairType)) Yes(HRT)

        HairColors.forEach {
            if (it.contains(other.hairColor)) Yes(HRC)
        }

        if (EyeColors.contains(other.eyeColor)) Yes(EYC)
        Matched = coincident.joinToString("|")
    }
}