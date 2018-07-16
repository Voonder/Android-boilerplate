/*
 * Copyright (c) 2018 Voonder
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.voonapp.boilerplate.ui.view.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.voonapp.boilerplate.R

abstract class StateView : FrameLayout {

    var viewSize: Int = SIZE_DEFAULT
        set(value) {
            field = value
            requestLayout()
            invalidate()
        }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.StateView, defStyleAttr, 0)

            try {
                if (typedArray.hasValue(R.styleable.StateView_sv_size)) {
                    viewSize = typedArray.getInt(R.styleable.StateView_sv_size, SIZE_DEFAULT)
                }
            } finally {
                typedArray.recycle()
            }
        }

        initChildren()
    }

    abstract fun initChildren()

    companion object {
        const val SIZE_SMALL = 1 shl 0
        const val SIZE_DEFAULT = 1 shl 1
    }
}
