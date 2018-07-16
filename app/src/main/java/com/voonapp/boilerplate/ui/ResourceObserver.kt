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

package com.voonapp.boilerplate.ui

import android.arch.lifecycle.Observer
import android.util.Log
import com.voonapp.boilerplate.domain.interactor.Resource
import com.voonapp.boilerplate.domain.interactor.Status

/**
 * An abstract [Observer] that make the boilerplate code to match with the different states of [Resource];
 *
 * @author Julien NORMAND - Orange Applications for Business [julien.normand@orange.com](julien.normand@orange.com)
 * @version 1.0.0
 * @since 2018-06-20
 *
 * @property tag Used to identify the source of a log message.
 * @property hideLoading The action to be performed to hide the loading state.
 * @property showLoading The action to be performed to show the loading state.
 * @property onSuccess The action to be performed to show the success state.
 * @property onError The action to be performed to show the error state.
 *
 * @param T The type of elements held.
 */
class ResourceObserver<T>(
    private val tag: String,
    private val hideLoading: () -> Unit,
    private val showLoading: () -> Unit,
    private val onSuccess: (data: T) -> Unit,
    private val onError: (message: String) -> Unit
) : Observer<Resource<T>> {

    override fun onChanged(resource: Resource<T>?) {
        when (resource?.status) {
            Status.LOADING -> {
                showLoading()
                Log.d(tag, "observer -> LOADING")
            }
            Status.SUCCESS -> {
                hideLoading()
                if (resource.data != null) {
                    Log.d(tag, "observer -> SUCCESS, ${resource.data} items")
                    onSuccess(resource.data)
                }
            }
            Status.ERROR -> {
                hideLoading()
                if (resource.error != null) {
                    Log.d(tag, "observer -> ERROR, ${resource.error}")
                    onError(resource.error.message)
                }
            }
        }
    }
}
