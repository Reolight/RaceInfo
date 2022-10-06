package com.reolight.raceidentity.support

import android.util.Range
import com.reolight.raceidentity.support.enums.HairColor

data class Subrace(
    val Name: String,
    var KefalIndex: Range<Float>,
    var FaceIndex: Range<Float>,
    var NoseIndex: Range<Float>,

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
    var Score = 0 //max 13!

    public fun ScoreSimilarity(other: Charter) {
        Score = 0
        if (KefalIndex.contains(other.kefalIndex)) Score++
        if (FaceIndex.contains(other.faceIndex)) Score++
        if (NoseIndex.contains(other.noseIndex)) Score++

        if (NoseMorph.contains(other.noseMorph)) Score++
        if (ForeheadHeightIndex.contains(other.foreheadHeightIndex)) Score++
        if (ForeheadMorph.contains(other.foreheadMorph)) Score++
        if (NapeConvexity.contains(other.napeConvexity)) Score++
        if (EyeShape.contains(other.eyeShape)) Score++
        if (CheekboneWidth.contains(other.cheekboneWidth)) Score++
        if (MandibleWidth.contains(other.mandibleWidth)) Score++
        if (SkinColor.contains(other.skinColor)) Score++

        HairColors.forEach {
            if (it.contains(other.hairColor)) Score++
        }

        if (EyeColors.contains(other.eyeColor)) Score++
    }
}