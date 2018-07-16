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
import com.voonapp.boilerplate.R
import com.voonapp.boilerplate.extensions.inflate

/**
 * Widget used to display an loading state to the user.
 *
 * @author Julien NORMAND - Orange Applications for Business [julien.normand@orange.com](julien.normand@orange.com)
 * @version 1.0.0
 * @since 2018-03-07
 */
class LoadingView : StateView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun initChildren() {
        when (viewSize) {
            SIZE_SMALL -> inflate(R.layout.view_loading_small, true)
            SIZE_DEFAULT -> inflate(R.layout.view_loading, true)
        }
    }
}
