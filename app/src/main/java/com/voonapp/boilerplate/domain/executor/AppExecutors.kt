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

package com.voonapp.boilerplate.domain.executor

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/*
 * Global executor pools for the whole application.
 *
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind webservice requests).
 */

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()
private val NETWORK_EXECUTOR = Executors.newFixedThreadPool(3)
private val MAIN_EXECUTOR = MainThreadExecutor()

/**
 * Utility method to run blocks on a dedicated background thread, used for io/database work.
 *
 * @param f The function to execute in io/database thread.
 */
fun ioThread(f: () -> Unit) {
    IO_EXECUTOR.execute(f)
}

/**
 * Utility method to run blocks on a dedicated background thread, used for network work.
 *
 * @param f The function to execute in network thread.
 */
fun networkThread(f: () -> Unit) {
    NETWORK_EXECUTOR.execute(f)
}

/**
 * Utility method to run blocks on a dedicated background thread, used for ui work.
 *
 * @param f The function to execute in main thread.
 */
fun mainThread(f: () -> Unit) {
    MAIN_EXECUTOR.execute(f)
}

private class MainThreadExecutor : Executor {
    private val mainThreadHandler = Handler(Looper.getMainLooper())

    override fun execute(command: Runnable) {
        mainThreadHandler.post(command)
    }
}
