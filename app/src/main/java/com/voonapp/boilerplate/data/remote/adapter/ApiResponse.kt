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

package com.voonapp.boilerplate.data.remote.adapter

import retrofit2.Response
import timber.log.Timber
import java.util.regex.Pattern

/**
 * Common class used by API responses.
 *
 * @author Julien NORMAND - Orange Applications for Business [julien.normand@orange.com](julien.normand@orange.com)
 * @version 1.0.0
 * @since 2018-07-16
 *
 * @param T the type of the response object
 */
sealed class ApiResponse<T> {

    data class Success<T>(val body: T, val links: Map<String, String> = emptyMap()) : ApiResponse<T>() {

        constructor(body: T, linkHeader: String?) : this(body, linkHeader?.extractLinks() ?: emptyMap())

        val nextPage: Int? by lazy(LazyThreadSafetyMode.NONE) {
            links[NEXT_LINK]?.let { next ->
                val matcher = PAGE_PATTERN.matcher(next)
                if (!matcher.find() || matcher.groupCount() != 1) {
                    null
                } else {
                    try {
                        Integer.parseInt(matcher.group(1))
                    } catch (ex: NumberFormatException) {
                        Timber.w("cannot parse next page from %s", next)
                        null
                    }
                }
            }
        }

        private companion object {
            private val LINK_PATTERN: Pattern = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")
            private val PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)")
            private const val NEXT_LINK = "next"

            fun String.extractLinks(): Map<String, String> {
                val links = mutableMapOf<String, String>()
                val matcher = LINK_PATTERN.matcher(this)

                while (matcher.find()) {
                    val count = matcher.groupCount()
                    if (count == 2) {
                        links[matcher.group(2)] = matcher.group(1)
                    }
                }
                return links
            }
        }
    }

    /**
     * Separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
     */
    class Empty<T> : ApiResponse<T>()

    data class Error<T>(val code: Int, val message: String) : ApiResponse<T>()

    companion object {
        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    Empty()
                } else {
                    Success(body, response.headers()?.get("link"))
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                Error(response.code(), errorMsg ?: "unknown error")
            }
        }

        fun <T> create(error: Throwable): Error<T> {
            return Error(503, error.message ?: "unknown error")
        }
    }
}
