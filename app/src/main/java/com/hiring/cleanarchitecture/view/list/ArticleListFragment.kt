package com.hiring.cleanarchitecture.view.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hiring.cleanarchitecture.R
import com.hiring.cleanarchitecture.databinding.FragmentArticleListBinding
import com.hiring.cleanarchitecture.databinding.ItemArticleBinding
import com.hiring.cleanarchitecture.ext.setupToolbar
import com.hiring.cleanarchitecture.util.SimpleAdapter
import com.hiring.cleanarchitecture.view.detail.ArticleDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ArticleListFragment: Fragment() {
    private val viewModel: ArticleListViewModel by viewModels()
    private lateinit var binding: FragmentArticleListBinding
    private val adapter = SimpleAdapter<ItemArticleBinding>(R.layout.item_article)

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

        binding.articleList.adapter = adapter
        binding.articleList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.setup("android")
        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            adapter.updateItems(articles.map { article ->
                ArticleItem(
                    article,
                    onItemClick = {
                        parentFragmentManager.beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.container, ArticleDetailFragment.newInstance(it.id))
                            .commit()
                    },
                    onCheckedChange = {
                        viewModel.toggleFavorite(it)
                    }
                )
            })
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Timber.d("error: ${it.error}")
        }

        viewModel.fetchArticles()
    }
}
