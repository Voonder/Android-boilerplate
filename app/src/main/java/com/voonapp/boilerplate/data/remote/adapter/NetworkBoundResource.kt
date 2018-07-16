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

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.voonapp.boilerplate.domain.executor.ioThread
import com.voonapp.boilerplate.domain.executor.mainThread
import com.voonapp.boilerplate.domain.interactor.Error
import com.voonapp.boilerplate.domain.interactor.Resource

/**
 * A generic class that can provide a resource backed by both the sql database and the network.
 *
 * @author Julien NORMAND - Orange Applications for Business [julien.normand@orange.com](julien.normand@orange.com)
 * @version 1.0.0
 * @since 2018-04-03
 *
 * @param ResultT
 * @param RequestT
 */
abstract class NetworkBoundResource<ResultT, RequestT> @MainThread constructor(withDatabase: Boolean = true) {

    private val result = MediatorLiveData<Resource<ResultT>>()

    init {
        result.value = Resource.loading(null)

        if (withDatabase) {
            fetchFromDatabaseAndNetwork()
        } else {
            fetchFromNetwork()
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultT>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromDatabaseAndNetwork() {
        val dbSource = loadFromDb()
        result.addSource(dbSource) {
            result.removeSource(dbSource)
            if (shouldFetch(it)) {
                val apiResponse = createCall()

                // we re-attach dbSource as a new source, it will dispatch its latest value quickly
                result.addSource(dbSource) {
                    setValue(Resource.loading(it))
                }

                result.addSource(apiResponse) { response ->
                    result.removeSource(apiResponse)
                    result.removeSource(dbSource)

                    when (response) {
                        is ApiResponse.Success -> {
                            ioThread {
                                saveCallResult(processResponse(response))
                                mainThread {
                                    // we specially request a new live data, otherwise we will get immediately last cached
                                    // value, which may not be updated with latest results received from network.
                                    result.addSource(loadFromDb()) {
                                        setValue(Resource.success(it))
                                    }
                                }
                            }
                        }
                        is ApiResponse.Empty -> {
                            // reload from disk whatever we had
                            mainThread {
                                result.addSource(loadFromDb()) {
                                    setValue(Resource.success(it))
                                }
                            }
                        }
                        is ApiResponse.Error -> {
                            onFetchFailed()
                            result.addSource(dbSource) {
                                setValue(Resource.error(it, Error(response.code, response.message)))
                            }
                        }
                    }
                }
            } else {
                result.addSource(dbSource) {
                    setValue(Resource.success(it))
                }
            }
        }
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()

        setValue(Resource.loading(null))

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)

            when (response) {
                is ApiResponse.Success -> {
                    val mapRequest = mapRequestToResult(processResponse(response))
                    result.addSource(mapRequest) {
                        setValue(Resource.success(it))
                    }
                }
                is ApiResponse.Empty -> setValue(Resource.success(null))
                is ApiResponse.Error -> {
                    onFetchFailed()
                    setValue(Resource.error(null, Error(response.code, response.message)))
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultT>> {
        return result
    }

    @WorkerThread
    private fun processResponse(response: ApiResponse.Success<RequestT>) = response.body

    protected open fun onFetchFailed() {}

    @MainThread
    protected open fun mapRequestToResult(item: RequestT): LiveData<ResultT> {
        return MutableLiveData()
    }

    @WorkerThread
    protected open fun saveCallResult(item: RequestT) {}

    @MainThread
    protected open fun loadFromDb(): LiveData<ResultT> {
        return MutableLiveData()
    }

    @MainThread
    protected abstract fun shouldFetch(data: ResultT?): Boolean

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestT>>
}
