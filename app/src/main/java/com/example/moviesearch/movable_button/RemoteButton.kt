package com.example.moviesearch.movable_button

import android.content.Context
import android.util.AttributeSet
import com.example.moviesearch.R

class RemoteButton : FloaterButton {

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
        setLayoutParams(72)
        setButtonGradient(R.color.light_red, R.color.dark_red)
        setButtonDrawable(R.drawable.ic_remote)
    }
}