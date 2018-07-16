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

package com.voonapp.boilerplate.di.picture

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import java.security.MessageDigest

/**
 * CircleCropBorder description
 *
 * @author Julien NORMAND - Orange Applications for Business [julien.normand@orange.com](julien.normand@orange.com)
 * @version 1.0.0
 * @since 2018-06-20
 */
class CircleCropBorder constructor(
    private val context: Context,
    @ColorRes private val colorRes: Int,
    private val borderWidth: Int = 2
) : BitmapTransformation() {

    override fun updateDiskCacheKey(messageDigest: MessageDigest) = messageDigest.update(ID_BYTES)

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val circle = TransformationUtils.circleCrop(pool, toTransform, outWidth, outHeight)
        return addBorderToCircularBitmap(circle)
    }

    override fun equals(other: Any?): Boolean {
        return other is CircleCropBorder
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    private fun addBorderToCircularBitmap(srcBitmap: Bitmap): Bitmap {
        val borderWidthInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            borderWidth.toFloat(),
            context.resources.displayMetrics
        )

        // Calculate the circular bitmap width with border
        val dstBitmapWidth = srcBitmap.width + borderWidthInPx * 2

        val dstBitmap = Bitmap.createBitmap(dstBitmapWidth.toInt(), dstBitmapWidth.toInt(), Bitmap.Config.ARGB_8888)

        val canvas = Canvas(dstBitmap)
        canvas.drawBitmap(srcBitmap, borderWidthInPx, borderWidthInPx, null)

        val paint = Paint().apply {
            color = ContextCompat.getColor(context, colorRes)
            style = Paint.Style.STROKE
            strokeWidth = borderWidthInPx
            isAntiAlias = true
        }

        // Draw the circular border around circular bitmap
        canvas.drawCircle(
            canvas.width / 2f, // cx
            canvas.width / 2f, // cy
            canvas.width / 2f - borderWidthInPx / 2f, // Radius
            paint // Paint
        )

        // Free the native object associated with this bitmap.
        srcBitmap.recycle()

        // Return the bordered circular bitmap
        return dstBitmap
    }

    companion object {
        private const val VERSION = 1
        private const val ID = "com.bumptech.glide.load.resource.bitmap.CircleCrop.$VERSION"
        private val ID_BYTES = ID.toByteArray(Key.CHARSET)
    }
}
