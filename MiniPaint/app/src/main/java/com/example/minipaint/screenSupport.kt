package com.example.minipaint

import android.content.res.Resources

val Float.dp: Float get() = (this / Resources.getSystem().displayMetrics.density)
val Float.px: Float get() = (this * Resources.getSystem().displayMetrics.density)

val Int.dp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.norm: Float get() = (this/400)