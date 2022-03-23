package com.hiring.cleanarchitecture.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hiring.cleanarchitecture.databinding.FragmentArticleDetailBinding
import com.hiring.cleanarchitecture.ext.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailFragment : Fragment() {

    companion object {
        private const val ARGS_ARTICLE_ID = "args_article_id"

        fun newInstance(articleId: String): ArticleDetailFragment {
            return ArticleDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARGS_ARTICLE_ID, articleId)
                }
            }
        }
    }

    private val viewModel: ArticleDetailViewModel by viewModels()
    private lateinit var binding: FragmentArticleDetailBinding

    private val articleId: String by lazy { checkNotNull(arguments?.getString(ARGS_ARTICLE_ID)) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.indicator.show()
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                binding.indicator.setProgressCompat(newProgress, true)
                if (newProgress == 100) {
                    binding.indicator.hide()
                }
            }
        }

        viewModel.setup(articleId)

        binding.fav.setOnClickListener {
            viewModel.toggleFavorite()
        }

        viewModel.article.observe(viewLifecycleOwner) {
            binding.article = it.article
            binding.isFavorite = it.isFavorite
        }

        viewModel.error.observe(viewLifecycleOwner) {
            when (it.execution) {
                ArticleDetailViewModel.FetchArticleDetailExecution -> {
                    showErrorSnackBar(it)
                }
            }
        }

        viewModel.fetchDetail()
    }
}
