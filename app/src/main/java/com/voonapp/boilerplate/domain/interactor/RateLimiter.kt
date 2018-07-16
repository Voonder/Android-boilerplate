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

package com.voonapp.boilerplate.domain.interactor

import android.os.SystemClock
import android.util.ArrayMap
import java.util.concurrent.TimeUnit

/**
 * Utility class that decides whether we should fetch some data or not.
 *
 * @author Julien NORMAND - Orange Applications for Business [julien.normand@orange.com](julien.normand@orange.com)
 * @version 1.0.0
 * @since 2018-06-20
 *
 * @param timeout The time corresponding to the [TimeUnit] to have a valid cache.
 * @param timeUnit The time unit corresponding to a [TimeUnit] value.
 *
 * @param T The type of elements held.
 */
class RateLimiter<in T> constructor(timeout: Int, timeUnit: TimeUnit) {

    private val timestamps = ArrayMap<T, Long>()
    private val timeout: Long = timeUnit.toMillis(timeout.toLong())

    /**
     * Check if the key has expired.
     *
     * @param key the key to check.
     */
    @Synchronized
    fun shouldFetch(key: T): Boolean {
        val lastFetched = timestamps[key]
        timestamps.forEach { t, u ->  }
        val now = now()
        if (lastFetched == null) {
            timestamps[key] = now
            return true
        }
        if (now - lastFetched > timeout) {
            timestamps[key] = now
            return true
        }
        return false
    }

    /**
     * Remove the key from the map.
     *
     * @param key the key to remove.
     */
    @Synchronized
    fun reset(key: T) {
        timestamps.remove(key)
    }

    private fun now(): Long {
        return SystemClock.uptimeMillis()
    }
}
