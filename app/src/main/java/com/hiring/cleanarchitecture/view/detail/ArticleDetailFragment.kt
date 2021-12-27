package com.hiring.cleanarchitecture.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hiring.cleanarchitecture.databinding.FragmentArticleDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ArticleDetailFragment: Fragment() {

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

        viewModel.setup(articleId)

        binding.fav.setOnClickListener {
            viewModel.toggleFavorite()
        }

        viewModel.article.observe(viewLifecycleOwner) {
            binding.article = it
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Timber.d("error: ${it.error}")
        }

        viewModel.fetchDetail()
    }
}
