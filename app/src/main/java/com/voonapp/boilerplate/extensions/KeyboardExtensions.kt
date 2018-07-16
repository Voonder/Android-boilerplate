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

package com.voonapp.boilerplate.extensions

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Show the soft input.
 */
fun AppCompatActivity.showSoftInput() {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return
    var view = currentFocus
    if (view == null) {
        view = View(this)
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.requestFocus()
    }
    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

/**
 * Hide  the soft input.
 */
fun AppCompatActivity.hideSoftInput() {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return
    val view = currentFocus ?: View(this)
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

/**
 * Show the soft input.
 */
fun Fragment.showSoftInput() {
    val inputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return
    var view = activity?.currentFocus
    if (view == null) {
        view = View(context)
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.requestFocus()
    }
    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED)
}

/**
 * Hide  the soft input.
 */
fun Fragment.hideSoftInput() {
    val inputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return
    val view = activity?.currentFocus ?: View(context)
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.showSoftInput() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return
    isFocusable = true
    isFocusableInTouchMode = true
    requestFocus()
    imm.showSoftInput(this, InputMethodManager.SHOW_FORCED)
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun View.hideSoftInput() {
    val inputMethodManager = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}
