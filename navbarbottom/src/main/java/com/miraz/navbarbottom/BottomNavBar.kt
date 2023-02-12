package com.miraz.navbarbottom

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.miraz.navbarbottom.model.CustomProps
import com.miraz.navbarbottom.model.NavObject

/**
 * Created by miraz on 09/02/23.
 */

class BottomNavBar(c: Context?, attrs: AttributeSet?) : ConstraintLayout(c!!, attrs) {
    private var weight: Float = 20.0f
    private var itemSize: Int = 0
    private val properties: CustomProps = CustomProps()
    private var coloredItemIndex: Int = -1
//    private lateinit var binding: BottomNavBarBinding

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BottomNavBar,
            0, 0
        )
        setSecondaryTextColor(
            typedArray.getColor(
                R.styleable.BottomNavBar_secondary_txt_color,
                Color.BLACK
            )
        )
        setPrimaryTextColor(
            typedArray.getColor(
                R.styleable.BottomNavBar_primary_txt_color,
                Color.BLACK
            )
        )
        setPrimaryButtonBackground(
            typedArray.getColor(
                R.styleable.BottomNavBar_primary_btn_bg,
                Color.BLACK
            )
        )
        setLineColor(
            typedArray.getColor(
                R.styleable.BottomNavBar_line_color,
                Color.BLACK
            )
        )
        setStripBackground(
            typedArray.getColor(
                R.styleable.BottomNavBar_strip_bg,
                Color.WHITE
            )
        )
        setSecondaryItemClickedColor(
            typedArray.getColor(
                R.styleable.BottomNavBar_secondary_item_clicked,
                -1
            )
        )
        typedArray.recycle()
        inflate()
    }

    private fun inflate() {
        val layoutInflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layoutInflater.inflate(R.layout.bottom_nav_bar, this, true)
//        binding = BottomNavBarBinding.inflate(layoutInflater)
/*        val view = binding.root
        setContentView(view)*/
    }

    /**
     * Primary initialization function
     * @param primaryNavObject - Object for center/floating button
     * @param secondaryNavObjects - List of objects for other buttons on the strip. The size of this list should be 2 or 4
     * @param listener - Callback for button click
     *
     */
    fun init(
        primaryNavObject: NavObject,
        secondaryNavObjects: List<NavObject>,
        listener: (position: Int, primaryClicked: Boolean) -> Unit
    ) {
        setSizeVariables(secondaryNavObjects)
        setItemStrip()
        setUpPrimaryItem(primaryNavObject)
        setUpSecondaryItems(secondaryNavObjects)
        addDummyView()
        setUpPrimaryItemListener(listener)
        setUpSecondaryItemListener(listener)
    }

    private fun setUpSecondaryItemListener(listener: (position: Int, primaryClicked: Boolean) -> Unit) {
        for (k in 0 until findViewById<LinearLayout>(R.id.itemStrip).childCount) {
            val child = findViewById<LinearLayout>(R.id.itemStrip).getChildAt(k)
            child.setOnClickListener {
                if (properties.secondaryItemClickedColor != -1) {
                    resetColoredItem(k)
                    setColorToCurrentItem(child)
                }
                when {
                    k > itemSize / 2 -> listener(k - 1, false)
                    else -> listener(k, false)
                }
            }
        }
    }

    private fun setUpPrimaryItemListener(listener: (position: Int, primaryClicked: Boolean) -> Unit) {
        findViewById<ImageButton>(R.id.primaryButton).setOnClickListener {
            listener(-1, true)
            resetColoredItem(-2)
            val animation = AnimationUtils.loadAnimation(context, R.anim.bounce)
            val bounceInterpolator = BounceInterpolator(0.2, 20.0)
            animation.interpolator = bounceInterpolator
            findViewById<ImageButton>(R.id.primaryButton).startAnimation(animation)
        }
        findViewById<TextView>(R.id.primaryText).setOnClickListener {
            listener(-1, true)
        }
    }

    private fun setColorToCurrentItem(child: View) {
        coloredItemIndex = findViewById<LinearLayout>(R.id.itemStrip).indexOfChild(child)
        val layout = child as LinearLayout
        val image = layout.findViewById<ImageView>(R.id.navItemImage)
        val text = layout.findViewById<TextView>(R.id.navItemText)
        text.setTextColor(properties.secondaryItemClickedColor)
        image.setColorFilter(properties.secondaryItemClickedColor, PorterDuff.Mode.SRC_ATOP)
    }

    private fun resetColoredItem(currentItemIndex: Int) {
        if (currentItemIndex != coloredItemIndex && coloredItemIndex != -1) {
            val layout =
                findViewById<LinearLayout>(R.id.itemStrip).getChildAt(coloredItemIndex) as LinearLayout
            val image = layout.findViewById<ImageView>(R.id.navItemImage)
            val text = layout.findViewById<TextView>(R.id.navItemText)
            text.setTextColor(properties.secondaryTextColor)
            image.setColorFilter(properties.secondaryTextColor, PorterDuff.Mode.SRC_ATOP)
        }
    }

    private fun setSizeVariables(navObjects: List<NavObject>) {
        itemSize = navObjects.size
        when {
            itemSize % 2 != 0 -> {
                Toast.makeText(
                    context, "Secondary items should be of even size",
                    Toast.LENGTH_LONG
                ).show()
                return
            }
            itemSize > 4 -> navObjects.dropLast(4)
            itemSize == 2 -> weight = 33.3f
        }
    }

    private fun setItemStrip() {
        findViewById<LinearLayout>(R.id.itemStrip).setBackgroundColor(properties.stripBg)
        lollipopAndAbove {
            findViewById<ConstraintLayout>(R.id.mainLayout).elevation = 8.toPx()
        }
    }

    private fun setUpPrimaryItem(primaryNavObject: NavObject) {
        findViewById<TextView>(R.id.primaryText).text = primaryNavObject.name
        findViewById<ImageButton>(R.id.primaryButton).setImageDrawable(primaryNavObject.image)
        val gradient: GradientDrawable =
            findViewById<ImageButton>(R.id.primaryButton).background as GradientDrawable
        gradient.setColor(properties.primaryButtonBg)
        findViewById<TextView>(R.id.primaryText).setTextColor(properties.primaryTextColor)
        lollipopAndAbove {
            findViewById<ImageButton>(R.id.primaryButton).elevation = 8.toPx()
        }
    }

    private fun setUpSecondaryItems(navObjects: List<NavObject>) {
        navObjects.forEach { navObject: NavObject ->
            val navItem = NavItem(context, null)
            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.weight = weight
            navItem.layoutParams = params
            navItem.setItem(navObject.name, navObject.image, properties.secondaryTextColor)
            navItem.gravity = Gravity.CENTER_HORIZONTAL
            findViewById<LinearLayout>(R.id.itemStrip).addView(navItem)
        }
    }

    private fun addDummyView() {
        val navItem = NavItem(context, null)
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.weight = weight
        navItem.layoutParams = params
        navItem.gravity = Gravity.CENTER_HORIZONTAL
        navItem.visibility = View.INVISIBLE
        navItem.isClickable = false
        findViewById<LinearLayout>(R.id.itemStrip).addView(navItem, itemSize / 2)
    }

    /**
     * Setters
     */

    fun setSecondaryTextColor(color: Int) {
        properties.secondaryTextColor = color
    }

    fun setPrimaryTextColor(color: Int) {
        properties.primaryTextColor = color
    }

    fun setPrimaryButtonBackground(color: Int) {
        properties.primaryButtonBg = color
    }

    fun setLineColor(color: Int) {
        properties.lineColor = color
    }

    fun setStripBackground(color: Int) {
        properties.stripBg = color
    }

    fun setSecondaryItemClickedColor(color: Int) {
        properties.secondaryItemClickedColor = color
    }

    /**
     * Utility functions
     */

    private fun Int.toPx(): Float {
        val displayMetrics = context.resources.displayMetrics
        return Math.round(this * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).toFloat()
    }

    private inline fun lollipopAndAbove(body: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            body()
        }
    }
}