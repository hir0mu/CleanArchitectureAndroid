package com.hir0mu.cleanarchitecture.ext

import com.google.android.material.progressindicator.LinearProgressIndicator


fun LinearProgressIndicator.setVisible(visible: Boolean) {
    when (visible) {
        true -> show()
        else -> hide()
    }
}
