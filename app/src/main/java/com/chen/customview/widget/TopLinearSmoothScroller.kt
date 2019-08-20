package com.chen.customview.widget

import android.content.Context
import androidx.recyclerview.widget.LinearSmoothScroller


class TopLinearSmoothScroller constructor(context: Context?) : LinearSmoothScroller(context) {

    override fun getVerticalSnapPreference(): Int {
        return SNAP_TO_START
    }
}