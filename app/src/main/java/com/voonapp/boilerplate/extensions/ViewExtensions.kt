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

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.support.annotation.LayoutRes
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import com.bumptech.glide.load.Transformation
import com.voonapp.boilerplate.di.picture.GlideApp
import timber.log.Timber

@SuppressLint("RestrictedApi")
fun BottomNavigationView.disableShiftMode() {
    val menuView = this.getChildAt(0) as BottomNavigationMenuView

    try {
        val shiftingMode = menuView::class.java.getDeclaredField("mShiftingMode")
        shiftingMode.apply {
            isAccessible = true
            setBoolean(menuView, false)
            isAccessible = false
        }
        for (i in 0 until menuView.childCount) {
            val item = menuView.getChildAt(i) as BottomNavigationItemView
            item.apply {
                setShiftingMode(false)
                // set once again checked value, so view will be updated
                setChecked(item.itemData.isChecked)
            }
        }
    } catch (e: NoSuchFieldException) {
        Timber.e(e, "Unable to get shift mode field")
    } catch (e: IllegalStateException) {
        Timber.e(e, "Unable to change value of shift mode")
    }
}

fun ImageView.loadWithGlide(
    url: Any?,
    centerCrop: Boolean = true,
    fitCenter: Boolean = false,
    transform: Transformation<Bitmap>? = null
) {
    val request = GlideApp.with(this).load(url)

    when {
        fitCenter -> request.fitCenter()
        centerCrop -> request.centerCrop()
    }

    if (transform != null) {
        request.transform(transform)
    }

    request.into(this)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun View.fadeAnimation() {
    val anim: AlphaAnimation = if (this.visibility == View.VISIBLE) {
        AlphaAnimation(1.0f, 0.0f)
    } else {
        AlphaAnimation(0.0f, 1.0f)
    }
    anim.duration = 600L
    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {}

        override fun onAnimationEnd(animation: Animation?) {
            this@fadeAnimation.visibility = if (this@fadeAnimation.visibility == View.VISIBLE) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        override fun onAnimationStart(animation: Animation?) {}
    })

    this.startAnimation(anim)
}
