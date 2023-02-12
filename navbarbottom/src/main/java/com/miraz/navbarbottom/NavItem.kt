package com.miraz.navbarbottom

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Created by miraz on 09/02/23.
 */

internal class NavItem(c: Context?, attrs: AttributeSet?) : LinearLayout(c, attrs) {

    init {
        val layoutInflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layoutInflater.inflate( R.layout.nav_item, this, true)
    }

    fun setItem(name: String?, image: Drawable?, color: Int) {
        name?.let {
            findViewById<TextView>(R.id.navItemText).text = it
        }
        image?.let {
            findViewById<ImageView>(R.id.navItemImage).setImageDrawable(it)
        }
        findViewById<TextView>(R.id.navItemText).setTextColor(color)
    }
}