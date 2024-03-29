package com.hir0mu.cleanarchitecture.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.hir0mu.cleanarchitecture.databinding.FragmentArticleDetailBinding
import com.hir0mu.cleanarchitecture.ext.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailFragment : Fragment() {

    private val viewModel: ArticleDetailViewModel by viewModels()
    private lateinit var binding: FragmentArticleDetailBinding
    private val args: ArticleDetailFragmentArgs by navArgs()

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

        viewModel.setup(args.articleId)

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
