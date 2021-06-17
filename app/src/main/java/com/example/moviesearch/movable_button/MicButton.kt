package com.example.moviesearch.movable_button

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.example.moviesearch.R

class MicButton: FloaterButton {
    private lateinit var mLongPressed: Runnable

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

    private fun init() {
        setLayoutParams(56)
        setButtonGradient(R.color.dark_turquoise, R.color.light_turquoise)
        setButtonDrawable(R.drawable.ic_mic)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> handler.postDelayed(mLongPressed, ViewConfiguration.getLongPressTimeout().toLong())
            MotionEvent.ACTION_MOVE -> handler.removeCallbacks(mLongPressed)
            MotionEvent.ACTION_UP -> handler.removeCallbacks(mLongPressed)
        }
        return super.onTouch(v, event)
    }

    override fun setOnLongClickListener(l: OnLongClickListener?) {
        super.setOnLongClickListener(l)
        mLongPressed = Runnable {
            isLongPressed = true
            l?.onLongClick(this)
        }
    }
}