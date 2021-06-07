package com.hiring.cleanarchitecture.view.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ArticleListFragment: ScopeFragment() {
    private val viewModel: ArticleListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("view model: $viewModel")
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}
