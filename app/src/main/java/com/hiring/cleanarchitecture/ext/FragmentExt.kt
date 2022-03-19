package com.hiring.cleanarchitecture.ext

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.hiring.cleanarchitecture.R
import com.hiring.cleanarchitecture.view.Failure

fun Fragment.setupToolbar(toolbar: Toolbar, @StringRes title: Int, hasOptionMenu: Boolean = false) {
    (requireActivity() as? AppCompatActivity)?.apply {
        setSupportActionBar(toolbar)
        setTitle(title)
    }
    setHasOptionsMenu(hasOptionMenu)
}

fun Fragment.showErrorSnackBar(failure: Failure) {
    val root = view ?: return
    Snackbar.make(root, failure.message, Snackbar.LENGTH_INDEFINITE)
        .setAction(R.string.retry) { failure.retry() }
        .show()
}
