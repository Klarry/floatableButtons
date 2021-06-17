package com.example.moviesearch.movable_button

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageButton
import com.example.moviesearch.R
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import kotlin.math.abs

open class FloaterButton : FrameLayout, View.OnTouchListener {
    private lateinit var button: AppCompatImageButton

    private val CLICK_DRAG_TOLERANCE = 10f
    private var downRawX = 0f
    private var downRawY = 0f
    private var dX = 0f
    private  var dY = 0f

    protected var isLongPressed = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr) {
        init()
    }

    @Subscribe()
    fun onButtonTouchEvent(event: ButtonTouchEvent) {
        if (event.button === this) {
            this.bringToFront()
        }
    }

    private fun registerEventBus(subscriber: Any?) {
        try {
            if (!EventBus.getDefault().isRegistered(subscriber)) {
                EventBus.getDefault().register(subscriber)
            }
        } catch (e: Exception) {
            Log.d("FloaterButton", "registerEventBus e: $e")
        }
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                EventBus.getDefault().post(ButtonTouchEvent(this))
                downRawX = event.rawX
                downRawY = event.rawY
                dX = this.x - downRawX
                dY = this.y - downRawY
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                val layoutParams = v.layoutParams as MarginLayoutParams

                val viewWidth = this.width
                val viewHeight = this.height

                val viewParent = this.parent as View
                val parentWidth = viewParent.width
                val parentHeight = viewParent.height

                var newX: Float = event.rawX + dX
                newX = layoutParams.leftMargin.toFloat().coerceAtLeast(newX)
                newX = (parentWidth - viewWidth - layoutParams.rightMargin).toFloat().coerceAtMost(newX)

                var newY: Float = event.rawY + dY
                newY = layoutParams.topMargin.toFloat().coerceAtLeast(newY)
                newY = (parentHeight - viewHeight - layoutParams.bottomMargin - 50).toFloat().coerceAtMost(newY)

                animate()
                    .x(newX)
                    .y(newY)
                    .setDuration(0)
                    .start()
                return true
            }

            MotionEvent.ACTION_UP -> {
                val upRawX: Float = event.rawX
                val upRawY: Float = event.rawY
                val upDX: Float = upRawX - downRawX
                val upDY: Float = upRawY - downRawY
                if (abs(upDX) < CLICK_DRAG_TOLERANCE && abs(upDY) <CLICK_DRAG_TOLERANCE) {
                    if (isLongPressed) {
                        isLongPressed = false
                        return true
                    } else {
                        performClick()
                    }
                } else {
                    return true
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun init() {
        registerEventBus(this)
        val params = LayoutParams(100, 100)
        params.gravity = Gravity.CENTER

        button = AppCompatImageButton(context)
        button.background = getGradientOvalDrawable(
            context,
            R.color.purple_200,
            R.color.purple_700
        )
        button.setOnTouchListener(this)
        button.setColorFilter(Color.WHITE)
        button.bringToFront()
        button.setPadding(10, 10, 10, 10)
        button.scaleType = ImageView.ScaleType.FIT_CENTER
        button.adjustViewBounds = true
        addView(button, params)
    }

    protected fun setButtonGradient(bottomColor: Int, topColor: Int) {
        button.background = getGradientOvalDrawable(
            context,
            bottomColor,
            topColor
        )
    }

    protected fun setButtonDrawable(drawable: Int) {
        button.setImageDrawable(AppCompatResources.getDrawable(context, drawable))
    }

    protected fun setLayoutParams(size: Int) {
        button.layoutParams = LayoutParams(size, size)
    }

    private fun getGradientOvalDrawable(
        context: Context,
        topColor: Int,
        bottomColor: Int
    ): GradientDrawable {
        val colors = intArrayOf(
            Color.parseColor(getStringColor(context, bottomColor)),
            Color.parseColor(getStringColor(context, topColor))
        )
        val shape = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors)
        shape.shape = GradientDrawable.OVAL
        shape.cornerRadius = 0f
        return shape
    }

    private fun getStringColor(context: Context, color: Int): String {
        return context.resources.getString(color)
    }
}