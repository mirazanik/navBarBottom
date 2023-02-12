package com.miraz.navbarbottom

import android.view.animation.Interpolator

/**
 * Created by miraz on 09/02/23.
 */

class BounceInterpolator(private var amplitude: Double, private var frequency: Double) :
    Interpolator {

    override fun getInterpolation(input: Float): Float =
        (-1 * Math.pow(Math.E, -input / amplitude) * Math.cos(frequency * input) + 1).toFloat()

}