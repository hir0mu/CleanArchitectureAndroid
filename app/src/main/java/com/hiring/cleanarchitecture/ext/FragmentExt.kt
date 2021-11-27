package com.hiring.cleanarchitecture.ext

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

fun Fragment.setupToolbar(toolbar: Toolbar, @StringRes title: Int, hasOptionMenu: Boolean = false) {
    (requireActivity() as? AppCompatActivity)?.apply {
        setSupportActionBar(toolbar)
        setTitle(title)
    }
    setHasOptionsMenu(hasOptionMenu)
}
