package com.miraz.navbarbottom.model

import android.graphics.Color

/**
 * Created by miraz on 09/02/23.
 */

data class CustomProps(
        var secondaryTextColor : Int = Color.BLACK,
        var primaryTextColor : Int = Color.BLACK,
        var primaryButtonBg : Int = Color.BLACK,
        var lineColor : Int = Color.BLACK,
        var stripBg : Int = Color.WHITE,
        var secondaryItemClickedColor : Int = -1
)