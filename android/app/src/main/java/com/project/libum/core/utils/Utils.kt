package com.project.libum.core.utils

import android.content.Context

class Utils {
    companion object{
        fun getScreenSizeInDp(context: Context): Pair<Float, Float> {
            val resources = context.resources
            val metrics = resources.displayMetrics
            val widthDp = metrics.widthPixels / metrics.density
            val heightDp = metrics.heightPixels / metrics.density
            return Pair(widthDp, heightDp)
        }

    }

}