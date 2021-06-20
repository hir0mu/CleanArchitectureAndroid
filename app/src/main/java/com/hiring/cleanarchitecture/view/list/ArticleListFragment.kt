package com.hiring.cleanarchitecture.view.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hiring.cleanarchitecture.R
import com.hiring.cleanarchitecture.databinding.FragmentArticleListBinding
import com.hiring.cleanarchitecture.ext.setupToolbar
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ArticleListFragment: ScopeFragment() {
    private val viewModel: ArticleListViewModel by viewModel()
    private lateinit var binding: FragmentArticleListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(binding.toolbar, R.string.title_article_list)

        viewModel.setup("android")
        viewModel.articles.observe(viewLifecycleOwner) {
            Timber.d("articles: $it")
        }

        viewModel.fetchArticles()

        viewModel.error.observe(viewLifecycleOwner) {
            Timber.d("error: ${it.error}")
        }
    }
}
