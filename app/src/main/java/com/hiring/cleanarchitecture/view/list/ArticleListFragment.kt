package com.hiring.cleanarchitecture.view.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.hiring.cleanarchitecture.databinding.FragmentArticleListBinding
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
        (requireActivity() as? AppCompatActivity)?.apply {
            setSupportActionBar(binding.toolbar)
        }

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
