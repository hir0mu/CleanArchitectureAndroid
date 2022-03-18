package com.hiring.cleanarchitecture.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hiring.cleanarchitecture.R
import com.hiring.cleanarchitecture.databinding.FragmentFavoriteListBinding
import com.hiring.cleanarchitecture.databinding.ItemFavoriteArticleBinding
import com.hiring.cleanarchitecture.ext.setVisible
import com.hiring.cleanarchitecture.ext.setupToolbar
import com.hiring.cleanarchitecture.util.SimpleAdapter
import com.hiring.cleanarchitecture.view.Execution
import com.hiring.cleanarchitecture.view.detail.ArticleDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FavoriteListFragment: Fragment() {
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
        binding.articleList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.favorites.observe(viewLifecycleOwner) { articles ->
            adapter.updateItems(articles.map { article ->
                FavoriteItem(article) {
                    parentFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.container, ArticleDetailFragment.newInstance(it.id))
                        .commit()
                }
            })
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Timber.d("error: ${it.error}")
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            when (it.execution) {
                FavoriteListViewModel.FetchFavoriteArticleExecution -> binding.indicator.setVisible(it.value)
            }
        }

        viewModel.fetchFavorites()
    }
}
