package com.hir0mu.cleanarchitecture.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hir0mu.cleanarchitecture.R
import com.hir0mu.cleanarchitecture.databinding.FragmentFavoriteListBinding
import com.hir0mu.cleanarchitecture.databinding.ItemFavoriteArticleBinding
import com.hir0mu.cleanarchitecture.ext.setVisible
import com.hir0mu.cleanarchitecture.ext.setupToolbar
import com.hir0mu.cleanarchitecture.ext.showErrorSnackBar
import com.hir0mu.cleanarchitecture.util.SimpleAdapter
import com.hir0mu.cleanarchitecture.view.detail.ArticleDetailFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteListFragment : Fragment() {
    private val viewModel: FavoriteListViewModel by viewModels()
    private lateinit var binding: FragmentFavoriteListBinding
    private val adapter = SimpleAdapter<ItemFavoriteArticleBinding>(R.layout.item_favorite_article)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(binding.toolbar, R.string.title_favorite_list)

        binding.articleList.adapter = adapter
        binding.articleList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.favorites.observe(viewLifecycleOwner) { articles ->
            adapter.updateItems(articles.map { article ->
                FavoriteItem(article) {
                    val action = ArticleDetailFragmentDirections.actionToArticleDetail(it.article.id)
                    findNavController().navigate(action)
                }
            })
        }

        viewModel.error.observe(viewLifecycleOwner) {
            when (it.execution) {
                FavoriteListViewModel.FetchFavoriteArticleExecution -> {
                    showErrorSnackBar(it)
                }
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            when (it.execution) {
                FavoriteListViewModel.FetchFavoriteArticleExecution -> {
                    binding.indicator.setVisible(it.value)
                }
            }
        }

        viewModel.fetchFavorites()
    }
}
