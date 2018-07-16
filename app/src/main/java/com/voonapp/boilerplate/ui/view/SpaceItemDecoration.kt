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

package com.voonapp.boilerplate.ui.view

import android.content.Context
import android.graphics.Rect
import android.support.annotation.IntRange
import android.support.v4.text.TextUtilsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.TypedValue
import android.view.View
import java.util.*

class SpaceItemDecoration(context: Context, spacing: Float, private val spanCount: Int = 1) :
    RecyclerView.ItemDecoration() {

    private val spaceInPx =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, spacing, context.resources.displayMetrics).toInt()

    override fun getItemOffsets(
        outRect: Rect?,
        view: View?,
        parent: RecyclerView?,
        state: RecyclerView.State?
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect ?: return
        parent ?: return
        view ?: return
        state ?: return

        setPadding(parent)

        val isRTL =
            TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == ViewCompat.LAYOUT_DIRECTION_RTL
        var orientation = OrientationHelper.VERTICAL
        var isInverse = false
        var position = parent.getChildAdapterPosition(view)
        var spanCurrent = position % spanCount

        when (parent.layoutManager) {
            is LinearLayoutManager -> {
                orientation = (parent.layoutManager as LinearLayoutManager).orientation
                isInverse = (parent.layoutManager as LinearLayoutManager).reverseLayout
                position = parent.getChildAdapterPosition(view)
                spanCurrent = 0
            }
            is GridLayoutManager -> {
                orientation = (parent.layoutManager as GridLayoutManager).orientation
                isInverse = (parent.layoutManager as LinearLayoutManager).reverseLayout
                spanCurrent = if (isRTL && orientation == OrientationHelper.VERTICAL) {
                    spanCount - spanCurrent - 1
                } else {
                    val params = view.layoutParams as GridLayoutManager.LayoutParams
                    params.spanIndex
                }
            }
            is StaggeredGridLayoutManager -> {
                orientation = (parent.layoutManager as StaggeredGridLayoutManager).orientation
                isInverse = (parent.layoutManager as StaggeredGridLayoutManager).reverseLayout
                val params = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
                spanCurrent = params.spanIndex
            }
        }

        if (isRTL && orientation == OrientationHelper.HORIZONTAL) {
            position = state.itemCount - position - 1
        }

        calculateMargin(
            outRect,
            position,
            spanCurrent,
            orientation,
            isInverse
        )
    }

    private fun setPadding(recyclerView: RecyclerView) {
        recyclerView.apply {
            clipToPadding = false
            scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
            setPadding(spaceInPx, spaceInPx, spaceInPx, spaceInPx)
        }
    }

    private fun calculateMargin(
        outRect: Rect,
        position: Int,
        spanCurrent: Int,
        @IntRange(from = 0, to = 1) orientation: Int,
        isReverse: Boolean
    ) {
        when (orientation) {
            OrientationHelper.HORIZONTAL -> {
                outRect.top = spanCurrent * spaceInPx / spanCount
                outRect.bottom = spaceInPx - (spanCurrent + 1) * spaceInPx / spanCount
                if (isReverse) {
                    if (position >= spanCount) outRect.right = spaceInPx
                } else {
                    if (position >= spanCount) outRect.left = spaceInPx
                }
            }
            OrientationHelper.VERTICAL -> {
                outRect.left = spanCurrent * spaceInPx / spanCount
                outRect.right = spaceInPx - (spanCurrent + 1) * spaceInPx / spanCount
                if (isReverse) {
                    if (position >= spanCount) outRect.bottom = spaceInPx
                } else {
                    if (position >= spanCount) outRect.top = spaceInPx
                }
            }
        }
    }

}
