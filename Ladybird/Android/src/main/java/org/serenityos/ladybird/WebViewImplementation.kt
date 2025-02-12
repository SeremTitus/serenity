/**
 * Copyright (c) 2023, Andrew Kaster <akaster@serenityos.org>
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */

package org.serenityos.ladybird

import android.graphics.Bitmap
import android.util.Log

/**
 * Wrapper around WebView::ViewImplementation for use by Kotlin
 */
class WebViewImplementation {
    // Instance Pointer to native object, very unsafe :)
    private var nativeInstance = nativeObjectInit()

    init {
        Log.d(
            "Ladybird",
            "New WebViewImplementation (Kotlin) with nativeInstance ${this.nativeInstance}"
        )
    }

    fun dispose() {
        nativeObjectDispose(nativeInstance)
        nativeInstance = 0
    }

    fun drawIntoBitmap(bitmap: Bitmap) {
        nativeDrawIntoBitmap(nativeInstance, bitmap)
    }

    fun setViewportGeometry(w: Int, h: Int) {
        nativeSetViewportGeometry(nativeInstance, w, h)
    }

    private external fun nativeObjectInit(): Long
    private external fun nativeObjectDispose(instance: Long)

    private external fun nativeDrawIntoBitmap(instance: Long, bitmap: Bitmap)
    private external fun nativeSetViewportGeometry(instance: Long, w: Int, h: Int)

    companion object {
        /*
         * We use a static class initializer to allow the native code to cache some
         * field offsets. This native function looks up and caches interesting
         * class/field/method IDs. Throws on failure.
         */
        private external fun nativeClassInit()

        init {
            nativeClassInit()
        }
    }
};
