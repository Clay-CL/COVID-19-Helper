package com.clay.covid_19helper.views

import android.content.Context
import android.graphics.Canvas
import android.os.Handler
import android.util.AttributeSet
import android.widget.EditText
import androidx.core.content.withStyledAttributes
import com.clay.covid_19helper.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TypeWriterSearchBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatEditText(context, attrs, defStyleAttr) {

    var mText: CharSequence? = null
    var mIndex = 0
    var mDelay: Long = 150 // in ms

    init {
        context.withStyledAttributes(attrs, R.styleable.TypeWriterSearchBar) {
            mText = this.getString(R.styleable.TypeWriterSearchBar_typeHint)
        }
    }

    private val mHandler = Handler()

    private val characterAdder: Runnable = object : Runnable {
        override fun run() {
            hint = mText?.subSequence(0, mIndex++)
            if (mIndex <= mText?.length!!) {
                mHandler.postDelayed(this, mDelay)
            }

        }
    }

    fun animateText(txt: CharSequence) {

        mText = txt
        mIndex = 0
        hint = ""
        mHandler.removeCallbacks(characterAdder)
        mHandler.postDelayed(characterAdder, mDelay)
    }



}